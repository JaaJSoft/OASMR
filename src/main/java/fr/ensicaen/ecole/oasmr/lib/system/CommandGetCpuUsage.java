package fr.ensicaen.ecole.oasmr.lib.system;

import fr.ensicaen.ecole.oasmr.lib.command.Command;

import java.io.Serializable;

public class CommandGetCpuUsage extends Command {
    @Override
    public Serializable execute(Object... params) throws Exception {
        return SystemInfoSingleton.getHardware().getProcessor().getSystemCpuLoadTicks();
    }

    @Override
    public String toString() {
        return "Command get Cpu usage";
    }
}
