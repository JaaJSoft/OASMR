package fr.ensicaen.ecole.oasmr.lib.system;

import org.junit.Before;
import org.junit.Test;

import oshi.hardware.GlobalMemory;
import oshi.software.os.OSProcess;
import oshi.software.os.OperatingSystem;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class CommandGetProcessesTest {

    private CommandGetProcesses c;
    private OperatingSystem os;
    private GlobalMemory mem;

    @Before
    public void setUp() {
        c = new CommandGetProcesses();
        os = SystemInfoSingleton.getInstance().getOperatingSystem();
        mem = SystemInfoSingleton.getInstance().getHardware().getMemory();
    }

    @Test
    public void execute() throws Exception {
        HashMap[] procsFromCommand = (HashMap[]) c.execute();
        List<OSProcess> procs = Arrays.asList(os.getProcesses(5, OperatingSystem.ProcessSort.CPU));
        assertEquals(procsFromCommand.length, procs.size());
        for (int i = 0; i < procs.size() && i < 5; i++) {
            OSProcess p = procs.get(i);
            assertEquals(String.valueOf(p.getProcessID()), procsFromCommand[i].get("PID"));
            //assertEquals(String.valueOf((100d * (p.getKernelTime() + p.getUserTime()) / p.getUpTime())),
            //        procsFromCommand[i].get("CPU"));
            //assertEquals(String.valueOf(100d * p.getResidentSetSize() / mem.getTotal()),
            //        procsFromCommand[i].get("MEM"));
            assertEquals(p.getName(), procsFromCommand[i].get("NAME"));
            assertEquals(String.valueOf(p.getParentProcessID()), procsFromCommand[i].get("PPID"));
        }
    }

    @Test
    public void executeFailure() throws Exception {
        HashMap[] procsFromCommand = (HashMap[]) c.execute();
        List<OSProcess> procs = Arrays.asList(os.getProcesses(5, OperatingSystem.ProcessSort.CPU));
        assertNotEquals(procsFromCommand.length, 0);
    }

}
