package fr.ensicaen.ecole.oasmr.lib.network;

import fr.ensicaen.ecole.oasmr.lib.network.exception.ExceptionPortInvalid;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class ServerTest {

    @Test(expected = ExceptionPortInvalid.class)
    public void portTestInvalidNegative() throws IOException, ExceptionPortInvalid {
        Server c1 = new Server(-2, new ServerRunnableEcho());
    }

    @Test(expected = ExceptionPortInvalid.class)
    public void portTestInvalidSup() throws IOException, ExceptionPortInvalid {
        Server c1 = new Server(1000000, new ServerRunnableEcho());
    }

    @Test
    public void portValid() throws IOException, ExceptionPortInvalid {
        Server c1 = new Server(6585, new ServerRunnableEcho());
        assertEquals(6585, c1.getPort());
    }
}