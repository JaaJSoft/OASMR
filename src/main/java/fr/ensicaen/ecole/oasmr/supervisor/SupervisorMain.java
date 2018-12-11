package fr.ensicaen.ecole.oasmr.supervisor;

import fr.ensicaen.ecole.oasmr.lib.network.exception.ExceptionPortInvalid;

import java.io.IOException;

public class SupervisorMain {

    public static void main(String[] args) throws IOException, ExceptionPortInvalid, CloneNotSupportedException, InterruptedException {
        Supervisor s = new Supervisor(6969,40404);
        s.start();
    }
}
