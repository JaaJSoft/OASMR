package fr.ensicaen.ecole.oasmr.lib.network;

import fr.ensicaen.ecole.oasmr.lib.network.exception.ExceptionPortInvalid;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.junit.Assert.*;

public class ClientTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test(expected = ExceptionPortInvalid.class)
    public void portTestInvalidNegative() throws UnknownHostException, ExceptionPortInvalid {
        Client c1 = new Client(InetAddress.getByName("1.1.1.1"), -2);
    }

    @Test(expected = ExceptionPortInvalid.class)
    public void portTestInvalidSup() throws UnknownHostException, ExceptionPortInvalid {
        Client c1 = new Client(InetAddress.getByName("1.1.1.1"), 1000000);
    }

    @Test(expected = UnknownHostException.class)
    public void addressInvalid() throws UnknownHostException, ExceptionPortInvalid {
        Client c1 = new Client(InetAddress.getByName("1.1.11.1.1"), 6585);
    }

    @Test
    public void portValid() throws UnknownHostException, ExceptionPortInvalid {
        Client c1 = new Client(InetAddress.getByName("1.11.1.1"), 6585);
        assertEquals(6585, c1.getPort());
    }

    @Test
    public void addressValid() throws UnknownHostException, ExceptionPortInvalid {
        InetAddress ip = InetAddress.getByName("1.11.1.1");
        Client c1 = new Client(ip, 6585);
        assertEquals(ip.toString(), c1.getIp().toString());
    }

    @Test
    public void connect() {
    }
}