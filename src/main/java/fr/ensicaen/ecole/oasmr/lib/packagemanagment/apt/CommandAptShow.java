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
                case 100:
                    throw new ExceptionAptPackageNotFound(ProcessBuilderUtil.getOutputError(p));
                default: //théoriquement c'est que ca marche (il n'y a qu'une seule valeur de retour pour les erreurs)
                    return ProcessBuilderUtil.getOutput(p);
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

