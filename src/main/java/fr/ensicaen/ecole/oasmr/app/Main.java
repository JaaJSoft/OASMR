package fr.ensicaen.ecole.oasmr.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("OASMR");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fr/ensicaen/ecole/oasmr/app/Login.fxml"));
        final Scene scene = new Scene(loader.load(),600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }


}
