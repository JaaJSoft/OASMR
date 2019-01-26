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

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.Objects;

public abstract class View {

    protected Scene scene;
    protected String fxml;
    protected int width;
    protected int height;

    public View(String fxml, int width, int height) throws IOException {
        this.fxml = fxml;
        this.width = width;
        this.height = height;
        Parent root = FXMLLoader.load(getClass().getResource(fxml + ".fxml"));
        scene = new Scene(root, width, height);
    }

    /**
     * Called only once
     */
    public abstract void onCreate();

    /**
     * Called at every SceneManager::setScene()
     */
    public abstract void onStart();

    public abstract void onStop();

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public String getFxml() {
        return fxml;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        View view = (View) o;
        return width == view.width &&
                height == view.height &&
                Objects.equals(fxml, view.fxml);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fxml, width, height);
    }
}
