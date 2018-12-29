package fr.ensicaen.ecole.oasmr.app.view.tree;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class GroupItem extends GenericTreeItem<NodeItem> {

    public GroupItem(String name, ObservableList<NodeItem> subObjects) {
        super(name, subObjects);
    }

    public GroupItem(String name) {
        super(name, FXCollections.observableArrayList());
    }

    public String toString(){
        return getName();
    }

}
