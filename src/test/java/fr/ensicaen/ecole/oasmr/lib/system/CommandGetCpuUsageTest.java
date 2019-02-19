package fr.ensicaen.ecole.oasmr.lib.system;

import org.junit.Before;
import org.junit.Test;
import oshi.hardware.HardwareAbstractionLayer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class CommandGetCpuUsageTest {

    private CommandGetCpuUsage c;
    private HardwareAbstractionLayer hal;

    @Before
    public void setUp() {
        c = new CommandGetCpuUsage();
        hal = SystemInfoSingleton.getHardware();
    }

    @Test
    public void execute() throws Exception {
        long[] cpuUsage = hal.getProcessor().getSystemCpuLoadTicks();
        long[] cpuUsageFromCommand = (long[]) c.execute();
        assertEquals(cpuUsage.length, cpuUsageFromCommand.length);
        for(int i = 0; i < cpuUsage.length; i++){
            assertEquals(cpuUsage[i], cpuUsageFromCommand[i]);
        }
    }

    @Test
    public void executeFailure() throws Exception {
        long[] cpuUsageFromCommand = (long[]) c.execute();
        for (long usage : cpuUsageFromCommand) {
            assertNotEquals(-1, usage);
        }
    }

}
