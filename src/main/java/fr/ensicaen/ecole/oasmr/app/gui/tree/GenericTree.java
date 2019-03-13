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

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.util.function.Function;

import static java.util.stream.Collectors.toList;

public class GenericTree<T> {

    private TreeView<T> treeView;

    private Function<T, ObservableList<? extends T>> childrenGetter ;

    public GenericTree(Function<T, ObservableList<? extends T>> childrenGetter, T... rootItem) {

        this.childrenGetter = childrenGetter ;

        TreeItem<T> root = new TreeItem<>();
        treeView = new TreeView<>(root);
        for(T item : rootItem){
            root.getChildren().add(createTreeItem(item));
        }

        //TODO : cell factory in parameter
        treeView.setCellFactory(tv -> new TreeCell<T>() {


            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                textProperty().unbind();
                if (empty) {
                    setText("");
                } else {
                    setText(item.toString());
                }
            }

        });

        treeView.setShowRoot(false);

    }

    public TreeView<T> getTreeView() {
        return treeView ;
    }

    private TreeItem<T> createTreeItem(T t) {
        TreeItem<T> item = new TreeItem<>(t);
        childrenGetter.apply(t).stream().map(this::createTreeItem).forEach(item.getChildren()::add);
        childrenGetter.apply(t).addListener((ListChangeListener.Change<? extends T> change) -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    item.getChildren().addAll(change.getAddedSubList().stream()
                            .map(this::createTreeItem).collect(toList()));
                }
                if (change.wasRemoved()) {
                    item.getChildren().removeIf(treeItem -> change.getRemoved()
                            .contains(treeItem.getValue()));
                }
            }
        });

        return item ;
    }

}
