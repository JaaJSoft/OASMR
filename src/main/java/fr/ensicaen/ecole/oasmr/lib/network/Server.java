package fr.ensicaen.ecole.oasmr.lib.network;


import fr.ensicaen.ecole.oasmr.lib.network.exception.ExceptionPortInvalid;
import fr.ensicaen.ecole.oasmr.lib.network.exception.ExceptionServerRunnableNotEnded;

import java.io.IOException;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Serializable {
    private int port;
    private ServerSocket serverSocket;
    private ServerRunnable runnable;

    public Server(int port, ServerRunnable runnable) throws IOException, ExceptionPortInvalid {
        if (port < 65536 && port > 0) {
            this.port = port;
        } else {
            throw new ExceptionPortInvalid();
        }
        serverSocket = new ServerSocket(port);
        this.runnable = runnable;
    }

    public void start() throws IOException, InterruptedException, CloneNotSupportedException {
        while (true) {
            Socket client = serverSocket.accept();
            ServerRunnable r = (ServerRunnable) runnable.clone();
            r.setClientSocket(client);
            Thread t = new Thread(r);
            t.start();
        }
    }


    public void stop() throws ExceptionServerRunnableNotEnded {
        try {
            serverSocket.close();
        } catch (IOException e) {
            throw new ExceptionServerRunnableNotEnded();
        }
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }


    public int getPort() {
        return port;
    }
}
