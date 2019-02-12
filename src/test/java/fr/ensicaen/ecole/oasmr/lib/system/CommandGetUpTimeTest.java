package fr.ensicaen.ecole.oasmr.lib.system;

import org.junit.Before;
import org.junit.Test;
import oshi.hardware.HardwareAbstractionLayer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class CommandGetUpTimeTest {

    private CommandGetUptime c;
    private HardwareAbstractionLayer hal;

    @Before
    public void setUp() {
        c = new CommandGetUptime();
        hal = SystemInfoSingleton.getInstance().getHardware();
    }

    @Test
    public void execute() throws Exception {
        assertEquals(hal.getProcessor().getSystemUptime(), c.execute());
    }

    @Test
    public void executeFailure() throws Exception {
        assertNotEquals(0, c.execute());
    }

}
