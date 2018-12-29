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

package fr.ensicaen.ecole.oasmr.cli;

import fr.ensicaen.ecole.oasmr.supervisor.request.RequestManager;
import fr.ensicaen.ecole.oasmr.supervisor.request.RequestManagerFlyweightFactory;
import picocli.CommandLine;

import java.net.InetAddress;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "MainCli", subcommands = {ListCli.class, NodeCli.class})
public class MainCli implements Callable<Void> {

    @CommandLine.Option(names = {"-s", "--supervisor"}, required = true, description = "supervisor address")
    InetAddress supervisorAddress;

    @CommandLine.Option(names = {"-p", "--port"}, description = "supervisor port")
    int port = 40404;

    @CommandLine.Option(names = {"-h", "--help"}, usageHelp = true, description = "display a help message")
    boolean help = false;

    @CommandLine.Option(names = {"-u", "--user"}, description = "username")
    String username = "admin";

    RequestManager r;


    @Override
    public Void call() throws Exception {
        if (help) {
            CommandLine.usage(this, System.err);
            return null;
        }
        r = RequestManagerFlyweightFactory.getInstance().getRequestManager(supervisorAddress, port);
        return null;
    }

    public static void main(String[] args) throws Exception {
        CommandLine.call(new MainCli(), args);
    }
}
