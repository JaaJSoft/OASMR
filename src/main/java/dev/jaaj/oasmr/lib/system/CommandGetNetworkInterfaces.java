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

package dev.jaaj.oasmr.lib.system;

import dev.jaaj.oasmr.lib.command.Command;
import oshi.hardware.NetworkIF;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class CommandGetNetworkInterfaces extends Command {

    @Override
    public Serializable execute(Object... params) throws Exception {
        NetworkIF[] networkInterfaces = SystemInfoSingleton.getHardware().getNetworkIFs();
        List<HashMap<String, String>> allNetIF = new ArrayList<>();
        for (NetworkIF net : networkInterfaces) {
            HashMap<String, String> netInfo = new HashMap<>();
            netInfo.put("NAME", net.getName());
            netInfo.put("MAC", net.getMacaddr());
            netInfo.put("IPv4", Arrays.toString(net.getIPv4addr()));
            netInfo.put("IPv6", Arrays.toString(net.getIPv6addr()));
            allNetIF.add(netInfo);
        }
        return allNetIF.toArray(new HashMap[allNetIF.size()]);
    }

    @Override
    public String toString() {
        return "Command get Network Inferfaces";
    }
}
