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

package dev.jaaj.oasmr.supervisor.node;

import dev.jaaj.oasmr.lib.command.Command;
import dev.jaaj.oasmr.lib.command.CommandExecutor;
import dev.jaaj.oasmr.lib.command.CommandsHist;

import java.io.Serializable;
import java.net.InetAddress;
import java.time.LocalDateTime;
import java.util.Set;

public abstract class Node extends CommandExecutor implements Comparable, Serializable {

    protected NodeData data;

    private final CommandsHist hist;

    protected Node(NodeData data) {
        this.data = data;
        hist = new CommandsHist();
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

    protected abstract Serializable execute(Command c) throws Exception;

    public abstract void syncData();

    public Integer getId() {
        return data.getId();
    }

    public String getName() {
        return data.getName();
    }

    public LocalDateTime getLastHeartBeat() {
        return data.getLastHeartBeat();
    }

    public InetAddress getNodeAddress() {
        return data.getNodeAddress();
    }

    public int getPort() {
        return data.getPort();
    }

    public void setLastHeartBeat(LocalDateTime lastHeartBeat) {
        data.setLastHeartBeat(lastHeartBeat);
    }

    @Override
    public String toString() {
        return data.toString();
    }

    public void setName(String name){
        data.setName(name);
        syncData();
    }

    public Set<Tag> getTags() {
        return data.getTags();
    }

    public void addTag(Tag s) {
        data.addTag(s);
        syncData();
    }

    public void addTags(Set<Tag> s) {
        data.addTags(s);
        syncData();
    }

    public void removeTag(Tag s){
        data.removeTag(s);
        syncData();
    }

    public void removeTags(Set<Tag> s){
        data.removeTags(s);
        syncData();
    }

    public NodeData getData() {
        return data;
    }

    public CommandsHist getHist() {
        return hist;
    }

    public void setData(NodeData data) {
        this.data = data;
    }

    public void setSSHLogin(String login){
        data.setSshLogin(login);
        syncData();
    }
    public void setSSHPort(int port){
        data.setSshPort(port);
        syncData();
    }
}