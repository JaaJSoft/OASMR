/*
 *  Copyright (c) 2018. CCC-Development-Team
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

package fr.ensicaen.ecole.oasmr.supervisor.request;

import fr.ensicaen.ecole.oasmr.lib.example.CommandEchoString;
import fr.ensicaen.ecole.oasmr.supervisor.node.request.RequestExecuteCommand;

import java.net.InetAddress;

class Example {

    public static void main(String[] args) throws Exception {
        RequestManager r = RequestManagerFlyweightFactory.getInstance().getRequestManager(InetAddress.getByName("127.0.0.1"), 40404);
        String jeej = (String) r.sendRequest(new RequestHelloWorld());
        System.out.println(jeej);
        String test = (String) r.sendRequest(new RequestExecuteCommand(0, new CommandEchoString(jeej)));
        System.out.println(test);

    }
}
