package fr.ensicaen.ecole.oasmr.lib.example;

import fr.ensicaen.ecole.oasmr.lib.ProcessBuilderUtil;
import fr.ensicaen.ecole.oasmr.lib.command.Command;

import java.io.*;

public class CommandEchoString extends Command {
    private final String message;

    public CommandEchoString(String message) {
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
        return "echo message";
    }
}