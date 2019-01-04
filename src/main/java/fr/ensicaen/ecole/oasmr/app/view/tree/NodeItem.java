package fr.ensicaen.ecole.oasmr.app.view.tree;

import fr.ensicaen.ecole.oasmr.app.gui.GenericTreeItem;
import javafx.collections.FXCollections;

public class NodeItem extends GenericTreeItem<GenericTreeItem<?>> {

    private String name;

    private int id;

    public NodeItem(String name, int id) {
        super(name, FXCollections.emptyObservableList());
    }

    public int getId() {
        return id;
    }

    public String toString(){
        return getName();
    }

}
