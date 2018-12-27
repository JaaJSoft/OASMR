
package fr.ensicaen.ecole.oasmr.lib.packagemanagment.apt;

import fr.ensicaen.ecole.oasmr.lib.ProcessBuilderUtil;
import fr.ensicaen.ecole.oasmr.lib.command.Command;
import fr.ensicaen.ecole.oasmr.lib.packagemanagment.apt.exceptions.ExceptionAptFailGettingList;
import fr.ensicaen.ecole.oasmr.lib.packagemanagment.apt.exceptions.ExceptionAptPackageNotFound;

import java.io.IOException;
import java.io.Serializable;

public class CommandAptList extends Command {

    public CommandAptList() {
    }

    @Override
    public Serializable execute(Object... params) throws Exception {
        throw new ExceptionAptFailGettingList("DOES NOT WORK !");
        /*ProcessBuilder processBuilder = new ProcessBuilder("apt", "list");
        try {
            Process p = processBuilder.start();
            p.waitFor();
            int ret = p.exitValue();
            switch (ret) {
                case 0:
                    String output = ProcessBuilderUtil.getOutput(p);
                    System.out.println(output);
                    return output;
                default:
                    throw new ExceptionAptFailGettingList(ProcessBuilderUtil.getOutputError(p));
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return e;
        }
        */
    }

    @Override
    public String toString() {
        return "apt list";
    }
}
