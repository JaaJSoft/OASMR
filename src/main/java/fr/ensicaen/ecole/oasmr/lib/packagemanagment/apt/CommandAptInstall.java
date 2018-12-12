package fr.ensicaen.ecole.oasmr.lib.packagemanagment.apt;

import fr.ensicaen.ecole.oasmr.lib.ProcessBuilderUtil;
import fr.ensicaen.ecole.oasmr.lib.command.Command;
import fr.ensicaen.ecole.oasmr.lib.packagemanagment.apt.exceptions.ExceptionAptInstallFailure;

import java.io.IOException;
import java.io.Serializable;

public class CommandAptInstall extends Command {


    private String packageName;

    public CommandAptInstall(String packageName) {
        this.packageName = packageName;
    }

    @Override
    public Serializable execute(Object... params) throws Exception {
        ProcessBuilder processBuilder = new ProcessBuilder("sudo", "apt", "install", "-y", packageName);
        try {
            Process p = processBuilder.start();
            p.waitFor();
            int ret = p.exitValue();
            switch (ret) {
                case 100:
                    throw new ExceptionAptInstallFailure(ProcessBuilderUtil.getOutputError(p));
                default:
                    return ProcessBuilderUtil.getOutput(p);
                //Il peut ne pas y avoir de résultat à la recherche meme si ça se passe bien
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return e;
        }
    }

    @Override
    public String toString() {
        return "sudo" + " " + "apt" + " " + "install" + " " + packageName;
    }
}
