package fr.ensicaen.ecole.oasmr.lib.network;

import java.io.IOException;

public class ServerRunnableEcho extends ServerRunnable {

    @Override
    public void run() {
        try {
            String temp = (String) util.receiveSerializable(clientSocket);
            util.sendSerializable(clientSocket, temp);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}