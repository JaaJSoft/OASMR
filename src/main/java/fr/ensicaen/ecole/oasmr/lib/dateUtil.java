package fr.ensicaen.ecole.oasmr.lib;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class dateUtil {

    public static String getFormattedDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        return dtf.format(LocalDateTime.now());
    }
}
