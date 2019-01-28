package fr.ensicaen.ecole.oasmr.lib.packagemanagment.pip;

import fr.ensicaen.ecole.oasmr.lib.ProcessBuilderUtil;
import fr.ensicaen.ecole.oasmr.lib.packagemanagment.pip.exceptions.PipException;

public class PipUtil {

    public static String executeDefault(ProcessBuilder p) throws Exception {
        Process process = p.start();
        process.waitFor();
        int ret = process.exitValue();
        switch (ret) {
            case 0:
                System.out.println(ProcessBuilderUtil.getOutput(process));
                return ProcessBuilderUtil.getOutput(process);

            default:
                System.out.println(ProcessBuilderUtil.getOutputError(process));
                throw new PipException(ProcessBuilderUtil.getOutputError(process));

        }
    }
}
