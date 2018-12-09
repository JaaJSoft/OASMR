package fr.ensicaen.ecole.oasmr.supervisor.node;

import fr.ensicaen.ecole.oasmr.lib.network.Client;
import fr.ensicaen.ecole.oasmr.lib.network.exception.ExceptionCannotDisconnect;
import fr.ensicaen.ecole.oasmr.lib.network.exception.ExceptionConnectionFailure;
import fr.ensicaen.ecole.oasmr.lib.network.exception.ExceptionPortInvalid;
import fr.ensicaen.ecole.oasmr.lib.network.util;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDate;

public class HeartbeatNodeAlive extends CommandNode {
    private Client c;
    private int commandPort;

    public HeartbeatNodeAlive(InetAddress supervisorAddress, int port, int commandPort) throws UnknownHostException, ExceptionPortInvalid {
        c = new Client(supervisorAddress, port);
        this.commandPort = commandPort;
    }


    @Override
    public Serializable execute(Node node) {
        try {
            c.connect();
            util.sendSerializable(c.getSocket(), commandPort);
            c.disconnect();
            node.setLastHeartBeat(LocalDate.now());
        } catch (ExceptionConnectionFailure | ExceptionCannotDisconnect | IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public String toString() {
        return null;
    }
}
