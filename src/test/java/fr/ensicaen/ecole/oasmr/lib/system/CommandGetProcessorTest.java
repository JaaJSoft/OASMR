package fr.ensicaen.ecole.oasmr.lib.system;

import org.junit.Before;
import org.junit.Test;

import oshi.hardware.HardwareAbstractionLayer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class CommandGetProcessorTest {

    private CommandGetProcessor c;
    private HardwareAbstractionLayer hal;

    @Before
    public void setUp() {
        c = new CommandGetProcessor();
        hal = SystemInfoSingleton.getHardware();
    }

    @Test
    public void execute() throws Exception {
        assertEquals(hal.getProcessor().toString(), c.execute());
    }

    @Test
    public void executeFailure() throws Exception {
        assertNotEquals("", c.execute());
    }

}
