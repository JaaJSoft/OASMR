package fr.ensicaen.ecole.oasmr.supervisor.node;

public class Tag {
    private String name;

    public Tag(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
