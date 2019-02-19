package fr.ensicaen.ecole.oasmr.lib.system;

import oshi.SystemInfo;

import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.OperatingSystem;

public class SystemInfoSingleton {

    private static SystemInfo instance = null;

    private static SystemInfo getInstance(){
        if(instance == null) {
            instance = new SystemInfo();
        }
        return instance;
    }

    public static HardwareAbstractionLayer getHardware(){
        return getInstance().getHardware();
    }

    public static OperatingSystem getOperatingSystem(){
        return getInstance().getOperatingSystem();
    }

}
