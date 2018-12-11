package fr.ensicaen.ecole.oasmr.app.view;

import fr.ensicaen.ecole.oasmr.app.view.exception.ExceptionFXMLNotFound;
import fr.ensicaen.ecole.oasmr.app.view.exception.ExceptionSceneAlrdeadyExists;
import fr.ensicaen.ecole.oasmr.app.view.exception.ExceptionSceneNotFound;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

public class SceneManagerFlyweight {

    //TODO : Virer ce truc immonde :
    private String path = "/fr/ensicaen/ecole/oasmr/app/";
    private HashMap<String, Scene> scenes;
    private Stage primaryStage;
    private static SceneManagerFlyweight ourInstance = new SceneManagerFlyweight();

    private SceneManagerFlyweight(){
        scenes = new HashMap<>();
        primaryStage = new Stage();
    }

    public static SceneManagerFlyweight getInstance(){
        return ourInstance;
    }

    public void addScene(String fxml, int width, int height) throws IOException, ExceptionSceneAlrdeadyExists {
        System.out.println("ADD : "  +  fxml);
        if(!scenes.containsKey(fxml)){
            Parent root = FXMLLoader.load(getClass().getResource(path + fxml + ".fxml"));
            Scene s = new Scene(root, width, height);
            scenes.put(fxml, s);
        }else{
            throw new ExceptionSceneAlrdeadyExists();
        }
    }

    public Scene getScene(String fxml) throws ExceptionSceneNotFound {
        if(scenes.containsKey(fxml)){
            return scenes.get(fxml);
        }else{
            throw new ExceptionSceneNotFound();
        }
    }

    public void setScenes(String fxml) throws ExceptionSceneNotFound {
        if(scenes.containsKey(fxml)){
            primaryStage.setScene(getScene(fxml));
        }else{
            throw new ExceptionSceneNotFound();
        }
    }


    public void show() {
        primaryStage.show();
    }

}
