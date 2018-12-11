package fr.ensicaen.ecole.oasmr.lib.network;

import java.net.Socket;

public abstract class ServerRunnable implements Runnable, Cloneable {

    protected Socket clientSocket;

    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
