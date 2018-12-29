package fr.ensicaen.ecole.oasmr.app.view;

import fr.ensicaen.ecole.oasmr.app.view.exception.ExceptionSceneAlrdeadyExists;
import fr.ensicaen.ecole.oasmr.app.view.exception.ExceptionSceneNotFound;

import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.io.IOException;

public class SceneManagerTest extends ApplicationTest {

    private SceneManager sceneManager;

    @Override
    public void start (Stage stage) {
        sceneManager = SceneManager.getInstance();
    }

    @Test (expected = NullPointerException.class)
    public void addNonExistingFXML() throws IOException, ExceptionSceneAlrdeadyExists, NullPointerException {
           sceneManager.addScene("Nothing", 0, 0);

    }

    @Test (expected = ExceptionSceneNotFound.class)
    public void getNonExistingScene() throws ExceptionSceneNotFound {
        sceneManager.getScene("Nothing");
    }

    @Test (expected = ExceptionSceneAlrdeadyExists.class)
    public void addExistingScene() throws IOException, ExceptionSceneAlrdeadyExists {
        sceneManager.addScene("Default", 0, 0);
        sceneManager.addScene("Default", 0, 0);
    }


}