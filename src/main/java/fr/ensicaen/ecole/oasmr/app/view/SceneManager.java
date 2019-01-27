package fr.ensicaen.ecole.oasmr.app.view;

import fr.ensicaen.ecole.oasmr.app.view.exception.ExceptionSceneAlrdeadyExists;
import fr.ensicaen.ecole.oasmr.app.view.exception.ExceptionSceneNotFound;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

public class SceneManager {

    //TODO : Virer ce truc immonde :
    private String path = "/fr/ensicaen/ecole/oasmr/app/";
    private HashSet<View> views = new HashSet<>();
    private View activeView;
    private Stage primaryStage;
    private static SceneManager ourInstance = new SceneManager();

    private SceneManager() {
        primaryStage = new Stage();
    }

    public static SceneManager getInstance() {
        return ourInstance;
    }

    public void addScene(View view) throws IOException, ExceptionSceneAlrdeadyExists {
        views.add(view);
        view.onCreate();
    }

    public View getView(Class<? extends View> klazz) throws ExceptionSceneNotFound {
        for (View v : views) {
            if (v.getClass().equals(klazz)) {
                return v;
            }
        }
        throw new ExceptionSceneNotFound();

    }

    public Scene getScene(Class<? extends View> klazz) throws ExceptionSceneNotFound {
        for (View v : views) {
            if (v.getClass().equals(klazz)) {
                return v.scene;
            }
        }
        throw new ExceptionSceneNotFound();

    }

    public void setScenes(Class<? extends View> klazz) throws ExceptionSceneNotFound {
        if (activeView != null)
            activeView.onStop();
        View v = getView(klazz);
        v.onStart();
        activeView = v;
        primaryStage.setScene(v.scene);
    }


    public void show() {
        primaryStage.show();
    }

}
