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

package fr.ensicaen.ecole.oasmr.lib.system;

import fr.ensicaen.ecole.oasmr.lib.command.Command;

import java.io.Serializable;

public class CommandKillProcess extends Command {

    private int pidToKill;

    public CommandKillProcess(int pidToKill) {
        this.pidToKill = pidToKill;
    }

    @Override
    protected Serializable execute(Object... params) throws Exception {
        Runtime rt = Runtime.getRuntime();
        if (System.getProperty("os.name").toLowerCase().indexOf("windows") > -1) {
            rt.exec("taskkill " + pidToKill);
        } else {
            rt.exec("kill -9 " + pidToKill);
        }
        return null;
    }

    @Override
    public String toString() {
        return null;
    }
}
