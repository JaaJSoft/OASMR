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

import fr.ensicaen.ecole.oasmr.supervisor.node.Node;
import fr.ensicaen.ecole.oasmr.supervisor.node.request.RequestGetNodes;
import picocli.CommandLine;

import java.util.Set;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "list")
public class ListCli implements Callable {

    @CommandLine.ParentCommand
    MainCli main;

    @CommandLine.Option(names = {"-h", "--help"}, usageHelp = true, description = "display a help message")
    boolean help = false;

    @Override
    public Object call() throws Exception {
        main.call();
        if (help) {
            CommandLine.usage(this, System.err);
            return null;
        }
        Set<Node> nodes = (Set<Node>) main.r.sendRequest(new RequestGetNodes());
        for (Node n : nodes) {
            System.out.println(n.getId() + " -> " + n.getNodeAddress() + ":" + n.getPort());
        }
        return nodes;
    }
}
