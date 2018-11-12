package fr.ensicaen.ecole.oasmr.supervisor;

import fr.ensicaen.ecole.oasmr.lib.network.ServerRunnable;

import java.io.IOException;
import fr.ensicaen.ecole.oasmr.lib.*;

public class HeartBeatsHandler extends ServerRunnable {
    @Override
    public void run() {

        System.out.print("[" + dateUtil.getFormattedDate() + "]-> Heartbeat from " + clientSocket.getInetAddress() + " : ");
        try {
            System.out.println("") ;

            //dateUtil.receiveSerializable(clientSocket);
            //dateUtil.sendSerializable(clientSocket, "jeej");
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
