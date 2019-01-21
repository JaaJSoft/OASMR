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

package fr.ensicaen.ecole.oasmr.lib.packagemanagment.apt;

import fr.ensicaen.ecole.oasmr.lib.ProcessBuilderUtil;
import fr.ensicaen.ecole.oasmr.lib.command.Command;
import fr.ensicaen.ecole.oasmr.lib.packagemanagment.apt.exceptions.ExceptionAptFailGettingList;

import java.io.IOException;
import java.io.Serializable;
import java.lang.management.ManagementFactory;

public class CommandAptList extends Command {

    public CommandAptList() {
    }

    @Override
    public Serializable execute(Object... params) throws Exception {
        String pid = "";
        try {
            pid = ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
        } catch (Exception e) {
            e.printStackTrace();
        }
        String tmpFileLocation = "/tmp/AptList" + pid;
        ProcessBuilder processBuilderList2Txt = new ProcessBuilder("apt", "list", ">", tmpFileLocation);
        ProcessBuilder processBuilderTxtReading = new ProcessBuilder("cat", tmpFileLocation);
        try {
            Process p = processBuilderList2Txt.start();
            p.waitFor();
            Process p2 = processBuilderTxtReading.start();
            p2.waitFor();
            int ret = p2.exitValue();
            switch (ret) {
                case 0:
                    String output = ProcessBuilderUtil.getOutput(p2);
                    System.out.println(output);
                    return output;
                default:
                    throw new ExceptionAptFailGettingList(ProcessBuilderUtil.getOutputError(p));
            }
        } catch(IOException | InterruptedException e){
                e.printStackTrace();
                return e;
        }

    }

    @Override
    public String toString() {
        return "apt list";
    }
}
