package fr.ensicaen.ecole.oasmr.node;

import fr.ensicaen.ecole.oasmr.command.Command;
import fr.ensicaen.ecole.oasmr.lib.dateUtil;
import fr.ensicaen.ecole.oasmr.lib.network.ServerRunnable;
import fr.ensicaen.ecole.oasmr.lib.network.util;
import fr.ensicaen.ecole.oasmr.supervisor.Node;
import fr.ensicaen.ecole.oasmr.supervisor.NodeReal;

import java.io.IOException;
import java.io.Serializable;

public class CommandReceiver extends ServerRunnable {
    private Node myNode;

    public CommandReceiver(Node myNode) {
        this.myNode = myNode;
    }

    @Override
    public void run() {
        System.out.print("[" + dateUtil.getFormattedDate() + "]-> New command from " + clientSocket.getInetAddress() + " : ");
        try {
            Command command = (Command) util.receiveSerializable(clientSocket);
            System.out.println(command);
            Serializable response = command.execute(myNode);
            util.sendSerializable(clientSocket, response);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
