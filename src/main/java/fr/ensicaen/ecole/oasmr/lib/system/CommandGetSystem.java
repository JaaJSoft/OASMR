package fr.ensicaen.ecole.oasmr.lib.system;

import fr.ensicaen.ecole.oasmr.lib.command.Command;
import oshi.SystemInfo;

import java.io.Serializable;

public class CommandGetSystem extends Command {
    @Override
    public Serializable execute(Object... params) throws Exception {
        return SystemInfo.getCurrentPlatformEnum().toString();
    }

    @Override
    public String toString() {
        return "Command Get System";
    }
}
