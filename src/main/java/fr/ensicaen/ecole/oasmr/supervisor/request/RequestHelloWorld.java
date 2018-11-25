package fr.ensicaen.ecole.oasmr.supervisor.request;

import fr.ensicaen.ecole.oasmr.lib.command.Command;

import java.io.Serializable;

public class RequestHelloWorld extends Command {
    @Override
    public Serializable execute(Object... params) {
        return "Hello world from Supervisor";
    }

    @Override
    public String toString() {
        return "Hello World";
    }
}
