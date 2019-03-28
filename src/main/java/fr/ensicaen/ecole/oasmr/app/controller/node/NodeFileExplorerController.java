/*
 *  Copyright (c) 2019. CCC-Development-Team
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package fr.ensicaen.ecole.oasmr.app.controller.node;


import com.jfoenix.animation.alert.JFXAlertAnimation;
import com.jfoenix.controls.*;
import fr.ensicaen.ecole.oasmr.app.Config;
import fr.ensicaen.ecole.oasmr.app.Main;
import fr.ensicaen.ecole.oasmr.app.view.NodesModel;
import fr.ensicaen.ecole.oasmr.app.view.View;
import fr.ensicaen.ecole.oasmr.lib.ObjectCreatedHandler;
import fr.ensicaen.ecole.oasmr.lib.filemanagement.*;
import fr.ensicaen.ecole.oasmr.lib.network.exception.ExceptionPortInvalid;
import fr.ensicaen.ecole.oasmr.supervisor.node.command.request.RequestExecuteCommand;
import fr.ensicaen.ecole.oasmr.supervisor.request.RequestManager;
import fr.ensicaen.ecole.oasmr.supervisor.request.RequestManagerFlyweightFactory;
import javafx.beans.property.BooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class NodeFileExplorerController extends View {

    @FXML
    VBox fileExplorerVBox;

    private RequestManager requestManager = null;
    private Config config;
    private NodesModel nodesModel;
    private JFXTreeView<FileAdapter> fileTreeView;
    private FileAdapter selectedForCopy;

    private final Image folderCloseIcon =
            new Image(Main.class.getResourceAsStream("img/folder_close.png"), 18, 18, false, false);
    private final Image folderOpenIcon =
            new Image(Main.class.getResourceAsStream("img/folder_open.png"), 18, 18, false, false);
    private final Image fileIcon =
            new Image(Main.class.getResourceAsStream("img/file.png"), 18, 18, false, false);

    public NodeFileExplorerController(View parent) throws IOException {
        super("NodeFileExplorer", parent);
        onCreate();
    }

    @Override
    public void onCreate() {
        nodesModel = NodesModel.getInstance();
    }

    @Override
    protected void onStart() {
        if (requestManager == null) {
            try {
                config = Config.getInstance();
                requestManager = RequestManagerFlyweightFactory.getInstance().getRequestManager(InetAddress.getByName(config.getIP()), config.getPort());
            } catch (ExceptionPortInvalid | UnknownHostException exceptionPortInvalid) {
                exceptionPortInvalid.printStackTrace();
            }
        }

        if (nodesModel.getSelectedAmount() > 1) {
            //TODO : Configure view for group
        } else if (nodesModel.getSelectedAmount() == 1) {

            try {
                Future<? extends Serializable> reponseRoot = requestManager.aSyncSendRequest(new RequestExecuteCommand(
                        nodesModel.getCurrentNodeData().iterator().next().getId(),
                        new CommandListRoots()
                ));
                String[] listRoots = (String[]) reponseRoot.get();
                TreeItem<FileAdapter> treeRoot = new TreeItem<>(new FileAdapter());
                for(String rootPath : listRoots){
                    FileAdapter root = new FileAdapter(rootPath, true);
                    fileExplorerVBox.getChildren().clear();
                    TreeItem<FileAdapter> rootItem = new TreeItem<>(root, new ImageView(folderCloseIcon));
                    ObservableList<TreeItem<FileAdapter>> children = buildChildren(rootItem);
                    rootItem.getChildren().addAll(children);
                    treeRoot.getChildren().add(rootItem);
                }
                fileTreeView = new JFXTreeView<>(treeRoot);
                fileTreeView.setShowRoot(false);
                fileTreeView.setCellFactory(p -> new FileTreeCell());
                fileExplorerVBox.getChildren().add(fileTreeView);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    protected void onUpdate() {

    }

    @Override
    public void onStop() {

    }

    private ObservableList<TreeItem<FileAdapter>> buildChildren(TreeItem<FileAdapter> treeItem) throws ExecutionException, InterruptedException {
        treeItem.getChildren().clear();
        Future<? extends Serializable> reponseList = requestManager.aSyncSendRequest(new RequestExecuteCommand(
                nodesModel.getCurrentNodeData().iterator().next().getId(),
                new CommandListFiles(treeItem.getValue().getPath())
        ));
        String[] list = (String[]) reponseList.get();
        if (list.length != 0) {
            ObservableList<TreeItem<FileAdapter>> children = FXCollections.observableArrayList();
            for (String childFile : list) {
                Future<? extends Serializable> isDirectory = requestManager.aSyncSendRequest(new RequestExecuteCommand(
                        nodesModel.getCurrentNodeData().iterator().next().getId(),
                        new CommandIsDirectory(childFile)
                ));
                Boolean isDirectoryReponse = (Boolean) isDirectory.get();
                FileAdapter childFileAdapter = new FileAdapter(childFile, isDirectoryReponse.booleanValue());
                TreeItem<FileAdapter> childTreeItem = new TreeItem<>(childFileAdapter);
                if(isDirectoryReponse){
                    childTreeItem.getChildren().add(new TreeItem<>(new FileAdapter()));
                    childTreeItem.setGraphic(new ImageView(folderCloseIcon));
                    childTreeItem.expandedProperty().addListener((observable, oldValue, newValue) -> {
                        BooleanProperty bb = (BooleanProperty) observable;
                        TreeItem<FileAdapter> t = (TreeItem<FileAdapter>) bb.getBean();
                        if(observable.getValue()){
                            try {
                                ObservableList<TreeItem<FileAdapter>> newTreeItem = buildChildren(t);
                                t.getChildren().addAll(newTreeItem);
                            } catch (ExecutionException | InterruptedException e) {
                                e.printStackTrace();
                            }
                            t.setGraphic(new ImageView(folderOpenIcon));
                        }else{
                            t.getChildren().clear();
                            t.getChildren().add(new TreeItem<>(new FileAdapter()));
                            t.setGraphic(new ImageView(folderCloseIcon));
                        }
                    });
                }else{
                    childTreeItem.setGraphic(new ImageView(fileIcon));
                }
                children.add(childTreeItem);
            }
            return children;
        }

        return FXCollections.emptyObservableList();
    }

    private void nameFileFromDialog(String path, boolean isDir, Stage stage, ObjectCreatedHandler handler){
        JFXDialogLayout content = new JFXDialogLayout();
        String type = (isDir ? "directory" : "file");
        content.setHeading(new Label("Name " + type));
        HBox hBox = new HBox();
        hBox.getChildren().add(new Label(type + "name : "));
        JFXTextField textField = new JFXTextField();
        hBox.getChildren().add(textField);
        content.setBody(hBox);

        JFXAlert alert = new JFXAlert(stage);
        alert.setOverlayClose(true);
        alert.setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
        alert.setContent(content);
        alert.initModality(Modality.APPLICATION_MODAL);

        JFXButton create = new JFXButton("Ok");
        JFXButton close = new JFXButton("Close");
        close.setOnAction(actionEvent -> alert.close());
        create.setOnAction(actionEvent -> {
            alert.close();
            String name = textField.getText();
            try {
                Object o = FileAdapter.class.getConstructors()[1].newInstance(path + "/" + name, isDir);
                handler.objectCreatedHandler(o);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        });
        content.setActions(create, close);
        alert.show();
    }


    private final class FileTreeCell extends TreeCell<FileAdapter> {

        private TextField textField;
        private ContextMenu menu = new ContextMenu();

        public FileTreeCell() {

            MenuItem addFileMenuItem = new MenuItem("New file");
            menu.getItems().add(addFileMenuItem);
            addFileMenuItem.setOnAction(t -> {
                TreeItem<FileAdapter> parentItem = (getItem().isDir() ? getTreeItem() : getTreeItem().getParent());
                Stage stage = (Stage) fileExplorerVBox.getScene().getWindow();
                nameFileFromDialog(parentItem.getValue().getPath(), false, stage, handler -> {
                    FileAdapter createdFile = (FileAdapter) handler;
                    Future<? extends Serializable> isCreated = requestManager.aSyncSendRequest(new RequestExecuteCommand(
                            nodesModel.getCurrentNodeData().iterator().next().getId(),
                            new CommandCreateFile(createdFile.getPath())
                    ));
                    try {
                        Boolean isCreatedReponse = (Boolean) isCreated.get();
                        if(isCreatedReponse){
                            TreeItem<FileAdapter> newFile = new TreeItem<>(
                                    createdFile,
                                    new ImageView(fileIcon)
                            );
                            parentItem.getChildren().add(newFile);
                        }
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                });
            });

            MenuItem addDirMenuItem = new MenuItem("New directory");
            menu.getItems().add(addDirMenuItem);
            addDirMenuItem.setOnAction(t -> {
                TreeItem<FileAdapter> parentItem = (getItem().isDir() ? getTreeItem() : getTreeItem().getParent());
                Stage stage = (Stage) fileExplorerVBox.getScene().getWindow();
                nameFileFromDialog(parentItem.getValue().getPath(), true, stage, handler -> {
                    FileAdapter createdFile = (FileAdapter) handler;
                    Future<? extends Serializable> isCreated = requestManager.aSyncSendRequest(new RequestExecuteCommand(
                            nodesModel.getCurrentNodeData().iterator().next().getId(),
                            new CommandMakeDiretory(createdFile.getPath())
                    ));
                    try {
                        Boolean isCreatedReponse = (Boolean) isCreated.get();
                        if(isCreatedReponse){
                            TreeItem<FileAdapter> newFile = new TreeItem<>(
                                    createdFile,
                                    new ImageView(folderCloseIcon)
                            );
                            parentItem.getChildren().add(newFile);
                        }
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                });
            });

            menu.getItems().add(new SeparatorMenuItem());

            MenuItem renameMenuItem = new MenuItem("Rename");
            menu.getItems().add(renameMenuItem);
            renameMenuItem.setOnAction(t -> {
                Stage stage = (Stage) fileExplorerVBox.getScene().getWindow();
                nameFileFromDialog(getItem().getPath(), false, stage, handler -> {
                    FileAdapter createdFile = (FileAdapter) handler;
                    Future<? extends Serializable> isRenamed = requestManager.aSyncSendRequest(new RequestExecuteCommand(
                            nodesModel.getCurrentNodeData().iterator().next().getId(),
                            new CommandRenameFile(getItem().getPath(), createdFile.getName())
                    ));
                    try {
                        Boolean isRenamedReponse = (Boolean) isRenamed.get();
                        if(isRenamedReponse){
                            TreeItem<FileAdapter> newFile = new TreeItem<>(
                                    createdFile,
                                    new ImageView(fileIcon)
                            );
                            getTreeItem().getParent().getChildren().add(newFile);
                            getTreeItem().getParent().getChildren().remove(getTreeItem());
                        }
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                });
            });

            MenuItem removeMenuItem = new MenuItem("Remove");
            menu.getItems().add(removeMenuItem);
            removeMenuItem.setOnAction(t -> {
                Future<? extends Serializable> isDeleted = requestManager.aSyncSendRequest(new RequestExecuteCommand(
                        nodesModel.getCurrentNodeData().iterator().next().getId(),
                        new CommandRemoveFile(getItem().getPath())
                ));
                try {
                    Boolean isDeletedReponse = (Boolean) isDeleted.get();
                    if(isDeletedReponse){
                        getTreeItem().getParent().getChildren().remove(getTreeItem());
                    }
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            });

            menu.getItems().add(new SeparatorMenuItem());

            MenuItem copyMenuItem = new MenuItem("Copy");
            menu.getItems().add(copyMenuItem);
            copyMenuItem.setOnAction(t -> {
                selectedForCopy = getItem();
            });

            MenuItem cutMenuItem = new MenuItem("Cut");
            menu.getItems().add(cutMenuItem);
            cutMenuItem.setOnAction(t -> {
                selectedForCopy = getItem();
            });

            MenuItem pasteManuItem = new MenuItem("Paste");
            menu.getItems().add(pasteManuItem);
            pasteManuItem.setOnAction(t -> {
                selectedForCopy = null;
            });

        }

        @Override
        public void startEdit() {
            super.startEdit();

            if (textField == null) {
                createTextField();
            }
            setText(null);
            setGraphic(textField);
            textField.selectAll();
        }

        @Override
        public void cancelEdit() {
            super.cancelEdit();
            setText(getItem().getName());
            setGraphic(getTreeItem().getGraphic());
        }

        @Override
        public void updateItem(FileAdapter item, boolean empty) {
            super.updateItem(item, empty);

            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                if (isEditing()) {
                    if (textField != null) {
                        textField.setText(getString());
                    }
                    setText(null);
                    setGraphic(textField);
                } else {
                    setText(getString());
                    setGraphic(getTreeItem().getGraphic());
                    if (!getTreeItem().isLeaf()&&getTreeItem().getParent()!= null){
                        setContextMenu(menu);
                    }
                }
            }
        }

        private void createTextField() {
            textField = new TextField(getString());
            textField.setOnKeyReleased(t -> {
                if (t.getCode() == KeyCode.ENTER) {
                    System.out.println(getItem().getPath() + "/" + textField.getText());
                    commitEdit(new FileAdapter(getItem().getPath() + "/" + textField.getText(), getItem().isDir()));
                } else if (t.getCode() == KeyCode.ESCAPE) {
                    cancelEdit();
                }
            });

        }

        private String getString() {
            return getItem() == null ? "" : getItem().toString();
        }
    }


}
