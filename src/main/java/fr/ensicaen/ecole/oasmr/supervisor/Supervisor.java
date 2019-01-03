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

package fr.ensicaen.ecole.oasmr.supervisor;

import fr.ensicaen.ecole.oasmr.lib.dateUtil;
import fr.ensicaen.ecole.oasmr.lib.network.Server;
import fr.ensicaen.ecole.oasmr.lib.network.exception.ExceptionPortInvalid;
import fr.ensicaen.ecole.oasmr.lib.command.ServerRunnableCommandHandler;
import fr.ensicaen.ecole.oasmr.supervisor.auth.User;
import fr.ensicaen.ecole.oasmr.supervisor.auth.UserList;
import fr.ensicaen.ecole.oasmr.supervisor.node.NodeFlyweightFactory;
import fr.ensicaen.ecole.oasmr.supervisor.node.ServerRunnableHeartBeatsHandler;

import java.io.IOException;

public class Supervisor {
    private NodeFlyweightFactory nodeFlyweightFactory;
    private Server serverHeartBeatsHandler;
    private Server serverRequestHandler;
    private UserList userList;
    private boolean authenticated;


    public Supervisor(int portHeartBeats, int portRequests) throws IOException, ExceptionPortInvalid {
        serverHeartBeatsHandler = new Server(portHeartBeats, new ServerRunnableHeartBeatsHandler(this));
        serverRequestHandler = new Server(portRequests, new ServerRunnableCommandHandler("Request", this));
        this.nodeFlyweightFactory = new NodeFlyweightFactory();
        this.userList = new UserList();
        this.authenticated = false;
    }

    public void start() throws InterruptedException {
        Thread ThreadServerHeartBeatsHandler = new Thread(() -> {
            try {
                serverHeartBeatsHandler.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        Thread ThreadServerRequestHandler = new Thread(() -> {
            try {
                serverRequestHandler.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        System.out.print("[" + dateUtil.getFormattedDate() + "]-> HeartBeatHandler loading... ");
        ThreadServerHeartBeatsHandler.start();
        System.out.println("Done !");
        System.out.print("[" + dateUtil.getFormattedDate() + "]-> RequestHandler loading... ");
        ThreadServerRequestHandler.start();
        System.out.println("Done !");
        ThreadServerHeartBeatsHandler.join();
        ThreadServerRequestHandler.join();
    }

    public NodeFlyweightFactory getNodeFlyweightFactory() {
        return nodeFlyweightFactory;
    }

    public UserList getUserList() {
        return userList;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void authentication(){
        authenticated = true;
    }

}
