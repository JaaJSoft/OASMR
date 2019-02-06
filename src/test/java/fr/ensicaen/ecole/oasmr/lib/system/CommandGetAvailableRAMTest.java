package fr.ensicaen.ecole.oasmr.lib.system;

import org.junit.Before;
import org.junit.Test;
import oshi.hardware.HardwareAbstractionLayer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class CommandGetAvailableRAMTest {

    private CommandGetAvailableRAM c;
    private HardwareAbstractionLayer hal;

    @Before
    public void setUp() {
        c = new CommandGetAvailableRAM();
        hal = SystemInfoSingleton.getInstance().getHardware();
    }

    @Test
    public void execute() throws Exception {
        assertEquals(hal.getMemory().getAvailable(), c.execute());
    }

    @Test
    public void executeFailure() throws Exception {
        assertNotEquals(-2, c.execute());
    }


}

