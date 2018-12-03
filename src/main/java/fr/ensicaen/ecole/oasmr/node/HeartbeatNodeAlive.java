package fr.ensicaen.ecole.oasmr.node;

import fr.ensicaen.ecole.oasmr.lib.command.Command;
import fr.ensicaen.ecole.oasmr.lib.network.Client;
import fr.ensicaen.ecole.oasmr.lib.network.exception.ExceptionCannotDisconnect;
import fr.ensicaen.ecole.oasmr.lib.network.exception.ExceptionConnectionFailure;
import fr.ensicaen.ecole.oasmr.lib.network.exception.ExceptionPortInvalid;
import fr.ensicaen.ecole.oasmr.lib.network.util;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class HeartbeatNodeAlive extends Command {
    private InetAddress supervisorAddress;
    private Client c;

    public HeartbeatNodeAlive(InetAddress supervisorAddress, int port) throws UnknownHostException, ExceptionPortInvalid {
        this.supervisorAddress = supervisorAddress;
        c = new Client(InetAddress.getByName("127.0.0.1"), port);
    }

    @Override
    public Serializable execute(Object... n) {
        try {
            c.connect();
            c.disconnect();
        } catch (ExceptionConnectionFailure | ExceptionCannotDisconnect exceptionConnectionFailure) {
            exceptionConnectionFailure.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        return null;
    }
}
