package fr.ensicaen.ecole.oasmr.app.view;

import fr.ensicaen.ecole.oasmr.app.controller.LoginController;
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


    @Test (expected = ExceptionSceneNotFound.class)
    public void getNonExistingScene() throws ExceptionSceneNotFound {
        sceneManager.getScene(LoginController.class);
    }

    @Test (expected = ExceptionSceneAlrdeadyExists.class)
    public void addExistingScene() throws IOException, ExceptionSceneAlrdeadyExists {
        sceneManager.addScene(new LoginController(0, 0));
        sceneManager.addScene(new LoginController(0, 0));
    }


}