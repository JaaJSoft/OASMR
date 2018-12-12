package fr.ensicaen.ecole.oasmr.lib.packagemanagment.apt;

import fr.ensicaen.ecole.oasmr.lib.ProcessBuilderUtil;
import fr.ensicaen.ecole.oasmr.lib.command.Command;
import fr.ensicaen.ecole.oasmr.lib.packagemanagment.apt.exceptions.ExceptionAptPackageNotFound;

import java.io.IOException;
import java.io.Serializable;

public class CommandAptShow extends Command {
    private String packageName;

    public CommandAptShow(String packageName) {
        this.packageName = packageName;
    }

    @Override
    public Serializable execute(Object... params) throws Exception {
        ProcessBuilder processBuilder = new ProcessBuilder("apt", "show", packageName);
        try {
            Process p = processBuilder.start();
            p.waitFor();
            int ret = p.exitValue();

            switch (ret) {
                case 0:
                    return ProcessBuilderUtil.getOutput(p);
                default:
                    throw new ExceptionAptPackageNotFound(ProcessBuilderUtil.getOutputError(p));
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return e;
        }
    }

    @Override
    public String toString() {
        return "apt" + " " + "show" + " " + packageName;
    }
}

