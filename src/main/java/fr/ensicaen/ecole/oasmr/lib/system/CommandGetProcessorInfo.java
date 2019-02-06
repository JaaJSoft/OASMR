package fr.ensicaen.ecole.oasmr.lib.system;

import fr.ensicaen.ecole.oasmr.lib.command.Command;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.HardwareAbstractionLayer;

import java.io.Serializable;

public class CommandGetProcessorInfo extends Command {
    @Override
    public Serializable execute(Object... params) throws Exception {
        HardwareAbstractionLayer hal = SystemInfoSingleton.getInstance().getHardware();
        CentralProcessor processor = hal.getProcessor();
        return  processor.getPhysicalPackageCount() + " physical CPU package(s)" + "\n" +
                processor.getPhysicalProcessorCount() + " physical CPU core(s)" + "\n" +
                processor.getLogicalProcessorCount() + " logical CPU(s)";
    }

    @Override
    public String toString() {
        return null;
    }
}
