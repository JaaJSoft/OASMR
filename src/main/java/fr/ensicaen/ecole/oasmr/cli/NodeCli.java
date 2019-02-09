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

package fr.ensicaen.ecole.oasmr.cli;

import fr.ensicaen.ecole.oasmr.supervisor.node.NodeData;
import fr.ensicaen.ecole.oasmr.supervisor.node.request.RequestGetNode;
import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(name = "node")
public class NodeCli implements Callable {

    @CommandLine.ParentCommand
    MainCli main;

    @CommandLine.Option(names = {"-h", "--help"}, usageHelp = true, description = "display a help message")
    boolean help = false;

    @CommandLine.Option(names = {"-i", "--id"}, required = true, description = "node id")
    int idNode;

    @Override
    public Object call() throws Exception {
        main.call();

        if (help) {
            CommandLine.usage(this, System.err);
            return null;
        }

        NodeData n = (NodeData) main.r.sendRequest(new RequestGetNode(idNode));
        System.out.println(n);
        return n;
    }
}
