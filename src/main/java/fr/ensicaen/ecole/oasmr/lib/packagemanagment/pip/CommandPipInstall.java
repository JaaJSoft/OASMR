
package fr.ensicaen.ecole.oasmr.lib.packagemanagment.pip;

import fr.ensicaen.ecole.oasmr.lib.ProcessBuilderUtil;
import fr.ensicaen.ecole.oasmr.lib.command.Command;
import fr.ensicaen.ecole.oasmr.lib.packagemanagment.pip.exceptions.PipException;

import java.io.IOException;
import java.io.Serializable;

public class CommandPipInstall extends Command {

    private String packageName;

    public CommandPipInstall(String packageName) { this.packageName = packageName; }

    @Override
    public Serializable execute(Object... params) throws Exception {
        ProcessBuilder processBuilder = new ProcessBuilder("pip", "install", packageName);
        try {
            Process p = processBuilder.start();
            p.waitFor();
            int ret = p.exitValue();
            switch (ret) {
                case 0:
                    //System.out.println(ProcessBuilderUtil.getOutput(p));
                    return ProcessBuilderUtil.getOutput(p);

                default:
                    throw new PipException(ProcessBuilderUtil.getOutputError(p));

            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return e;
        }
    }

    @Override
    public String toString() {
        return "pip" + " " + "install" + " " + packageName;
    }
}
