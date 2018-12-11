package fr.ensicaen.ecole.oasmr.lib.network;


import fr.ensicaen.ecole.oasmr.lib.network.exception.ExceptionCannotDisconnect;
import fr.ensicaen.ecole.oasmr.lib.network.exception.ExceptionConnectionFailure;
import fr.ensicaen.ecole.oasmr.lib.network.exception.ExceptionPortInvalid;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Objects;

public class Client implements Serializable {
    private final InetAddress ip;
    private final int port;
    private Socket socket;

    public Client(InetAddress ip, int port) throws ExceptionPortInvalid {
        this.ip = ip;
        if (port < 65536 && port > 0) {
            this.port = port;
        } else {
            throw new ExceptionPortInvalid();
        }
    }

    public void connect() throws ExceptionConnectionFailure {
        try {
            socket = new Socket(ip, port);
        } catch (IOException e) {
            throw new ExceptionConnectionFailure();
        }
    }

    public void disconnect() throws ExceptionCannotDisconnect {
        try {
            socket.close();
        } catch (IOException e) {
            throw new ExceptionCannotDisconnect();
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public InetAddress getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return port == client.port && ip.equals(client.ip);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ip, port);
    }
}
