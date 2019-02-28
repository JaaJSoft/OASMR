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

package fr.ensicaen.ecole.oasmr.app.gui.tree;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public abstract class GenericTreeItem<T extends GenericTreeItem<?>> {

    private String name;

    private ObservableList<T> subObjects;

    public GenericTreeItem(String name, ObservableList<T> subObjects) {
        this.subObjects = subObjects;
        this.name = name;
    }

    public GenericTreeItem(String name) {
        this.subObjects = FXCollections.observableArrayList();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ObservableList<T> getSubObjects() {
        return subObjects;
    }

    public String toString(){
        return getName();
    }

}
