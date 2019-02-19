package fr.ensicaen.ecole.oasmr.lib.system;

import fr.ensicaen.ecole.oasmr.lib.command.Command;

import java.io.Serializable;

public class CommandGetTotalRAM extends Command {
    @Override
    public Serializable execute(Object... params) throws Exception {
        return SystemInfoSingleton.getHardware().getMemory().getTotal();
    }

    @Override
    public String toString() {
        return "Command get total RAM";
    }
}
