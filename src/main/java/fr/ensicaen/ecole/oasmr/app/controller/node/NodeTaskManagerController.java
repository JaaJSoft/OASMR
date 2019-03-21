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

package fr.ensicaen.ecole.oasmr.app.controller.node;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import fr.ensicaen.ecole.oasmr.app.Config;
import fr.ensicaen.ecole.oasmr.app.controller.UserManagementController;
import fr.ensicaen.ecole.oasmr.app.view.NodesModel;
import fr.ensicaen.ecole.oasmr.app.view.View;
import fr.ensicaen.ecole.oasmr.lib.network.exception.ExceptionPortInvalid;
import fr.ensicaen.ecole.oasmr.lib.system.CommandGetProcesses;
import fr.ensicaen.ecole.oasmr.lib.system.CommandKillProcess;
import fr.ensicaen.ecole.oasmr.supervisor.auth.request.RequestGetAdmin;
import fr.ensicaen.ecole.oasmr.supervisor.node.command.request.RequestExecuteCommand;
import fr.ensicaen.ecole.oasmr.supervisor.request.RequestManager;
import fr.ensicaen.ecole.oasmr.supervisor.request.RequestManagerFlyweightFactory;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;

public class NodeTaskManagerController extends View {

    private NodesModel nodesModel;
    private RequestManager requestManager = null;
    private Config config;

    @FXML
    JFXButton kill;

    @FXML
    JFXTextField searchField;

    @FXML
    VBox tableBox;

    public NodeTaskManagerController(View parent) throws IOException {
        super("NodeTaskManager", parent);
    }

    @Override
    public void onCreate() {
        nodesModel = NodesModel.getInstance();
    }

    @Override
    protected void onStart() {
        if (requestManager == null) {
            try {
                nodesModel = NodesModel.getInstance();
                config = Config.getInstance();
                requestManager = RequestManagerFlyweightFactory.getInstance().getRequestManager(InetAddress.getByName(config.getIP()), config.getPort());
            } catch (ExceptionPortInvalid | UnknownHostException exceptionPortInvalid) {
                exceptionPortInvalid.printStackTrace();
            }
        }

        if (nodesModel.getSelectedAmount() > 1) {
            //TODO : Configure view for group
        } else if (nodesModel.getSelectedAmount() == 1) {
            init();
        }

    }

    public void init(){


        CommandGetProcesses getProcesses = new CommandGetProcesses();

        HashMap<String, String>[] processes = new HashMap[0];

        try {
            processes = (HashMap<String, String>[]) requestManager.sendRequest(new RequestExecuteCommand(nodesModel.getCurrentNodeData().get(0).getId(), getProcesses));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("AAAAAAAA");
        System.out.println(Arrays.toString(processes));

        /**
         * =>
         * Create a table with all processes
         * Refresh table every 5sec (like diapo EX java bean TP)
         * make a search bar
         * make a kill btn
         * make a test for CommandKill
         */
        tableBox.getChildren().clear();
        ObservableList<InternalProcess> processesList = FXCollections.observableArrayList();


        for (HashMap<String, String> p : processes) {
                processesList.add(new InternalProcess(p.get("PID"), p.get("CPU"), p.get("MEM"), p.get("NAME")));
        }

        final TreeItem<InternalProcess> root = new RecursiveTreeItem<>(processesList, RecursiveTreeObject::getChildren);

        JFXTreeTableView processesTable = new JFXTreeTableView(root);

        JFXTreeTableColumn<InternalProcess, String> nameCol = new JFXTreeTableColumn<>("Name");
        nameCol.setPrefWidth(300);
        nameCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<InternalProcess, String> param) -> {
            if (nameCol.validateValue(param)) {
                return param.getValue().getValue().Name;
            } else {
                return nameCol.getComputedValue(param);
            }
        });


        JFXTreeTableColumn<InternalProcess, String> cpuCol = new JFXTreeTableColumn<>("CPU usage");
        cpuCol.setPrefWidth(150);
        cpuCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<InternalProcess, String> param) -> {
            if (cpuCol.validateValue(param)) {
                return param.getValue().getValue().Cpu;
            } else {
                return cpuCol.getComputedValue(param);
            }
        });

        JFXTreeTableColumn<InternalProcess, String> memCol = new JFXTreeTableColumn<>("Memory");
        memCol.setPrefWidth(150);
        memCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<InternalProcess, String> param) -> {
            if (memCol.validateValue(param)) {
                return param.getValue().getValue().Mem;
            } else {
                return memCol.getComputedValue(param);
            }
        });

        JFXTreeTableColumn<InternalProcess, String> pidCol = new JFXTreeTableColumn<>("PID");
        pidCol.setPrefWidth(150);
        pidCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<InternalProcess, String> param) -> {
            if (pidCol.validateValue(param)) {
                return param.getValue().getValue().Pid;
            } else {
                return pidCol.getComputedValue(param);
            }
        });

        processesTable.setShowRoot(false);
        processesTable.setEditable(false);
        processesTable.getColumns().setAll(nameCol, cpuCol, memCol, pidCol);
        processesTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        processesTable.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        tableBox.getChildren().addAll(processesTable);



        kill.setOnAction(event -> {
            for (Object u : processesTable.getSelectionModel().getSelectedItems()) {
                if (u instanceof TreeItem) {
                    if (((TreeItem) u).getValue() instanceof InternalProcess) {
                        System.out.println(((InternalProcess) ((TreeItem) u).getValue()).getPid());
                        CommandKillProcess killProcess = new CommandKillProcess(((InternalProcess) ((TreeItem) u).getValue()).getPid());
                        try {
                            requestManager.sendRequest(killProcess);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });


    }

    @Override
    public void onStop() {

    }

    private static final class InternalProcess extends RecursiveTreeObject<InternalProcess>{
        StringProperty Pid;
        StringProperty Cpu;
        StringProperty Mem;
        StringProperty Name;

        public InternalProcess(String pid, String cpu, String mem, String name) {
            Pid = new SimpleStringProperty(pid);
            Cpu = new SimpleStringProperty(cpu);
            Mem = new SimpleStringProperty(mem);
            Name = new SimpleStringProperty(name);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            InternalProcess that = (InternalProcess) o;
            return Objects.equals(Pid, that.Pid) &&
                    Objects.equals(Cpu, that.Cpu) &&
                    Objects.equals(Mem, that.Mem) &&
                    Objects.equals(Name, that.Name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(Pid, Cpu, Mem, Name);
        }

        public int getPid(){
            return Integer.parseInt(Pid.get());
        }
    }

}
