package fr.ensicaen.ecole.oasmr.lib.system;

import fr.ensicaen.ecole.oasmr.lib.command.Command;
import oshi.hardware.CentralProcessor;
import oshi.hardware.HardwareAbstractionLayer;

import java.io.Serializable;
import java.lang.reflect.Array;

public class CommandGetProcessorInfo extends Command {
    @Override
    public Serializable execute(Object... params) throws Exception {
        HardwareAbstractionLayer hal = SystemInfoSingleton.getInstance().getHardware();
        CentralProcessor processor = hal.getProcessor();
        int processorInfo[] = {
                processor.getPhysicalPackageCount(),
                processor.getPhysicalProcessorCount(),
                processor.getLogicalProcessorCount()};
        return processorInfo;
    }

    @Override
    public String toString() {
        return "Command get processor info";
    }
}
