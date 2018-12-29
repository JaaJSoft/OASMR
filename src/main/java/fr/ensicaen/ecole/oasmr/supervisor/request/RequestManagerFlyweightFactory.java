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

package fr.ensicaen.ecole.oasmr.supervisor.request;

import fr.ensicaen.ecole.oasmr.lib.network.exception.ExceptionPortInvalid;

import java.net.InetAddress;
import java.util.HashSet;

public class RequestManagerFlyweightFactory {
    private final HashSet<RequestManager> requestManagers;
    private final static RequestManagerFlyweightFactory ourInstance = new RequestManagerFlyweightFactory();

    public static RequestManagerFlyweightFactory getInstance() {
        return ourInstance;
    }

    private RequestManagerFlyweightFactory() {
        requestManagers = new HashSet<>();
    }

    public RequestManager getRequestManager(InetAddress address, int port) throws ExceptionPortInvalid {
        RequestManager r = new RequestManager(address, port);
        for (RequestManager requestManager : requestManagers) {
            if (requestManager.equals(r)) {
                return requestManager;
            }
        }
        requestManagers.add(r);
        return r;
    }

    public int getNbRequestManager() {
        return requestManagers.size();
    }

    public void clear() {
        requestManagers.clear();
    }
}
