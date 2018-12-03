package fr.ensicaen.ecole.oasmr.lib;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ProcessBuilderUtil {

    private static BufferedReader getOutput(Process p) {
        return new BufferedReader(new InputStreamReader(p.getInputStream()));
    }

    private static BufferedReader getError(Process p) {
        return new BufferedReader(new InputStreamReader(p.getErrorStream()));
    }

    public static String getString(Process p) throws IOException {
        BufferedReader ouput = getOutput(p);
        BufferedReader error = getError(p);
        StringBuilder builder = new StringBuilder();
        String line = null;
        while ((line = ouput.readLine()) != null) {
            builder.append(line);
            builder.append(System.getProperty("line.separator"));
        }

        while ((line = error.readLine()) != null) {
            builder.append(line);
            builder.append(System.getProperty("line.separator"));
        }
        return builder.toString();
    }

}
