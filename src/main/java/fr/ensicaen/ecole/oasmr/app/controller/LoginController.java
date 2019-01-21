package fr.ensicaen.ecole.oasmr.app.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import fr.ensicaen.ecole.oasmr.app.Config;
import fr.ensicaen.ecole.oasmr.app.view.SceneManager;
import fr.ensicaen.ecole.oasmr.app.view.exception.ExceptionSceneNotFound;
import fr.ensicaen.ecole.oasmr.lib.network.exception.ExceptionPortInvalid;
import fr.ensicaen.ecole.oasmr.supervisor.auth.request.RequestAddUser;
import fr.ensicaen.ecole.oasmr.supervisor.auth.request.RequestAuthentication;
import fr.ensicaen.ecole.oasmr.supervisor.request.RequestManager;
import fr.ensicaen.ecole.oasmr.supervisor.request.RequestManagerFlyweightFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    private SceneManager sceneManager;
    private RequestManager requestManager;

    @FXML
    Text loginError;

    @FXML
    JFXTextField loginUser;

    @FXML
    JFXPasswordField loginPassword;

    @FXML
    JFXButton loginConnect;

    @FXML
    JFXTextField IPServer;

    @FXML
    JFXTextField portNumber;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            requestManager = RequestManagerFlyweightFactory.getInstance().getRequestManager(InetAddress.getByName(Config.ip), Config.port);
        } catch (ExceptionPortInvalid | UnknownHostException exceptionPortInvalid) {
            exceptionPortInvalid.printStackTrace();
        }

        /* Only For Test: delete after */
        RequestAddUser r = new RequestAddUser("admin", "jeej");
        try {
            requestManager.sendRequest(r);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //End of test section

        sceneManager = SceneManager.getInstance();
    }


    public void connect(ActionEvent actionEvent) {
        if(loginUser.getText() == null || loginUser.getText().trim().isEmpty()){
            loginError.setText("No username");
        }else if(loginPassword.getText() == null || loginPassword.getText().trim().isEmpty()){
            loginError.setText("No password");
            loginUser.setDisableAnimation(true);
        }else{
            boolean correctAuth;
            loginError.setText("");
            RequestAuthentication requestAuthentication = new RequestAuthentication(loginUser.getText(), loginPassword.getText());
            try {
                correctAuth = (boolean) requestManager.sendRequest(requestAuthentication);
                if (correctAuth){
                    try {
                        Config.ip = IPServer.getText();
                        Config.port = Integer.parseInt(portNumber.getText());
                        sceneManager.setScenes("Main");
                    } catch (ExceptionSceneNotFound exceptionSceneNotFound) {
                        exceptionSceneNotFound.printStackTrace();
                    }
                } else {
                    loginError.setText("Authentication Failure");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
