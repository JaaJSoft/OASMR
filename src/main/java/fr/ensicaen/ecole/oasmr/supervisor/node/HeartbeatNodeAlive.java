package fr.ensicaen.ecole.oasmr.supervisor.node;

import fr.ensicaen.ecole.oasmr.lib.command.Command;
import fr.ensicaen.ecole.oasmr.lib.network.Client;
import fr.ensicaen.ecole.oasmr.lib.network.exception.ExceptionCannotDisconnect;
import fr.ensicaen.ecole.oasmr.lib.network.exception.ExceptionConnectionFailure;
import fr.ensicaen.ecole.oasmr.lib.network.exception.ExceptionPortInvalid;
import fr.ensicaen.ecole.oasmr.lib.network.util;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class HeartbeatNodeAlive extends Command {
    private Client c;
    private int commandPort;

    public HeartbeatNodeAlive(InetAddress supervisorAddress, int port, int commandPort) throws UnknownHostException, ExceptionPortInvalid {
        c = new Client(supervisorAddress, port);
        this.commandPort = commandPort;
    }

    @Override
    public Serializable execute(Object... n) {
        try {
            c.connect();
            util.sendSerializable(c.getSocket(), commandPort);
            c.disconnect();
        } catch (ExceptionConnectionFailure | ExceptionCannotDisconnect | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        return null;
    }
}
