package fr.ensicaen.ecole.oasmr.lib.network;


import fr.ensicaen.ecole.oasmr.lib.network.exception.ExceptionCannotDisconnect;
import fr.ensicaen.ecole.oasmr.lib.network.exception.ExceptionConnectionFailure;
import fr.ensicaen.ecole.oasmr.lib.network.exception.ExceptionPortInvalid;
import fr.ensicaen.ecole.oasmr.lib.network.exception.ExceptionServerRunnableNotEnded;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import static org.junit.Assert.*;

public class NetworkTest {
    private Server server;
    private Client c;

    @Before
    public void setUp() throws IOException, ExceptionPortInvalid, ExceptionConnectionFailure, InterruptedException {
        int port = 54324;
        Thread t = new Thread(() -> {
            try {
                Server s = new Server(port, new ServerRunnableEcho());
                s.start();
            } catch (IOException | ExceptionPortInvalid | InterruptedException | CloneNotSupportedException e) {
                e.printStackTrace();
            }
        });
        c = new Client(InetAddress.getByName("127.0.0.1"), port);
        t.start();
        Thread.sleep(1000);
        System.out.println("COnnexion");
        c.connect();
    }

    @After
    public void tearDown() throws ExceptionCannotDisconnect, ExceptionServerRunnableNotEnded {
        c.disconnect();
        //s.stop();
    }

    @Test
    public void sendStringTest() throws Exception {
        String test = "test";
        util.sendSerializable(c.getSocket(), test);
        String result = (String) util.receiveSerializable(c.getSocket());
        assertEquals(test, result);
    }

}
