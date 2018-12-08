package fr.ensicaen.ecole.oasmr.supervisor.request;

import fr.ensicaen.ecole.oasmr.lib.network.exception.ExceptionPortInvalid;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.junit.Assert.*;

public class RequestManagerFlyweightFactoryTest {

    @Test
    public void getRequestManager() throws UnknownHostException, ExceptionPortInvalid {
        RequestManagerFlyweightFactory factory = RequestManagerFlyweightFactory.getInstance();
        RequestManager r = factory.getRequestManager(InetAddress.getByName("192.168.1.1"), 55555);
        RequestManager r2 = factory.getRequestManager(InetAddress.getByName("192.168.1.1"), 55555);
        assertEquals(1, factory.getNbRequestManager());
    }
}