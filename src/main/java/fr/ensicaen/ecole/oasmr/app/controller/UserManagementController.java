package fr.ensicaen.ecole.oasmr.app.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import fr.ensicaen.ecole.oasmr.app.view.SceneManager;
import fr.ensicaen.ecole.oasmr.lib.PropertiesFactory;
import fr.ensicaen.ecole.oasmr.supervisor.request.RequestManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

public class UserManagementController implements Initializable {
    private SceneManager sceneManager;
    private RequestManager requestManager;
    private Properties p;

    @FXML
    JFXButton modifyUser;

    @FXML
    JFXButton addUser;

    @FXML
    JFXButton deleteUser;

    @FXML
    JFXButton applyChangesButton;

    @FXML
    JFXTextField userLogin;

    @FXML
    JFXPasswordField userPassword;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        sceneManager = SceneManager.getInstance();

    }


    public void applyChanges(ActionEvent actionEvent) {

    }
}
