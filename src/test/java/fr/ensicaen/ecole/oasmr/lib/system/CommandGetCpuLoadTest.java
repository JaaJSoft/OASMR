package fr.ensicaen.ecole.oasmr.lib.system;

import org.junit.Before;
import org.junit.Test;
import oshi.hardware.HardwareAbstractionLayer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class CommandGetCpuLoadTest {

    private CommandGetCpuLoad c;
    private HardwareAbstractionLayer hal;

    @Before
    public void setUp() {
        c = new CommandGetCpuLoad();
        hal = SystemInfoSingleton.getInstance().getHardware();
    }

    @Test
    public void execute() throws Exception {
        assertEquals(hal.getProcessor().getSystemCpuLoadBetweenTicks(), c.execute());
    }

    @Test
    public void executeFailure() throws Exception {
        assertNotEquals(0, c.execute());
    }

}
