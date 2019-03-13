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

package fr.ensicaen.ecole.oasmr.app.gui.list;
import com.jfoenix.controls.JFXListView;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;

public class ElementListView<T> extends JFXListView<T> {

    public ElementListView(ObservableList<T> elements){
        setGroupnode(new Label("ALL NODES"));
        setItems(elements);
        getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
    }

}
