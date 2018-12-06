package fr.ensicaen.ecole.oasmr.lib;

import fr.ensicaen.ecole.oasmr.lib.command.Command;

import java.io.*;

public class CommandProcessBuilderExample extends Command {
    private String message;

    public CommandProcessBuilderExample(String message) {
        this.message = message;
    }

    @Override
    public Serializable execute(Object... params) throws Exception {
        ProcessBuilder processBuilder = new ProcessBuilder("echo", message);
        try {
            Process p = processBuilder.start();
            p.waitFor();
            int ret = p.exitValue();
            switch (ret) {
                case 0:
                    return ProcessBuilderUtil.getOutput(p);
                default:
                    throw new ExceptionJeejException(ProcessBuilderUtil.getOutputError(p));
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