package fr.ensicaen.ecole.oasmr.lib.command;

import java.io.Serializable;

public abstract class Command implements Serializable {

    public abstract Serializable execute(Object... params) throws Exception;

    public abstract String toString();
}
