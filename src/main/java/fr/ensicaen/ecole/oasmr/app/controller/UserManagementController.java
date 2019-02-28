package fr.ensicaen.ecole.oasmr.app.controller;

import com.jfoenix.controls.*;
import com.jfoenix.controls.cells.editors.TextFieldEditorBuilder;
import com.jfoenix.controls.cells.editors.base.GenericEditableTreeTableCell;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import fr.ensicaen.ecole.oasmr.app.Config;
import fr.ensicaen.ecole.oasmr.app.view.SceneManager;
import fr.ensicaen.ecole.oasmr.app.view.View;
import fr.ensicaen.ecole.oasmr.app.view.exception.ExceptionSceneNotFound;
import fr.ensicaen.ecole.oasmr.lib.network.exception.ExceptionPortInvalid;
import fr.ensicaen.ecole.oasmr.supervisor.auth.User;
import fr.ensicaen.ecole.oasmr.supervisor.auth.request.*;
import fr.ensicaen.ecole.oasmr.supervisor.request.RequestManager;
import fr.ensicaen.ecole.oasmr.supervisor.request.RequestManagerFlyweightFactory;
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
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTreeTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class UserManagementController extends View implements Initializable{
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
    VBox userTableVBox;

    public UserManagementController(int width, int height) throws IOException {
        super("UserManagement", width, height);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            Config config = Config.getInstance();
            requestManager = RequestManagerFlyweightFactory.getInstance().getRequestManager(InetAddress.getByName(config.getIP()), config.getPort());
        } catch (ExceptionPortInvalid | UnknownHostException exceptionPortInvalid) {
            exceptionPortInvalid.printStackTrace();
        }
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

        addUser.setOnAction(addUser(new Stage()));
        returnPrev.setOnAction(actionEvent -> {
            try {
                returnAction(actionEvent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

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

        final TreeTableColumn<UserInfo, Boolean> column = new TreeTableColumn<>("admin");
        column.setCellFactory(CheckBoxTreeTableCell.forTreeTableColumn(column));
        column.setCellValueFactory(param -> param.getValue().getValue().admin);


        final TreeTableColumn<UserInfo, JFXButton> deleteCol = new TreeTableColumn<>("delete User");
        deleteCol.setCellValueFactory(param -> (ObservableValue<JFXButton>) param.getValue().getValue().delete);

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

        userTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        userTable.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        deleteUser.setOnAction(event ->{
            for (Object u : userTable.getSelectionModel().getSelectedItems()){
                System.out.println(u);
                if (u instanceof TreeItem) {
                    if (((TreeItem)u).getValue() instanceof UserInfo) {
                        RequestDeleteUser deleteRequest = new RequestDeleteUser(((UserInfo)((TreeItem)u).getValue()).getLogin());
                        try {
                            requestManager.sendRequest(deleteRequest);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            //reload table when users are delete...
        });

        userTableVBox.getChildren().add(userTable);

        JFXTextField filterField = new JFXTextField();
        userTableVBox.getChildren().add(filterField);

        Label size = new Label();

        size.textProperty()
                .bind(Bindings.createStringBinding(() -> String.valueOf(userTable.getCurrentItemsCount()),
                        userTable.currentItemsCountProperty()));
        userTableVBox.getChildren().add(size);
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

                dialog.close();

            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }


    private void returnAction(Object actionEvent) throws Exception{
        try {
            sceneManager.setScenes(MainController.class);
        } catch (ExceptionSceneNotFound exceptionSceneNotFound) {
            exceptionSceneNotFound.printStackTrace();
        }
    }

    @Override
    public void onCreate() {
        onLoadTest();
    }

    @Override
    public void onStart() {

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

