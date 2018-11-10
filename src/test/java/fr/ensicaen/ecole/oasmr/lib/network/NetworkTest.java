package fr.ensicaen.ecole.oasmr.lib.network;


import fr.ensicaen.ecole.oasmr.lib.network.exception.ExceptionCannotDisconnect;
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
    private Server s;
    private Client c;
/*
    @Before
    public void setUp() throws UnknownHostException, ExceptionPortInvalid, ExceptionConnectionFailure {
        int port = 54322;
        Thread t = new Thread(() -> {
            try {
                s = new Server(port);
                s.start(() -> {
                    try {
                        Socket clientSocket = s.getServerSocket().accept();
                        String temp = (String) util.receiveSerializable(clientSocket);
                        util.sendSerializable(clientSocket, temp);
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                });
            } catch (IOException | ExceptionPortInvalid | InterruptedException e) {
                e.printStackTrace();
            }
        });
        c = new Client(InetAddress.getByName("127.0.0.1"), port);
        t.start();
        c.connect();
    }
*/
    @After
    public void tearDown() throws ExceptionCannotDisconnect, ExceptionServerRunnableNotEnded {
        c.disconnect();
        s.stop();
    }

    @Test
    public void sendStringTest() throws Exception {
        String test = "test";
        util.sendSerializable(c.getSocket(), test);
        String result = (String) util.receiveSerializable(c.getSocket());
        assertEquals(test, result);
    }

}