package fr.ensicaen.ecole.oasmr.lib.system;

import org.junit.Before;
import org.junit.Test;
import oshi.PlatformEnum;
import oshi.SystemInfo;

import static org.junit.Assert.assertEquals;

public class CommandGetSystemTest {

    private CommandGetSystem c;
    private PlatformEnum os;

    @Before
    public void setUp() {
        c = new CommandGetSystem();
        os = SystemInfo.getCurrentPlatformEnum();
    }

    @Test
    public void execute() throws Exception {
        System.out.println(os);
        assertEquals(os.toString(), c.execute());
    }


}
