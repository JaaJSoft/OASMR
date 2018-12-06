package fr.ensicaen.ecole.oasmr.lib;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ProcessBuilderUtil {

    private static BufferedReader getOutputStream(Process p) {
        return new BufferedReader(new InputStreamReader(p.getInputStream()));
    }

    private static BufferedReader getErrorStream(Process p) {
        return new BufferedReader(new InputStreamReader(p.getErrorStream()));
    }

    private static String StreamToString(BufferedReader ouput) throws IOException {
        StringBuilder builder = new StringBuilder();
        String line = null;
        while ((line = ouput.readLine()) != null) {
            builder.append(line);
            builder.append(System.getProperty("line.separator"));
        }
        return builder.toString();
    }

    public static String getOutputError(Process p) throws IOException {
        BufferedReader ouput = getErrorStream(p);
        return StreamToString(ouput);
    }

    public static String getOutput(Process p) throws IOException {
        BufferedReader ouput = getOutputStream(p);
        return StreamToString(ouput);
    }


}
