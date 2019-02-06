package fr.ensicaen.ecole.oasmr.lib.system;

import org.junit.Before;
import org.junit.Test;
import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class CommandGetTotalRAMTest {

    private CommandGetTotalRAM c;
    private HardwareAbstractionLayer hal;

    @Before
    public void setUp() {
        c = new CommandGetTotalRAM();
        hal = SystemInfoSingleton.getInstance().getHardware();
    }

    @Test
    public void execute() throws Exception {
        assertEquals(hal.getMemory().getTotal(), c.execute());
    }

    @Test
    public void executeFailure() throws Exception {
        assertNotEquals(-2, c.execute());
    }


}

