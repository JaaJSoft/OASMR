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
