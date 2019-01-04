package fr.ensicaen.ecole.oasmr.app.beans;

public class Node {

    private String name;
    private int id;

    public Node(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String toString(){
        return name;
    }

}
