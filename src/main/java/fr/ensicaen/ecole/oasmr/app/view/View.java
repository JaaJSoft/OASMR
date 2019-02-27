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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class View {

    protected Scene scene;
    protected Node root;
    protected String fxml;
    protected int width;
    protected int height;
    protected List<View> subView;

    private String path = "/fr/ensicaen/ecole/oasmr/app/";

    public View(String fxml, int width, int height) throws IOException {
        this.fxml = fxml;
        this.width = width;
        this.height = height;
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(path + fxml + ".fxml"));
        fxmlLoader.setController(this);
        root = fxmlLoader.load();
        subView = new ArrayList<>();
    }

    public View(String fxml) throws IOException {
        this.fxml = fxml;
        this.width = 0;
        this.height = 0;
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(path + fxml + ".fxml"));
        fxmlLoader.setController(this);
        root = fxmlLoader.load();
        subView = new ArrayList<>();
    }

    /**
     * Called when the scene is add in the scene manager
     */
    public abstract void onCreate();


    /**
     * Called at every SceneManager::setScene()
     */
    protected abstract void onStart();

    /**
     * Update all sub view before
     */
    public void onStartView(){
        onStart();
        for (View v : subView) {
            v.onStart();
        }
    }

    public abstract void onStop();

    public Scene getScene() {
        if(scene == null){
            return new Scene((Parent) root, width, height);
        }else{
            return scene;
        }
    }

    public Node getRoot() {
        return root;
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

    public void addSubView(View v){
        v.onCreate();
        subView.add(v);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        View view = (View) o;
        return Objects.equals(fxml, view.fxml);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fxml, width, height);
    }
}
