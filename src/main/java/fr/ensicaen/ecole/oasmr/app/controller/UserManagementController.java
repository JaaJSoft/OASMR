package fr.ensicaen.ecole.oasmr.app.controller;

import com.jfoenix.controls.*;
import com.jfoenix.controls.cells.editors.TextFieldEditorBuilder;
import com.jfoenix.controls.cells.editors.base.GenericEditableTreeTableCell;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import fr.ensicaen.ecole.oasmr.app.Config;
import fr.ensicaen.ecole.oasmr.app.view.SceneManager;
import fr.ensicaen.ecole.oasmr.lib.network.exception.ExceptionPortInvalid;
import fr.ensicaen.ecole.oasmr.supervisor.auth.User;
import fr.ensicaen.ecole.oasmr.supervisor.auth.request.RequestGetUsersList;
import fr.ensicaen.ecole.oasmr.supervisor.auth.request.RequestModifyUserLogin;
import fr.ensicaen.ecole.oasmr.supervisor.request.RequestManager;
import fr.ensicaen.ecole.oasmr.supervisor.request.RequestManagerFlyweightFactory;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.List;
import java.util.ResourceBundle;

public class UserManagementController implements Initializable {
    private SceneManager sceneManager;
    private RequestManager requestManager;
    private List<String> loginList;
    private ObservableList<UserInfo> data;

    @FXML
    JFXButton modifyUser;

    @FXML
    JFXButton deleteUser;

    @FXML
    JFXButton addUser;

    @FXML
    JFXButton applyChangesButton;

    @FXML
    JFXTextField userLogin;

    @FXML
    JFXPasswordField userPassword;

    @FXML
    VBox userTableVBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        sceneManager = SceneManager.getInstance();
        try {
            requestManager = RequestManagerFlyweightFactory.getInstance().getRequestManager(InetAddress.getByName(Config.ip), Config.port);
        } catch (ExceptionPortInvalid | UnknownHostException exceptionPortInvalid) {
            exceptionPortInvalid.printStackTrace();
        }
        onLoadTest();
    }


    public void applyChanges(ActionEvent actionEvent) {


    }

    private void onLoadTest() {
        RequestGetUsersList r = new RequestGetUsersList();

        try {
            loginList = (List<String>) requestManager.sendRequest(r);
        } catch (Exception e) {
            e.printStackTrace();
        }

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
                    e.printStackTrace();
                }
            }
        });


        ObservableList<UserInfo> users = FXCollections.observableArrayList();
        for (String s : loginList) {
            users.add(new UserInfo(s));
        }

        final TreeItem<UserInfo> root = new RecursiveTreeItem<>(users, RecursiveTreeObject::getChildren);

        JFXTreeTableView<UserInfo> userTable = new JFXTreeTableView(root);
        userTable.setShowRoot(false);
        userTable.setEditable(true);
        userTable.getColumns().setAll(userCol);

        userTableVBox.getChildren().add(userTable);


        JFXButton groupButton = new JFXButton("Group");
        groupButton.setOnAction((action) -> new Thread(() -> userTable.group(userCol)).start());
        userTableVBox.getChildren().add(groupButton);

        JFXButton unGroupButton = new JFXButton("unGroup");
        unGroupButton.setOnAction((action) -> userTable.unGroup(userCol));
        userTableVBox.getChildren().add(unGroupButton);

        JFXTextField filterField = new JFXTextField();
        userTableVBox.getChildren().add(filterField);

        Label size = new Label();

        filterField.textProperty().addListener((o, oldVal, newVal) -> {
            userTable.setPredicate(userProp -> {
                final UserInfo user = userProp.getValue();
                return user.login.get().contains(newVal)
                       /* || user.department.get().contains(newVal)
                        || user.userName.get().contains(newVal)*/;
            });
        });
        size.textProperty()
                .bind(Bindings.createStringBinding(() -> String.valueOf(userTable.getCurrentItemsCount()),
                        userTable.currentItemsCountProperty()));
        userTableVBox.getChildren().add(size);
    }

    private static final class UserInfo extends RecursiveTreeObject<UserInfo> {
        final StringProperty login;

        UserInfo(String login) {
            this.login = new SimpleStringProperty(login);
        }
    }
}

