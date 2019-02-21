
package fr.ensicaen.ecole.oasmr.lib.packagemanagment.pip;

import fr.ensicaen.ecole.oasmr.lib.ProcessBuilderUtil;
import fr.ensicaen.ecole.oasmr.lib.command.Command;
import fr.ensicaen.ecole.oasmr.lib.packagemanagment.pip.exceptions.PipException;

import java.io.IOException;
import java.io.Serializable;

public class CommandPipWheel extends Command {

    private final String packageName;

    public CommandPipWheel(String packageName) { this.packageName = packageName; }

    @Override
    public Serializable execute(Object... params) throws Exception {
        ProcessBuilder processBuilder = new ProcessBuilder("pip", "wheel", packageName);
        try {
            return PipUtil.executeDefault(processBuilder);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return e;
        }
    }

    @Override
    public String toString() {
        return "pip " + "wheel " + packageName;
    }
}
