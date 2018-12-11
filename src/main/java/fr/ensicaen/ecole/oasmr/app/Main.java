package fr.ensicaen.ecole.oasmr.app;

import fr.ensicaen.ecole.oasmr.app.view.SceneManagerFlyweight;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        SceneManagerFlyweight sceneManager = SceneManagerFlyweight.getInstance();
        try{
            sceneManager.addScene("Login", 600, 400);
            sceneManager.addScene("Main", 1000, 600);
            sceneManager.setScenes("Login");
            sceneManager.show();
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    public static void main(String[] args) {
        launch(args);
    }


}
