package fr.ensicaen.ecole.oasmr.lib.system;

import oshi.SystemInfo;

public class SystemInfoSingleton {

    private static SystemInfo instance = null;

    public static SystemInfo getInstance(){
        if(instance == null) {
            instance = new SystemInfo();
        }
        return instance;
    }

}
