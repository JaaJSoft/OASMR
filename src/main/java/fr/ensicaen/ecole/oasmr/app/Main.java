package fr.ensicaen.ecole.oasmr.app;

import fr.ensicaen.ecole.oasmr.app.controller.LoginController;
import fr.ensicaen.ecole.oasmr.app.controller.MainController;
import fr.ensicaen.ecole.oasmr.app.controller.UserManagementController;
import fr.ensicaen.ecole.oasmr.app.view.SceneManager;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("OASMR");
        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("img/OASMR.png")));

        SceneManager sceneManager = SceneManager.getInstance();

        try {
            sceneManager.addScene(new LoginController(400, 450));
            sceneManager.addScene(new MainController(1500, 800));
            sceneManager.addScene(new UserManagementController(1500, 800));

            sceneManager.setScenes(LoginController.class);
            sceneManager.show();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static void main(String[] args) {
        launch(args);
    }


}
