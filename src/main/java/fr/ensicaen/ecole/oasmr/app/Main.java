package fr.ensicaen.ecole.oasmr.app;

import com.jfoenix.controls.JFXButton;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Hello World!");
        Parent root = FXMLLoader.load(getClass().getResource("/fr/ensicaen/ecole/oasmr/app/Main.fxml"));
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }


}
