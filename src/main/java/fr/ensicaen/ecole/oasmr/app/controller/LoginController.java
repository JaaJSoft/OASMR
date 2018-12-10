package fr.ensicaen.ecole.oasmr.app.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

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

    }


    public void connect(ActionEvent actionEvent) {
        if(loginUser.getText() == null || loginUser.getText().trim().isEmpty()){
            loginError.setText("No username");
        }else if(loginPassword.getText() == null || loginPassword.getText().trim().isEmpty()){
            loginError.setText("No password");
        }else{
            loginError.setText("");
            Stage stage = new Stage();
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/fr/ensicaen/ecole/oasmr/app/Main.fxml"));
                Scene scene = new Scene(root, 1000, 600);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
