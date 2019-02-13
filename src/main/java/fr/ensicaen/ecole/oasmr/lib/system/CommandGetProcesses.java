package fr.ensicaen.ecole.oasmr.lib.system;

import fr.ensicaen.ecole.oasmr.lib.command.Command;
import oshi.software.os.OSProcess;
import oshi.software.os.OperatingSystem;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class CommandGetProcesses extends Command {

    @Override
    public Serializable execute(Object... params) throws Exception {
        OperatingSystem os = SystemInfoSingleton.getInstance().getOperatingSystem();
        List<OSProcess> procs = Arrays.asList(os.getProcesses(5, OperatingSystem.ProcessSort.CPU));
        for (int i = 0; i < procs.size() && i < 5; i++) {
            HashMap<String, Float> procInfo = new HashMap<>();
        }
        return 1;
    }

    @Override
    public String toString() {
        return "Command get processes";
    }
}
