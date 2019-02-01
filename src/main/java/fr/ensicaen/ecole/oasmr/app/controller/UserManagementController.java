package fr.ensicaen.ecole.oasmr.app.controller;

import com.jfoenix.controls.*;
import com.jfoenix.controls.cells.editors.TextFieldEditorBuilder;
import com.jfoenix.controls.cells.editors.base.GenericEditableTreeTableCell;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import fr.ensicaen.ecole.oasmr.app.Config;
import fr.ensicaen.ecole.oasmr.app.view.SceneManager;
import fr.ensicaen.ecole.oasmr.app.view.exception.ExceptionSceneNotFound;
import fr.ensicaen.ecole.oasmr.lib.network.exception.ExceptionPortInvalid;
import fr.ensicaen.ecole.oasmr.supervisor.auth.User;
import fr.ensicaen.ecole.oasmr.supervisor.auth.request.RequestAddUser;
import fr.ensicaen.ecole.oasmr.supervisor.auth.request.RequestGetAdmin;
import fr.ensicaen.ecole.oasmr.supervisor.auth.request.RequestGetLoginList;
import fr.ensicaen.ecole.oasmr.supervisor.auth.request.RequestModifyUserLogin;
import fr.ensicaen.ecole.oasmr.supervisor.request.RequestManager;
import fr.ensicaen.ecole.oasmr.supervisor.request.RequestManagerFlyweightFactory;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.List;
import java.util.ResourceBundle;

public class UserManagementController implements Initializable {
    private SceneManager sceneManager;
    private RequestManager requestManager;
    private List<String> userList;
    private ObservableList<UserInfo> data;

    @FXML
    JFXButton returnPrev;

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



        userTableVBox.getChildren().clear();

        RequestGetLoginList r = new RequestGetLoginList();

        try {
            userList = (List<String>) requestManager.sendRequest(r);
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

/*
        JFXTreeTableColumn<UserInfo, Boolean> adminCol = new JFXTreeTableColumn<>("is Admin");
        adminCol.setPrefWidth(150);
        adminCol.setCellFactory(CheckBoxTreeTableCell.forTreeTableColumn(adminCol));
        adminCol.setCellValueFactory(param -> {
                return new SimpleBooleanProperty(param.getValue().getValue().admin);
        });
*/


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

        final TreeTableColumn<UserInfo, Boolean> column = new TreeTableColumn<>();
        column.setCellFactory(CheckBoxTreeTableCell.forTreeTableColumn(column));
        column.setCellValueFactory(param -> param.getValue().getValue().admin);

/*
        adminCol.setCellFactory((TreeTableColumn<UserInfo, JFXCheckBox> param) -> new CheckBoxTreeTableCell<>(
                new JFXCheckBox()));



        TreeTableColumn<UserInfo,Boolean> columnWeight=new TreeTableColumn<>("Admin");
        // case TreeTableColumn (uncomment to run)
        columnWeight.setCellFactory(CheckBoxTreeTableCell.forTreeTableColumn(columnWeight));

        // case Callback:
        columnWeight.setCellFactory(
                CheckBoxTreeTableCell.forTreeTableColumn(
                        (Integer param) -> data.get(param).getAdmin()));

        columnWeight.setCellValueFactory(cellData -> cellData.getValue().getValue().getAdmin());
        columnWeight.setPrefWidth(150);
*/

        ObservableList<UserInfo> users = FXCollections.observableArrayList();

        for (String user : userList) {
            boolean admin = false;
            try {
                admin = (boolean) requestManager.sendRequest(new RequestGetAdmin(user));
            } catch (Exception e) {
                e.printStackTrace();
            }
            users.add(new UserInfo(user, admin));
        }

        //test

        users.add(new UserInfo("Oui", true));

        //test end


        for (UserInfo u : users){
            System.out.println(u);
        }

        final TreeItem<UserInfo> root = new RecursiveTreeItem<>(users, RecursiveTreeObject::getChildren);

        JFXTreeTableView userTable = new JFXTreeTableView(root);
        userTable.setShowRoot(false);
        userTable.setEditable(true);
        userTable.getColumns().setAll(userCol, column);

        userTableVBox.getChildren().add(userTable);

/*
        JFXButton groupButton = new JFXButton("Group");
        groupButton.setOnAction((action) -> new Thread(() -> userTable.group(userCol)).start());
        userTableVBox.getChildren().add(groupButton);

        JFXButton unGroupButton = new JFXButton("unGroup");
        unGroupButton.setOnAction((action) -> userTable.unGroup(userCol));
        userTableVBox.getChildren().add(unGroupButton);
*/

        JFXTextField filterField = new JFXTextField();
        userTableVBox.getChildren().add(filterField);

        Label size = new Label();

        size.textProperty()
                .bind(Bindings.createStringBinding(() -> String.valueOf(userTable.getCurrentItemsCount()),
                        userTable.currentItemsCountProperty()));
        userTableVBox.getChildren().add(size);
    }

