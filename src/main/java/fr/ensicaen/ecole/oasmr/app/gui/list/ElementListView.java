package fr.ensicaen.ecole.oasmr.app.gui.list;
import com.jfoenix.controls.JFXListView;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;

public class ElementListView<K, T> extends JFXListView<T> {

    private K key;

    public ElementListView(K key, ObservableList<T> elements){
        this.key = key;
        super.setGroupnode(new Label(key.toString()));
        super.setItems(elements);
    }

    public K getKey() {
        return key;
    }


}
