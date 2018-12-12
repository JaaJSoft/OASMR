package fr.ensicaen.ecole.oasmr.lib.packagemanagment.apt;

import fr.ensicaen.ecole.oasmr.lib.ProcessBuilderUtil;
import fr.ensicaen.ecole.oasmr.lib.command.Command;
import fr.ensicaen.ecole.oasmr.lib.packagemanagment.apt.exceptions.ExceptionAptUpgradeFailure;

import java.io.IOException;
import java.io.Serializable;

//ToDO: check if it work

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
            //System.out.println(ProcessBuilderUtil.getOutput(p));
            switch (ret) {
                case 0:
                    return ProcessBuilderUtil.getOutput(p);

                default:
                    throw new ExceptionAptUpgradeFailure(ProcessBuilderUtil.getOutputError(p));

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
