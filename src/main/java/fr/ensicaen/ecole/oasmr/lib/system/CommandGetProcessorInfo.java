package fr.ensicaen.ecole.oasmr.lib.system;

import fr.ensicaen.ecole.oasmr.lib.command.Command;

import oshi.hardware.CentralProcessor;

import java.io.Serializable;

public class CommandGetProcessorInfo extends Command {
    @Override
    public Serializable execute(Object... params) throws Exception {
        CentralProcessor processor = SystemInfoSingleton.getHardware().getProcessor();
        return new int[]{
                processor.getPhysicalPackageCount(),
                processor.getPhysicalProcessorCount(),
                processor.getLogicalProcessorCount()
        };
    }

    @Override
    public String toString() {
        return "Command get processor info";
    }
}
