package fr.ensicaen.ecole.oasmr.lib.system;

import org.junit.Before;
import org.junit.Test;

import oshi.hardware.NetworkIF;

import java.util.Arrays;
import java.util.HashMap;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class CommandGetNetworkInterfacesTest {

    private CommandGetNetworkInterfaces c;
    private NetworkIF[] networkIFS;


    @Before
    public void setUp() {
        c = new CommandGetNetworkInterfaces();
        networkIFS = SystemInfoSingleton.getHardware().getNetworkIFs();
    }

    @Test
    public void execute() throws Exception {
        HashMap[] netIfsFromCommand = (HashMap[]) c.execute();
        assertEquals(networkIFS.length, netIfsFromCommand.length);
        for (int i = 0; i < networkIFS.length; i++) {
            NetworkIF n = networkIFS[i];
            assertEquals(n.getName(), netIfsFromCommand[i].get("NAME"));
            assertEquals(n.getMacaddr(), netIfsFromCommand[i].get("MAC"));
            assertEquals(Arrays.toString(n.getIPv4addr()), netIfsFromCommand[i].get("IPv4"));
            assertEquals(Arrays.toString(n.getIPv6addr()), netIfsFromCommand[i].get("IPv6"));
        }
    }

    @Test
    public void executeFailure() throws Exception {
        HashMap[] netIfsFromCommand = (HashMap[]) c.execute();
        assertNotEquals(0, netIfsFromCommand.length);
    }

}
