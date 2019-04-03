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
    protected List<View> subView;
    protected View parent = null;

    private String path = "/fr/ensicaen/ecole/oasmr/app/";

    public View(String fxml) throws IOException {
        this.fxml = fxml;
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(path + fxml + ".fxml"));
        fxmlLoader.setController(this);
        root = fxmlLoader.load();
        subView = new ArrayList<>();
    }

    public View(String fxml, View parent) throws IOException {
        this.fxml = fxml;
        this.parent = parent;
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(path + fxml + ".fxml"));
        fxmlLoader.setController(this);
        root = fxmlLoader.load();
        subView = new ArrayList<>();
    }

    /**
     * Called when the scene is add in the scene manager or in another view
     */
    public abstract void onCreate();


    /**
     * Start the view
     */
    protected abstract void onStart();

    /**
     * Called at every SceneManager::setScene()
     * Update all sub view before
     */
    public void onLoad() {
        onStart();
        for (View v : subView) {
            v.onLoad();
        }
    }

    public void update() {
        onUpdate();
        for (View v : subView) {
            v.update();
        }
    }

    /**
     * Called every n (5 default) seconds to update the view
     */
    protected abstract void onUpdate();

    public void onFinish() {
        for (View v : subView) {
            v.onFinish();
        }
        onStop();
    }

    protected abstract void onStop();

    public Scene getScene() {
        if (scene == null) {
            scene = new Scene((Parent) root);
        }
        return scene;
    }

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root){
        this.root = root;
    }

    public String getFxml() {
        return fxml;
    }

    public View getParent() {
        return parent;
    }

    public void addSubView(View v) {
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
        return Objects.hash(fxml);
    }
}
