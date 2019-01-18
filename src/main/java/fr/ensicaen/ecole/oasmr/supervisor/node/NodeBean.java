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

import java.io.Serializable;
import java.net.InetAddress;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class NodeBean implements Comparable, Serializable {
    private final Integer id;
    private String name;
    private LocalDate lastHeartBeat;
    private InetAddress nodeAddress;
    protected int port;

    private String sshLogin = "root";
    private int sshPort = 22;
    private final Set<Tag> tags = new HashSet<>();

    public NodeBean(Integer id, String name, InetAddress nodeAddress, int port) {
        this.id = id;
        this.name = name;
        this.nodeAddress = nodeAddress;
        this.port = port;
    }

    public NodeBean(Integer id, String name, InetAddress nodeAddress, int port, String sshLogin, int sshPort) {
        this.id = id;
        this.name = name;
        this.nodeAddress = nodeAddress;
        this.port = port;
        this.sshLogin = sshLogin;
        this.sshPort = sshPort;
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof NodeBean) {
            return id.compareTo(((NodeBean) o).getId());
        }
        return 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NodeBean node = (NodeBean) o;
        return id.equals(node.id);
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getSshPort() {
        return sshPort;
    }

    public void setSshPort(int sshPort) {
        this.sshPort = sshPort;
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

    public Set<Tag> getTags() {
        return tags;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastHeartBeat(LocalDate lastHeartBeat) {
        this.lastHeartBeat = lastHeartBeat;
    }

    public void setNodeAddress(InetAddress nodeAddress) {
        this.nodeAddress = nodeAddress;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void addTag(Tag s) {
        tags.add(s);
    }

    public void addTags(Set<Tag> s) {
        tags.addAll(s);
    }
}
