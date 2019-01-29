package fr.ensicaen.ecole.oasmr.app.gui.list;
import com.jfoenix.controls.JFXListView;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;

public class ElementListView<T> extends JFXListView<T> {


    public ElementListView(ObservableList<T> elements){
        super.setGroupnode(new Label("ALL NODES"));
        super.setItems(elements);
        super.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        super.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
    }

}
