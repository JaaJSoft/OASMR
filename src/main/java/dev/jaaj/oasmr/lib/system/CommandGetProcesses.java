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

package dev.jaaj.oasmr.lib.system;

import dev.jaaj.oasmr.lib.command.Command;
import oshi.hardware.GlobalMemory;
import oshi.software.os.OSProcess;
import oshi.software.os.OperatingSystem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class CommandGetProcesses extends Command {

    @Override
    public Serializable execute(Object... params) throws Exception {
        //int limit = params.length == 0 ?  5 : (int) params[0];
        OperatingSystem os = SystemInfoSingleton.getOperatingSystem();
        GlobalMemory mem = SystemInfoSingleton.getHardware().getMemory();
        List<OSProcess> procs = Arrays.asList(os.getProcesses(20, OperatingSystem.ProcessSort.CPU));
        List<HashMap<String, String>> allProcesses = new ArrayList<>();
        for (OSProcess p : procs) {
            HashMap<String, String> procInfo = new HashMap<>();
            procInfo.put("PID", String.valueOf(p.getProcessID()));
            procInfo.put("CPU", String.valueOf((100d * (p.getKernelTime() + p.getUserTime()) / p.getUpTime())));
            procInfo.put("MEM", String.valueOf(100d * p.getResidentSetSize() / mem.getTotal()));
            procInfo.put("NAME", p.getName());
            procInfo.put("PPID", String.valueOf(p.getParentProcessID()));
            allProcesses.add(procInfo);
        }
        return allProcesses.toArray(new HashMap[0]);
    }

    @Override
    public String toString() {
        return "Command get processes";
    }
}
