/*
 *  Copyright (c) 2019. CCC-Development-Team
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package fr.ensicaen.ecole.oasmr.lib.command;

import fr.ensicaen.ecole.oasmr.lib.dateUtil;
import fr.ensicaen.ecole.oasmr.lib.network.ServerRunnable;
import fr.ensicaen.ecole.oasmr.lib.network.util;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ServerRunnableCommandExecutor extends ServerRunnable {

    private final CommandExecutor executor;
    private final List<InetAddress> authorizedAddress;
    private final Set<Class<Command>> authorizedCommands;


    public ServerRunnableCommandExecutor(CommandExecutor executor, List<InetAddress> authorizedAddress, Set<Class<Command>> authorizedCommands) {
        this.executor = executor;
        this.authorizedAddress = authorizedAddress;
        this.authorizedCommands = authorizedCommands;
    }

    public ServerRunnableCommandExecutor(CommandExecutor executor) {
        this.executor = executor;
        this.authorizedCommands = new HashSet<>();
        authorizedAddress = new ArrayList<>();
    }

    @Override
    public void run() {
        System.out.print("[" + dateUtil.getFormattedDate() + "]-> New command from " + clientSocket.getInetAddress() + " : ");
        try {
            Command command = (Command) util.receiveSerializable(clientSocket);
            System.out.println(command);
            Serializable response;
            if (!authorizedCommands.contains(command) && !authorizedCommands.isEmpty()) {
                response = new ExceptionCommandNotAuthorized(command.toString());
            } else {
                response = executor.executeCommand(command);
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
