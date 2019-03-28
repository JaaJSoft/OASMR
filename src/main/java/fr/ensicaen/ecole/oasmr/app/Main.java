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

package fr.ensicaen.ecole.oasmr.app;

import fr.ensicaen.ecole.oasmr.app.controller.LoginController;
import fr.ensicaen.ecole.oasmr.app.controller.MainController;
import fr.ensicaen.ecole.oasmr.app.controller.UserManagementController;
import fr.ensicaen.ecole.oasmr.app.view.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {


        SceneManager sceneManager = SceneManager.getInstance();

        try {
            sceneManager.addScene(new LoginController());
            sceneManager.addScene(new MainController());
            sceneManager.addScene(new UserManagementController());
            sceneManager.setScenes(LoginController.class, 400, 450);
            sceneManager.show();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static void main(String[] args) {
        launch(args);
    }


}
