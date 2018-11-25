package fr.ensicaen.ecole.oasmr.lib.command;

import fr.ensicaen.ecole.oasmr.lib.dateUtil;
import fr.ensicaen.ecole.oasmr.lib.network.ServerRunnable;
import fr.ensicaen.ecole.oasmr.lib.network.util;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class ServerRunnableCommandHandler extends ServerRunnable {
    private Object o;
    private String commandType;
    private List<InetAddress> authorizedAddress;

    public ServerRunnableCommandHandler(Object o, String commandType, List<InetAddress> authorizedAddress) {
        this.o = o;
        this.commandType = commandType;
        this.authorizedAddress = authorizedAddress;
    }

    public ServerRunnableCommandHandler(Object o, String commandType) {
        this.o = o;
        this.commandType = commandType;
        authorizedAddress = new ArrayList<>();
    }

    @Override
    public void run() {
        System.out.print("[" + dateUtil.getFormattedDate() + "]-> New " + commandType + " from " + clientSocket.getInetAddress() + " : ");
        try {
            Command command = (Command) util.receiveSerializable(clientSocket);
            System.out.println(command);
            Serializable response = command.execute(o);
            util.sendSerializable(clientSocket, response);
            clientSocket.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
