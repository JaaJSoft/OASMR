/*
 * Copyright (c) 2019. JaaJ-dev
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.jaaj.oasmr.app.controller;

import com.jfoenix.controls.*;
import com.jfoenix.controls.cells.editors.TextFieldEditorBuilder;
import com.jfoenix.controls.cells.editors.base.GenericEditableTreeTableCell;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import dev.jaaj.oasmr.lib.network.exception.ExceptionPortInvalid;
import dev.jaaj.oasmr.app.Config;
import dev.jaaj.oasmr.app.Session;
import dev.jaaj.oasmr.app.view.SceneManager;
import dev.jaaj.oasmr.app.view.View;
import dev.jaaj.oasmr.app.view.exception.ExceptionSceneNotFound;
import dev.jaaj.oasmr.supervisor.auth.User;
import dev.jaaj.oasmr.supervisor.auth.request.*;
import dev.jaaj.oasmr.supervisor.request.RequestManager;
import dev.jaaj.oasmr.supervisor.request.RequestManagerFlyweightFactory;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTreeTableCell;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Objects;

public class UserManagementController extends View{
    private SceneManager sceneManager;
    private RequestManager requestManager;
    private List<String> userList;
    private ObservableList<UserInfo> data;
    private String currentUserLogin;
    private int nbAdmin;

    @FXML
    JFXButton returnPrev;

    @FXML
    JFXButton deleteUser;

    @FXML
    JFXButton addUser;

    @FXML
    VBox userTableVBox;

    @FXML
    JFXButton adminBtn;

    @FXML
    Label errorMsg;


    @FXML
    JFXTextField searchField;



    public UserManagementController() throws IOException {
        super("UserManagement");

    }



    public void applyChanges(ActionEvent actionEvent) {

    }

    private void onLoadTest(String toSearch) {
        nbAdmin = 0;

        currentUserLogin = Session.getProperty("user");
        System.out.println("User connected :" + currentUserLogin);
        RequestGetAdmin requestGetAdmin = new RequestGetAdmin(currentUserLogin);
        Boolean isCurrentAdmin = false;
        try {
            isCurrentAdmin = (Boolean) requestManager.sendRequest(requestGetAdmin);
        } catch (Exception e) {
            e.printStackTrace();
        }


        searchField.setOnAction(event -> {
            if (!searchField.getText().trim().equals("")) {
                onLoadTest(searchField.getText());
            } else {
                onLoadTest("");
            }
        });

        searchField.setOnKeyReleased(event -> {
            //if(searchField.getText().trim().equals(""))
                onLoadTest(searchField.getText());
        });


        if(isCurrentAdmin) {

            userTableVBox.getChildren().clear();

            RequestGetLoginList r = new RequestGetLoginList();

            try {
                userList = (List<String>) requestManager.sendRequest(r);
            } catch (Exception e) {
                e.printStackTrace();
            }

            addUser.setOnAction(addUser(new Stage()));
            returnPrev.setOnAction(this::returnAction);

            JFXTreeTableColumn<UserInfo, String> userCol = new JFXTreeTableColumn<>("Login");
            userCol.setPrefWidth(300);
            userCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<UserInfo, String> param) -> {
                if (userCol.validateValue(param)) {
                    return param.getValue().getValue().login;
                } else {
                    return userCol.getComputedValue(param);
                }
            });

            userCol.setCellFactory((TreeTableColumn<UserInfo, String> param) -> new GenericEditableTreeTableCell<>(
                    new TextFieldEditorBuilder()));
            userCol.setOnEditCommit(new EventHandler<TreeTableColumn.CellEditEvent<UserInfo, String>>() {
                @Override
                public void handle(TreeTableColumn.CellEditEvent<UserInfo, String> t) {
                    String old = t.getOldValue();
                    t.getTreeTableView()
                            .getTreeItem(t.getTreeTablePosition()
                                    .getRow())
                            .getValue().login.set(t.getNewValue());

                    try {
                        requestManager.sendRequest(new RequestModifyUserLogin(old, t.getNewValue()));
                    } catch (Exception e) {
                        t.getTreeTableView()
                                .getTreeItem(t.getTreeTablePosition()
                                        .getRow())
                                .getValue().login.set(old);
                        e.printStackTrace();
                    }
                }
            });

            final TreeTableColumn<UserInfo, Boolean> adminCol = new TreeTableColumn<>("admin");
            adminCol.setCellFactory(CheckBoxTreeTableCell.forTreeTableColumn(adminCol));
            adminCol.setCellValueFactory(param -> param.getValue().getValue().admin);


            final TreeTableColumn<UserInfo, JFXButton> deleteCol = new TreeTableColumn<>("delete User");
            deleteCol.setCellValueFactory(param -> (ObservableValue<JFXButton>) param.getValue().getValue().delete);

            ObservableList<UserInfo> users = FXCollections.observableArrayList();

            for (String user : userList) {

                boolean admin = false;
                try {
                    admin = (boolean) requestManager.sendRequest(new RequestGetAdmin(user));
                    if (admin) {
                        nbAdmin++;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (!toSearch.trim().equals("") && user.contains(toSearch)) {
                    users.add(new UserInfo(user, admin));
                } else if (toSearch.trim().equals("")){
                    users.add(new UserInfo(user, admin));
                }

            }

            for (UserInfo u : users) {
                System.out.println(u);
            }

            final TreeItem<UserInfo> root = new RecursiveTreeItem<>(users, RecursiveTreeObject::getChildren);

            JFXTreeTableView userTable = new JFXTreeTableView(root);
            userTable.setShowRoot(false);
            userTable.setEditable(true);
            adminCol.setEditable(false);
            userTable.getColumns().setAll(userCol, adminCol);

            userTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            userTable.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

            deleteUser.setOnAction(event -> {
                for (Object u : userTable.getSelectionModel().getSelectedItems()) {
                    System.out.println(u);
                    if (u instanceof TreeItem) {
                        if (((TreeItem) u).getValue() instanceof UserInfo) {
                            RequestDeleteUser deleteRequest = new RequestDeleteUser(((UserInfo) ((TreeItem) u).getValue()).getLogin());
                            try {
                                requestManager.sendRequest(deleteRequest);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                onLoadTest(toSearch);
            });


            adminBtn.setOnAction(event -> {
                for (Object u : userTable.getSelectionModel().getSelectedItems()) {
                    System.out.println(u);
                    if (u instanceof TreeItem) {
                        if (((TreeItem) u).getValue() instanceof UserInfo) {

                            RequestSetAdmin requestSetAdmin = new RequestSetAdmin(((UserInfo) ((TreeItem) u).getValue()).getLogin(), !((UserInfo) ((TreeItem) u).getValue()).getAdmin().getValue());
                            try {
                                if (nbAdmin==1 && ((UserInfo) ((TreeItem) u).getValue()).getAdmin().getValue()){
                                    errorMsg.setText("You have to have at least one administrator");
                                } else {
                                    if (((UserInfo) ((TreeItem) u).getValue()).getAdmin().getValue()) {
                                        nbAdmin--;
                                    }

                                    requestManager.sendRequest(requestSetAdmin);

                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                onLoadTest(toSearch);
            });


            userTableVBox.getChildren().add(userTable);

            JFXTextField filterField = new JFXTextField();
            userTableVBox.getChildren().add(filterField);

            Label size = new Label();

            size.textProperty()
                    .bind(Bindings.createStringBinding(() -> String.valueOf(userTable.getCurrentItemsCount()),
                            userTable.currentItemsCountProperty()));
            userTableVBox.getChildren().add(size);
        } else {
            deleteUser.setDisable(true);
            addUser.setDisable(true);
            adminBtn.setDisable(true);
            searchField.setDisable(true);

            deleteUser.setVisible(false);
            addUser.setVisible(false);
            adminBtn.setVisible(false);
            searchField.setVisible(false);


            final Label message = new Label("");

            GridPane grid = new GridPane();
            grid.setPadding(new Insets(10, 10, 10, 10));
            grid.setVgap(5);
            grid.setHgap(5);
            VBox dialogVbox = new VBox(20);
            Text login = new Text("Enter new login:");
            Text password = new Text("Enter your password:");
            GridPane.setConstraints(login,0,0);
            grid.getChildren().add(login);
            JFXTextField loginField = new JFXTextField();
            JFXPasswordField passwordField = new JFXPasswordField();
            GridPane.setConstraints(loginField, 1,0);
            grid.getChildren().add(loginField);
            GridPane.setConstraints(password, 0,1);
            grid.getChildren().add(password);
            GridPane.setConstraints(passwordField, 1,1);
            grid.getChildren().add(passwordField);
            Text passwordN = new Text("Enter a new password:");
            JFXPasswordField passwordFieldN = new JFXPasswordField();
            GridPane.setConstraints(passwordN, 0,2);
            grid.getChildren().add(passwordN);
            GridPane.setConstraints(passwordFieldN, 1,2);
            grid.getChildren().add(passwordFieldN);
            JFXButton changeBtn = new JFXButton("Change");

            GridPane.setConstraints(changeBtn, 1,3);

            changeBtn.setStyle("    -jfx-button-type: RAISED;\n" +
                    "    -fx-background-color: #FF6026;\n" +
                    "    -fx-text-fill: white;");
            grid.getChildren().add(changeBtn);
            GridPane.setConstraints(message, 0,4);
            grid.getChildren().add(message);

            changeBtn.setOnAction((ActionEvent e) -> modifyUserFunction(message, loginField, passwordField, passwordFieldN));
            userTableVBox.getChildren().addAll(grid);
        }
    }

    private void modifyUserFunction(Label message, JFXTextField loginField, JFXPasswordField oldPasswordField,JFXPasswordField passwordField) {
        int a =0;
        if (!loginField.getText().trim().equals("")) {
            a++;
            try {
                RequestModifyUserLogin loginModif = new RequestModifyUserLogin(currentUserLogin, loginField.getText());
                requestManager.sendRequest(loginModif);
                message.setText("Successfull Changes");
                message.setTextFill(Color.rgb(30, 220, 30));
                Session.setProperty("user", loginField.getText());
                currentUserLogin = loginField.getText();

            } catch (Exception e1) {
                e1.printStackTrace();
                message.setText("Error");
                message.setTextFill(Color.rgb(210, 39, 30));
            }

            loginField.clear();

        } if (!passwordField.getText().trim().equals("") && !oldPasswordField.getText().trim().equals("")) {
            a++;
            try {
                RequestModifyUserPassword pswdModif = new RequestModifyUserPassword(currentUserLogin, oldPasswordField.getText(), passwordField.getText());
                requestManager.sendRequest(pswdModif);
                message.setText("Successfull Changes");
                message.setTextFill(Color.rgb(30, 220, 30));
            } catch (Exception e1) {
                e1.printStackTrace();
                message.setText("Error");
                message.setTextFill(Color.rgb(210, 39, 30));
            }

            passwordField.clear();
            oldPasswordField.clear();

        } if (a==0) {
            message.setText("No Changes");
            message.setTextFill(Color.rgb(210, 39, 30));
        }
    }

    private EventHandler<ActionEvent>  addUser(Stage primaryStage) {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                final Label message = new Label("");
                final Stage dialog = new Stage();
                //dialog.initStyle(new StageStyle());
                GridPane grid = new GridPane();
                grid.setPadding(new Insets(10, 10, 10, 10));
                grid.setVgap(5);
                grid.setHgap(5);
                dialog.initModality(Modality.APPLICATION_MODAL);
                dialog.initOwner(primaryStage);
                VBox dialogVbox = new VBox(20);
                Text login = new Text("Enter a login:");
                Text password = new Text("Enter a password:");
                CheckBox adminCB = new CheckBox();
                Text adminLabel = new Text("is Admin?");
                GridPane.setConstraints(login,0,0);
                grid.getChildren().add(login);
                JFXTextField loginField = new JFXTextField();
                JFXPasswordField passwordField = new JFXPasswordField();
                GridPane.setConstraints(loginField, 1,0);
                grid.getChildren().add(loginField);
                GridPane.setConstraints(password, 0,1);
                grid.getChildren().add(password);
                GridPane.setConstraints(passwordField, 1,1);
                grid.getChildren().add(passwordField);
                JFXButton addBtn = new JFXButton("Add");
                GridPane.setConstraints(adminLabel, 0,2);
                grid.getChildren().add(adminLabel);
                GridPane.setConstraints(adminCB, 1,2);
                grid.getChildren().add(adminCB);
                GridPane.setConstraints(addBtn, 1,3);
                addBtn.setStyle("    -jfx-button-type: RAISED;\n" +
                        "    -fx-background-color: #FF6026;\n" +
                        "    -fx-text-fill: white;");
                grid.getChildren().add(addBtn);
                addBtn.setOnAction((ActionEvent e) -> addUserFunction(message, loginField, passwordField, dialog, adminCB));
                Scene dialogScene = new Scene(dialogVbox, 300, 200);
                dialog.setScene(dialogScene);
                passwordField.setOnAction((ActionEvent e) -> addUserFunction(message, loginField, passwordField ,dialog, adminCB));
                dialogVbox.getChildren().addAll(message, grid);
                dialog.show();
            }
        };
    }

    private void addUserFunction(Label message, JFXTextField loginField, JFXPasswordField passwordField, Stage dialog, CheckBox cb) {
        if (passwordField.getText().trim().equals("")) {
            message.setText("You have to set a password");
            message.setTextFill(Color.rgb(210, 39, 30));
        } else if (loginField.getText().trim().equals("")) {
            message.setText("You have to set a login");
            message.setTextFill(Color.rgb(210, 39, 30));
        } else {
            try {
                RequestAddUser addRequest = new RequestAddUser(loginField.getText(), passwordField.getText());
                requestManager.sendRequest(addRequest);

                if (cb.isSelected()){
                    RequestSetAdmin requestSetAdmin = new RequestSetAdmin(loginField.getText(), true);
                    requestManager.sendRequest(requestSetAdmin);
                }
                onLoadTest("");
                dialog.close();

            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }


    private void returnAction(Object actionEvent) {
        try {
            sceneManager.setScenes(MainController.class, 1500, 800);
        } catch (ExceptionSceneNotFound exceptionSceneNotFound) {
            exceptionSceneNotFound.printStackTrace();
        }
    }

    @Override
    public void onCreate() {
        sceneManager = SceneManager.getInstance();
        
    }

    @Override
    public void onStart() {
    	try {
            Config config = Config.getInstance();
            requestManager = RequestManagerFlyweightFactory.getInstance().getRequestManager(InetAddress.getByName(config.getIP()), config.getPort());
        } catch (ExceptionPortInvalid | UnknownHostException exceptionPortInvalid) {
            exceptionPortInvalid.printStackTrace();
        }
        onLoadTest("");
    }

    @Override
    protected void onUpdate() {

    }

    @Override
    public void onStop() {

    }


    private static final class UserInfo extends RecursiveTreeObject<UserInfo> {
        final StringProperty login;
        final JFXButton delete;
        final BooleanProperty admin;

        UserInfo(String login) {
            this.login = new SimpleStringProperty(login);
            this.delete = new JFXButton("Delete");
            delete.setStyle("    -jfx-button-type: RAISED;\n" +
                    "    -fx-background-color: #FF6026;\n" +
                    "    -fx-text-fill: white;");
            this.admin = new SimpleBooleanProperty(false);
        }

        UserInfo(User u) {
            this.login = new SimpleStringProperty(u.getLogin());
            this.delete = new JFXButton("Delete");
            delete.setStyle("    -jfx-button-type: RAISED;\n" +
                    "    -fx-background-color: #FF6026;\n" +
                    "    -fx-text-fill: white;");
            this.admin = new SimpleBooleanProperty(u.isAdmin());
        }

        UserInfo(String login, boolean isAdmin) {
            this.login = new SimpleStringProperty(login);
            this.delete = new JFXButton("Delete");
            delete.setStyle("    -jfx-button-type: RAISED;\n" +
                    "    -fx-background-color: #FF6026;\n" +
                    "    -fx-text-fill: white;");
            this.admin = new SimpleBooleanProperty(isAdmin);

        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            UserInfo userInfo = (UserInfo) o;
            return Objects.equals(login, userInfo.login);
        }

        BooleanProperty getAdmin() {
            return admin;
        }

        public String getLogin() {
            return login.get();
        }

        public StringProperty loginProperty() {
            return login;
        }

        @Override
        public String toString() {
            return "UserInfo{" +
                    "login=" + login +
                    ", admin=" + admin +
                    '}';
        }
    }
}

