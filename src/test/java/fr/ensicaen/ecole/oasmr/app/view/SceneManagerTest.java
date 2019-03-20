/*
 *  Copyright (c) 2019. CCC-Development-Team
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

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
        /*sceneManager.addScene(new LoginController(0, 0));
        sceneManager.addScene(new LoginController(0, 0));*/
    }


}