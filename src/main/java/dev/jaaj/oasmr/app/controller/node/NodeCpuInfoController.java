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

package dev.jaaj.oasmr.app.controller.node;

import dev.jaaj.oasmr.lib.network.exception.ExceptionPortInvalid;
import dev.jaaj.oasmr.lib.system.CommandGetCpuLoad;
import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.TileBuilder;
import dev.jaaj.oasmr.app.Config;
import dev.jaaj.oasmr.app.view.NodesModel;
import dev.jaaj.oasmr.app.view.View;
import dev.jaaj.oasmr.supervisor.node.command.request.RequestExecuteCommand;
import dev.jaaj.oasmr.supervisor.request.RequestManager;
import dev.jaaj.oasmr.supervisor.request.RequestManagerFlyweightFactory;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Stop;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class NodeCpuInfoController extends View {

    @FXML
    VBox cpuInfoVBox;

    private RequestManager requestManager = null;
    private Config config = null;
    private NodesModel nodesModel = null;
    private Tile cpuGraph;

    public NodeCpuInfoController(View parent) throws IOException {
        super("NodeCpuInfo", parent);
        onCreate();
    }

    @Override
    public void onCreate() {
        cpuGraph = TileBuilder.create()
                .skinType(Tile.SkinType.GAUGE_SPARK_LINE)
                .title("Cpu Usage")
                .animated(true)
                .averagingPeriod(25)
                .barColor(Tile.YELLOW_ORANGE)
                .barBackgroundColor(Color.rgb(255, 0, 0, 0.1))
                .sections(new eu.hansolo.tilesfx.Section(0, 33, Tile.GREEN),
                        new eu.hansolo.tilesfx.Section(33, 67, Tile.YELLOW),
                        new eu.hansolo.tilesfx.Section(67, 100, Tile.RED))
                .sectionsVisible(true)
                .highlightSections(true)
                .strokeWithGradient(true)
                .gradientStops(new Stop(0.0, Tile.GREEN),
                        new Stop(0.33, Tile.GREEN),
                        new Stop(0.33, Tile.YELLOW),
                        new Stop(0.67, Tile.YELLOW),
                        new Stop(0.67, Tile.RED),
                        new Stop(1.0, Tile.RED))
                .prefSize(150, 150)
                .build();
        cpuInfoVBox.getChildren().add(cpuGraph);

    }

    @Override
    protected void onStart() {
        if (requestManager == null) {
            try {
                config = Config.getInstance();
                requestManager = RequestManagerFlyweightFactory.getInstance().getRequestManager(InetAddress.getByName(config.getIP()), config.getPort());
                nodesModel = NodesModel.getInstance();
            } catch (ExceptionPortInvalid | UnknownHostException exceptionPortInvalid) {
                exceptionPortInvalid.printStackTrace();
            }
        }
    }

    @Override
    protected void onUpdate() {
        if (nodesModel.getSelectedAmount() == 1) {
            try {
                cpuGraph.setValue((double) requestManager.sendRequest(
                        new RequestExecuteCommand(
                                nodesModel.getCurrentNodeData().iterator().next().getId(),
                                new CommandGetCpuLoad()
                        )) * 100);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    @Override
    public void onStop() {
        config = null;
        requestManager = null;
        nodesModel = null;
    }
}
