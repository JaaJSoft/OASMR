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

package dev.jaaj.oasmr.supervisor;

import dev.jaaj.oasmr.lib.example.CommandEchoString;
import dev.jaaj.oasmr.lib.network.exception.ExceptionPortInvalid;

import java.io.IOException;

public class SupervisorMain {

    public static void main(String[] args) throws IOException, ExceptionPortInvalid, InterruptedException {
        Supervisor s;
        if (args.length == 1) {
            s = new Supervisor(Integer.parseInt(args[0]));
        } else {
            s = new Supervisor(40404);
        }
        s.getCommandFinder().addCommand(CommandEchoString.class);
        s.start();
    }
}
