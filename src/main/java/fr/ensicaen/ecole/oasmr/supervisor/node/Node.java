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

import fr.ensicaen.ecole.oasmr.lib.command.Command;

import java.io.Serializable;
import java.net.InetAddress;
import java.time.LocalDate;
import java.util.Set;

public abstract class Node implements Comparable, Serializable {

    private final NodeBean data;


    public Node(int id, InetAddress nodeAddress, int port) {
        data = new NodeBean(id, nodeAddress.toString() + ":" + port, nodeAddress, port);
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof Node) {
            return data.compareTo(((Node) o).getData());
        }
        return 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return data.equals(node.getData());
    }

    @Override
    public int hashCode() {
        return data.getId();
    }

    public abstract Serializable executeCommand(Command c) throws Exception;

    public Integer getId() {
        return data.getId();
    }

    public String getName() {
        return data.getName();
    }

    public LocalDate getLastHeartBeat() {
        return data.getLastHeartBeat();
    }

    public InetAddress getNodeAddress() {
        return data.getNodeAddress();
    }

    public int getPort() {
        return data.getPort();
    }

    public void setLastHeartBeat(LocalDate lastHeartBeat) {
        data.setLastHeartBeat(lastHeartBeat);
    }

    @Override
    public String toString() {
        return data.toString();
    }

    public void setName(String name) {
        data.setName(name);
    }

    public Set<Tag> getTags() {
        return data.getTags();
    }

    public void addTag(Tag s) {
        data.addTag(s);
    }

    public void addTags(Set<Tag> s) {
        data.addTags(s);
    }

    public NodeBean getData() {
        return data;
    }
}