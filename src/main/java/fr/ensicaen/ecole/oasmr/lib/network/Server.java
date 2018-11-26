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
    private Boolean run;

    public Server(int port, ServerRunnable runnable) throws IOException, ExceptionPortInvalid {
        if (port < 65536 && port > 0) {
            this.port = port;
            run = false;
        } else {
            throw new ExceptionPortInvalid();
        }
        serverSocket = new ServerSocket(port);
        this.runnable = runnable;
    }

    public void start() throws IOException, InterruptedException {
        run = true;
        while (run) {
            try {
                Socket client = serverSocket.accept();
                ServerRunnable r = null;
                r = (ServerRunnable) runnable.clone();
                r.setClientSocket(client);
                Thread t = new Thread(r);
                t.start();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
    }


    public void stop() throws ExceptionServerRunnableNotEnded {
        try {
            serverSocket.close();
        } catch (IOException e) {
            throw new ExceptionServerRunnableNotEnded();
        } finally {
            run = false;
        }
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }


    public int getPort() {
        return port;
    }
}
