package fr.ensicaen.ecole.oasmr.app.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import fr.ensicaen.ecole.oasmr.app.Config;
import fr.ensicaen.ecole.oasmr.app.view.SceneManager;
import fr.ensicaen.ecole.oasmr.app.view.View;
import fr.ensicaen.ecole.oasmr.app.view.exception.ExceptionSceneNotFound;
import fr.ensicaen.ecole.oasmr.lib.PropertiesFactory;
import fr.ensicaen.ecole.oasmr.lib.network.exception.ExceptionPortInvalid;
import fr.ensicaen.ecole.oasmr.supervisor.auth.request.RequestAddUser;
import fr.ensicaen.ecole.oasmr.supervisor.auth.request.RequestAuthentication;
import fr.ensicaen.ecole.oasmr.supervisor.auth.request.RequestSetAdmin;
import fr.ensicaen.ecole.oasmr.supervisor.request.RequestManager;
import fr.ensicaen.ecole.oasmr.supervisor.request.RequestManagerFlyweightFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Properties;
import java.util.ResourceBundle;

public class LoginController extends View {

    private SceneManager sceneManager;
    private RequestManager requestManager;
    private Properties p;

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

    public LoginController(int width, int height) throws IOException {
        super("Login", width, height);
    }

    private boolean checkInput() { //TODO CHECK PORT NOT VALID
        if (IPServer.getText() == null || IPServer.getText().trim().isEmpty()) {
            loginError.setText("No IP");
            return false;
        } else if (portNumber.getText() == null || portNumber.getText().trim().isEmpty()) {
            loginError.setText("No port");
            return false;
        }else if (Integer.parseInt(portNumber.getText())<0 || Integer.parseInt(portNumber.getText())>65536){
            loginError.setText("Unvalid port");
            return false;
        } else if (loginUser.getText() == null || loginUser.getText().trim().isEmpty()) {
            loginError.setText("No username");
            return false;
        } else if (loginPassword.getText() == null || loginPassword.getText().trim().isEmpty()) {
            loginError.setText("No password");
            loginUser.setDisableAnimation(true);
            return false;
        }
        return true;
    }

    //TODO use Validator
    private void connect(ActionEvent actionEvent) throws UnknownHostException, ExceptionPortInvalid {
        if (checkInput()) {
            loginError.setText("");
            Config config = Config.getInstance();
            config.setIP(IPServer.getText());
            config.setPort(Integer.parseInt(portNumber.getText()));

            requestManager = RequestManagerFlyweightFactory.getInstance().getRequestManager(InetAddress.getByName(IPServer.getText()), Integer.parseInt(portNumber.getText()));

            RequestAddUser r = new RequestAddUser("admin", "jeej");
            RequestSetAdmin ra = new RequestSetAdmin("admin", true);
            try {
                requestManager.sendRequest(r);
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                requestManager.sendRequest(ra);
            } catch (Exception e) {
                e.printStackTrace();
            }

            RequestAuthentication requestAuthentication = new RequestAuthentication(loginUser.getText(), loginPassword.getText());
            try {
                if ((boolean) requestManager.sendRequest(requestAuthentication)) {
                    try {
                        sceneManager.setScenes(UserManagementController.class);
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

    @Override
    public void onCreate() {
        loginConnect.setDefaultButton(true);
        loginConnect.setOnAction(actionEvent -> {
            try {
                connect(actionEvent);
            } catch (UnknownHostException | ExceptionPortInvalid e) {
                e.printStackTrace(); //TODO print an error on screen
            }
        });
        sceneManager = SceneManager.getInstance();
    }

    @Override
    public void onStart() {
        Config c = Config.getInstance();
        IPServer.setText(c.getIP());
        portNumber.setText(String.valueOf(c.getPort()));
    }

    @Override
    public void onStop() {

    }
}
