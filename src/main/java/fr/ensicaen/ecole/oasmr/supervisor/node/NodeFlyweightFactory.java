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

package fr.ensicaen.ecole.oasmr.supervisor.node;

import fr.ensicaen.ecole.oasmr.supervisor.node.exception.ExceptionNodeNotFound;

import java.net.InetAddress;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class NodeFlyweightFactory {

    private int nbNode = 0;
    private final Set<Node> nodes;

    public NodeFlyweightFactory() {
        nodes = new HashSet<>();
    }


    public Node getNode(InetAddress address, int port) {
        for (Node n : nodes) {
            if (address.equals(n.getNodeAddress()) && port == n.getPort()) {
                return n;
            }
        }
        NodeProxy newNode = new NodeProxy(new NodeData(++nbNode, address + ":" + port, 5, address, port));
        nodes.add(newNode);
        return newNode;
    }

    public Node getNode(Integer id) throws ExceptionNodeNotFound {
        for (Node n : nodes) {
            if (id < n.getId()) {
                throw new ExceptionNodeNotFound(id.toString());
            } else if (id.equals(n.getId())) {
                return n;
            }
        }
        throw new ExceptionNodeNotFound(id.toString());
    }

    public void removeNode(Integer id) throws ExceptionNodeNotFound {
        for (Node n : nodes) {
            if (id < n.getId()) {
                throw new ExceptionNodeNotFound(id.toString());
            } else if (id.equals(n.getId())) {
                nodes.remove(n);
                return;
            }
        }
        throw new ExceptionNodeNotFound(id.toString());
    }

    public final Set<Node> getNodes() {
        return Collections.unmodifiableSet(nodes);
    }

    public final NodeData[] getNodesData() {
        return nodes.stream().map(Node::getData).toArray(NodeData[]::new);
    }
}
