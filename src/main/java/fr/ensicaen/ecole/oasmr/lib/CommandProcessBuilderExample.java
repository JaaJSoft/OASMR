package fr.ensicaen.ecole.oasmr.lib;

import fr.ensicaen.ecole.oasmr.lib.command.Command;

import java.io.IOException;
import java.io.Serializable;

public class CommandProcessBuilderExample extends Command {

    @Override
    public Serializable execute(Object... params) {
        ProcessBuilder processBuilder = new ProcessBuilder("echo", "jeej");
        try {
            Process p = processBuilder.start();
            p.waitFor();
            //System.out.println(p.getOutputStream());
            return p.exitValue();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return e;
        }
    }

    @Override
    public String toString() {
        return null;
    }
}