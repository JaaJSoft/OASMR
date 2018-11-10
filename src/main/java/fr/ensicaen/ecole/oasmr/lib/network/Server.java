package fr.ensicaen.ecole.oasmr.lib.network;


import fr.ensicaen.ecole.oasmr.lib.network.exception.ExceptionPortInvalid;
import fr.ensicaen.ecole.oasmr.lib.network.exception.ExceptionServerRunnableNotEnded;

import java.io.IOException;
import java.io.Serializable;
import java.net.ServerSocket;

public class Server implements Serializable {
    private int port;
    private ServerSocket serverSocket;

    public Server(int port) throws IOException, ExceptionPortInvalid {
        if (port < 65536 && port > 0) {
            this.port = port;
        } else {
            throw new ExceptionPortInvalid();
        }
        serverSocket = new ServerSocket(port);
    }

    public void start(Runnable runnable) throws IOException, InterruptedException {
        while (true) {
            Thread t = new Thread(runnable);
            t.start();
            t.join();
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
