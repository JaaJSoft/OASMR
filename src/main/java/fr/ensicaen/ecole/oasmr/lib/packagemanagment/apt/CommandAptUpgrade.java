package fr.ensicaen.ecole.oasmr.lib.packagemanagment.apt;

import fr.ensicaen.ecole.oasmr.lib.ProcessBuilderUtil;
import fr.ensicaen.ecole.oasmr.lib.command.Command;
import fr.ensicaen.ecole.oasmr.lib.packagemanagment.apt.exceptions.ExceptionAptUpgradeFailure;

import java.io.IOException;
import java.io.Serializable;

public class CommandAptUpgrade extends Command {

    public CommandAptUpgrade() {

    }

    @Override
    public Serializable execute(Object... params) throws Exception {
        ProcessBuilder processBuilder = new ProcessBuilder("sudo", "apt", "upgrade");
        try {
            Process p = processBuilder.start();
            p.waitFor();
            int ret = p.exitValue();
            switch (ret) {
                case 100:
                    throw new ExceptionAptUpgradeFailure(ProcessBuilderUtil.getOutputError(p));
                default:
                    return ProcessBuilderUtil.getOutput(p);
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return e;
        }
    }

    @Override
    public String toString() {
        return "sudo" + " " +"apt" + " " + "upgrade";
    }
}
