/*
 * Copyright (c) 2019. JaaJ-dev
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.jaaj.oasmr.cli;

import dev.jaaj.oasmr.supervisor.node.NodeData;
import dev.jaaj.oasmr.supervisor.node.command.request.RequestGetNodes;
import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(name = "list")
class ListCli implements Callable {

    @CommandLine.ParentCommand
    private
    MainCli main;

    @CommandLine.Option(names = {"-h", "--help"}, usageHelp = true, description = "display a help message")
    private
    boolean help = false;

    @Override
    public Object call() throws Exception {
        main.call();
        if (help) {
            CommandLine.usage(this, System.err);
            return null;
        }
        NodeData[] nodes = (NodeData[]) main.r.sendRequest(new RequestGetNodes());
        for (NodeData n : nodes) {
            System.out.println(n.getId() + " -> " + n.getNodeAddress() + ":" + n.getPort());
        }
        return nodes;
    }
}
