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

import fr.ensicaen.ecole.oasmr.app.Config;
import fr.ensicaen.ecole.oasmr.app.view.NodesModel;
import fr.ensicaen.ecole.oasmr.app.view.View;
import fr.ensicaen.ecole.oasmr.lib.network.exception.ExceptionPortInvalid;
import fr.ensicaen.ecole.oasmr.supervisor.request.RequestManager;
import fr.ensicaen.ecole.oasmr.supervisor.request.RequestManagerFlyweightFactory;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class NodeTaskManagerController extends View {

    private NodesModel nodesModel;
    private RequestManager requestManager = null;
    private Config config;

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

        }

    }

    @Override
    public void onStop() {

    }
}
