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
