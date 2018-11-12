package fr.ensicaen.ecole.oasmr.node;

import fr.ensicaen.ecole.oasmr.lib.network.Client;
import fr.ensicaen.ecole.oasmr.lib.network.exception.ExceptionCannotDisconnect;
import fr.ensicaen.ecole.oasmr.lib.network.exception.ExceptionConnectionFailure;
import fr.ensicaen.ecole.oasmr.lib.network.exception.ExceptionPortInvalid;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class HeartbeatNodeAlive implements Runnable {
    private InetAddress supervisorAddress;
    private Client c;

    public HeartbeatNodeAlive(InetAddress supervisorAddress) throws UnknownHostException, ExceptionPortInvalid {
        this.supervisorAddress = supervisorAddress;
        c = new Client(InetAddress.getByName("127.0.0.1"), 55555);
    }

    @Override
    public void run() {
        try {
            c.connect();
            c.disconnect();
        } catch (ExceptionConnectionFailure | ExceptionCannotDisconnect exceptionConnectionFailure) {
            exceptionConnectionFailure.printStackTrace();
        }
    }

}
