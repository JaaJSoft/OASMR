package fr.ensicaen.ecole.oasmr.lib.system;

import fr.ensicaen.ecole.oasmr.lib.command.Command;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.HardwareAbstractionLayer;

import java.io.Serializable;

public class CommandGetCpuUsage extends Command {
    @Override
    public Serializable execute(Object... params) throws Exception {
        HardwareAbstractionLayer hal = SystemInfoSingleton.getInstance().getHardware();
        return hal.getProcessor().getSystemCpuLoadTicks();
    }

    @Override
    public String toString() {
        return "Command get Cpu usage";
    }
}
