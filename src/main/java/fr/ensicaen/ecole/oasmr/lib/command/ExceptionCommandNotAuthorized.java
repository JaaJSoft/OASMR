package fr.ensicaen.ecole.oasmr.lib.command;

public class ExceptionCommandNotAuthorized extends Exception {

    public ExceptionCommandNotAuthorized(String message) {
        super(message);
    }
}
