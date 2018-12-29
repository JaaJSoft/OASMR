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

package fr.ensicaen.ecole.oasmr.supervisor.node;

import fr.ensicaen.ecole.oasmr.lib.command.Command;

import java.io.Serializable;
import java.net.InetAddress;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public abstract class Node implements Comparable, Serializable {

    private final Integer id;
    private String name;
    private LocalDate lastHeartBeat;
    protected InetAddress nodeAddress;
    protected int port;
    private Set<Tag> tags = new HashSet<>();

    public Node(int id, InetAddress nodeAddress, int port) {
        this.id = id;
        this.nodeAddress = nodeAddress;
        this.port = port;
        name = nodeAddress.toString() + ":" + port;
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof Node) {
            return id.compareTo(((Node) o).getId());
        }
        return 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return id.equals(node.id);
    }

    @Override
    public int hashCode() {
        return id;
    }

    public abstract Serializable executeCommand(Command c) throws Exception;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getLastHeartBeat() {
        return lastHeartBeat;
    }

    public InetAddress getNodeAddress() {
        return nodeAddress;
    }

    public int getPort() {
        return port;
    }

    public void setLastHeartBeat(LocalDate lastHeartBeat) {
        this.lastHeartBeat = lastHeartBeat;
    }

    @Override
    public String toString() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void addTag(Tag s) {
        tags.add(s);
    }

    public void addTags(Set<Tag> s) {
        tags.addAll(s);
    }
}