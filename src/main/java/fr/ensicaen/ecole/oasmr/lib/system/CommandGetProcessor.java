package fr.ensicaen.ecole.oasmr.lib.system;

import fr.ensicaen.ecole.oasmr.lib.command.Command;

import oshi.hardware.HardwareAbstractionLayer;

import java.io.Serializable;

public class CommandGetProcessor extends Command {
    @Override
    public Serializable execute(Object... params) throws Exception {
        HardwareAbstractionLayer hal = SystemInfoSingleton.getInstance().getHardware();
        return hal.getProcessor().toString();
    }

    @Override
    public String toString() {
        return "Command get processor";
    }
}
