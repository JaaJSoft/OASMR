package fr.ensicaen.ecole.oasmr.lib;

import fr.ensicaen.ecole.oasmr.lib.command.Command;

import java.io.*;

public class CommandProcessBuilderExample extends Command {

    @Override
    public Serializable execute(Object... params) {
        ProcessBuilder processBuilder = new ProcessBuilder("echo", "jeej");
        try {
            Process p = processBuilder.start();
            p.waitFor();
            int ret = p.exitValue();
            switch (ret) {
                case 0:
                    return ProcessBuilderUtil.getString(p);
                case 100:
                    return new ExceptionJeejException("jeej");
                default:
                    return 0;
            }
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