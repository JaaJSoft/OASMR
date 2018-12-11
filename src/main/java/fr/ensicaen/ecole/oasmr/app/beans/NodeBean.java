package fr.ensicaen.ecole.oasmr.app.beans;

public class NodeBean {

    private Integer id;
    private String name;

    public NodeBean(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
