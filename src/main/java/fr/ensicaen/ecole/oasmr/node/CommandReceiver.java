package fr.ensicaen.ecole.oasmr.node;

import fr.ensicaen.ecole.oasmr.lib.dateUtil;
import fr.ensicaen.ecole.oasmr.lib.network.ServerRunnable;
import fr.ensicaen.ecole.oasmr.lib.network.util;

import java.io.IOException;

public class CommandReceiver extends ServerRunnable {

    @Override
    public void run() {
        System.out.print("[" + dateUtil.getFormattedDate() + "]-> New command from " + clientSocket.getInetAddress() + " : ");

        try {
            String temp = (String) util.receiveSerializable(clientSocket);
            System.out.println("");
            util.sendSerializable(clientSocket, temp);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
