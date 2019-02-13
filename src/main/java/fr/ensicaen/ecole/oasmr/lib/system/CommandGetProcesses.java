package fr.ensicaen.ecole.oasmr.lib.system;

import fr.ensicaen.ecole.oasmr.lib.command.Command;
import oshi.hardware.GlobalMemory;
import oshi.software.os.OSProcess;
import oshi.software.os.OperatingSystem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class CommandGetProcesses extends Command {

    @Override
    public Serializable execute(Object... params) throws Exception {
        int limit = params.length == 0 ?  5 : (int) params[0];
        OperatingSystem os = SystemInfoSingleton.getInstance().getOperatingSystem();
        GlobalMemory mem = SystemInfoSingleton.getInstance().getHardware().getMemory();
        List<OSProcess> procs = Arrays.asList(os.getProcesses(limit, OperatingSystem.ProcessSort.CPU));
        List<HashMap<String, String>> allProcesses = new ArrayList<>();
        for (int i = 0; i < procs.size(); i++) {
            OSProcess p = procs.get(i);
            HashMap<String, String> procInfo = new HashMap<>();
            procInfo.put("PID", String.valueOf(p.getProcessID()));
            procInfo.put("CPU", String.valueOf((100d * (p.getKernelTime() + p.getUserTime()) / p.getUpTime())));
            procInfo.put("MEM", String.valueOf(100d * p.getResidentSetSize() / mem.getTotal()));
            procInfo.put("NAME", p.getName());
            procInfo.put("PPID", String.valueOf(p.getParentProcessID()));
            allProcesses.add(procInfo);
        }
        return allProcesses.toArray();
    }

    @Override
    public String toString() {
        return "Command get processes";
    }
}
