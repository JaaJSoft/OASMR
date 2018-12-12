package fr.ensicaen.ecole.oasmr.lib.packagemanagment.apt;

import fr.ensicaen.ecole.oasmr.lib.ProcessBuilderUtil;
import fr.ensicaen.ecole.oasmr.lib.command.Command;
import fr.ensicaen.ecole.oasmr.lib.packagemanagment.apt.exceptions.ExceptionPurgeFailure;

import java.io.IOException;
import java.io.Serializable;

public class CommandAptPurge extends Command {

    public CommandAptPurge() {
    }

    @Override
    public Serializable execute(Object... params) throws Exception {
        ProcessBuilder processBuilder = new ProcessBuilder("sudo", "apt", "purge", "-y");
        try {
            Process p = processBuilder.start();
            p.waitFor();
            int ret = p.exitValue();
            switch (ret) {
                case 0:
                    return ProcessBuilderUtil.getOutput(p);
                default:
                    throw new ExceptionPurgeFailure(ProcessBuilderUtil.getOutputError(p));

            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return e;
        }
    }

    @Override
    public String toString() {
        return "sudo" + " " + "apt" + " " + "purge";
    }
}
