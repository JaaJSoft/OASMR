package fr.ensicaen.ecole.oasmr.app.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import fr.ensicaen.ecole.oasmr.app.view.SceneManager;
import fr.ensicaen.ecole.oasmr.app.view.exception.ExceptionSceneNotFound;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    private SceneManager sceneManager;

    @FXML
    Text loginError;

    @FXML
    JFXTextField loginUser;

    @FXML
    JFXPasswordField loginPassword;

    @FXML
    JFXButton loginConnect;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sceneManager = SceneManager.getInstance();
    }


    public void connect(ActionEvent actionEvent) {
        if(loginUser.getText() == null || loginUser.getText().trim().isEmpty()){
            loginError.setText("No username");
        }else if(loginPassword.getText() == null || loginPassword.getText().trim().isEmpty()){
            loginError.setText("No password");
            loginUser.setDisableAnimation(true);
        }else{
            loginError.setText("");
            try {
                sceneManager.setScenes("Main");
            } catch (ExceptionSceneNotFound exceptionSceneNotFound) {
                exceptionSceneNotFound.printStackTrace();
            }
        }
    }
}
