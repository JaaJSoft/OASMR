package fr.ensicaen.ecole.oasmr.node;

import fr.ensicaen.ecole.oasmr.lib.command.Command;
import fr.ensicaen.ecole.oasmr.lib.command.ExceptionCommandNotAuthorized;
import fr.ensicaen.ecole.oasmr.lib.dateUtil;
import fr.ensicaen.ecole.oasmr.lib.network.ServerRunnable;
import fr.ensicaen.ecole.oasmr.lib.network.util;
import fr.ensicaen.ecole.oasmr.supervisor.Supervisor;

import java.io.IOException;
import java.io.Serializable;

public class ServerRunnableHeartBeatsHandler extends ServerRunnable {
    private Supervisor supervisor;

    public ServerRunnableHeartBeatsHandler(Supervisor supervisor) {
        this.supervisor = supervisor;
    }

    @Override
    public void run() {
        System.out.println("[" + dateUtil.getFormattedDate() + "]-> New heartbeat from " + clientSocket.getInetAddress());
        try {
            //TODO Maybe do some stuff here :)
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
