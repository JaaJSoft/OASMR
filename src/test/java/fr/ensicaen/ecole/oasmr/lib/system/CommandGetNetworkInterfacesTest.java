package fr.ensicaen.ecole.oasmr.lib.system;

import org.junit.Before;
import org.junit.Test;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.NetworkIF;
import oshi.software.os.OperatingSystem;

public class CommandGetNetworkInterfacesTest {

    private CommandGetNetworkInterfaces c;
    private NetworkIF[] networkIFS;


    @Before
    public void setUp() {
        c = new CommandGetNetworkInterfaces();
        networkIFS = SystemInfoSingleton.getInstance().getHardware().getNetworkIFs();
    }

    @Test
    public void execute() throws Exception {

    }

    @Test
    public void executeFailure() throws Exception {

    }

}