    public void addUser(ActionEvent actionEvent) {
        new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                final Label message = new Label("");
                final Stage dialog = new Stage();
                //HBox hb = new HBox();
                GridPane grid = new GridPane();
                grid.setPadding(new Insets(10, 10, 10, 10));
                grid.setVgap(5);
                grid.setHgap(5);
                dialog.initModality(Modality.APPLICATION_MODAL);
                dialog.initOwner(new Stage());
                VBox dialogVbox = new VBox(20);
                Text login = new Text("Enter a login:");
                Text password = new Text("Enter a password:");
                GridPane.setConstraints(login,0,0);
                grid.getChildren().add(login);
                TextField loginField = new TextField();
                PasswordField passwordField = new PasswordField();
                GridPane.setConstraints(loginField, 1,0);
                grid.getChildren().add(loginField);
                GridPane.setConstraints(password, 0,1);
                grid.getChildren().add(password);
                GridPane.setConstraints(passwordField, 1,1);
                grid.getChildren().add(passwordField);
                Scene dialogScene = new Scene(dialogVbox, 300, 200);
                dialog.setScene(dialogScene);
                passwordField.setOnAction((ActionEvent e) -> {
                        if (passwordField.getText().trim().equals("")) {
                            message.setText("New password is incorrect!");
                            message.setTextFill(Color.rgb(210, 39, 30));
                        } else {
                            //message.setText("Your password has been changed");
                            //message.setTextFill(Color.rgb(21, 117, 84));
                            try {
                                RequestAddUser addRequest = new RequestAddUser(loginField.getText(), passwordField.getText());
                                requestManager.sendRequest(addRequest);
                            } catch (Exception e1) {
                                e1.printStackTrace();
                            }
                        }

                });


                //hb.getChildren().addAll(newPasswordField, oldPasswordField);
                dialogVbox.getChildren().addAll(message, /*hb,*/ grid);
                dialog.show();
            }
        };
    }


    public void returnAction(ActionEvent actionEvent) {
        try {
            sceneManager.setScenes("Login"); //TODO: Main.fxml, login is only for test
        } catch (ExceptionSceneNotFound exceptionSceneNotFound) {
            exceptionSceneNotFound.printStackTrace();
        }
    }



    private static final class UserInfo extends RecursiveTreeObject<UserInfo> {
        final StringProperty login;
        final JFXButton delete;
        final BooleanProperty admin;

        UserInfo(String login) {
            this.login = new SimpleStringProperty(login);
            this.delete = new JFXButton("");
            this.admin = new SimpleBooleanProperty(false);
        }

        UserInfo(User u) {
            this.login = new SimpleStringProperty(u.getLogin());
            this.delete = new JFXButton("");
            this.admin = new SimpleBooleanProperty(u.isAdmin());
        }

        UserInfo(String login, boolean isAdmin) {
            this.login = new SimpleStringProperty(login);
            this.delete = new JFXButton("");
            this.admin = new SimpleBooleanProperty(isAdmin);

        }

        BooleanProperty getAdmin() {
            return admin;
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

