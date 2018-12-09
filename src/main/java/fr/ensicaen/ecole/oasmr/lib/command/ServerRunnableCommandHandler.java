package fr.ensicaen.ecole.oasmr.lib.command;

import fr.ensicaen.ecole.oasmr.lib.dateUtil;
import fr.ensicaen.ecole.oasmr.lib.network.ServerRunnable;
import fr.ensicaen.ecole.oasmr.lib.network.util;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ServerRunnableCommandHandler extends ServerRunnable {
    private Object[] o;
    private String commandType;
    private List<InetAddress> authorizedAddress;
    private Set<Command> authorizedCommands;

    public ServerRunnableCommandHandler(String commandType, List<InetAddress> authorizedAddress, Set<Command> authorizedCommands, Object... o) {
        this.o = o;
        this.commandType = commandType;
        this.authorizedAddress = authorizedAddress;
        this.authorizedCommands = authorizedCommands;
    }

    public ServerRunnableCommandHandler(String commandType, Object... o) {
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
            Serializable response;
            if (!authorizedCommands.contains(command) && !authorizedCommands.isEmpty()) {
                response = new ExceptionCommandNotAuthorized(command.toString());
            } else {
                response = command.execute(o);
            }
            util.sendSerializable(clientSocket, response);
            clientSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                util.sendSerializable(clientSocket, e);
                clientSocket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
