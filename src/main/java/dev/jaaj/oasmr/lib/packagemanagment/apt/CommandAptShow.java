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

package dev.jaaj.oasmr.lib.packagemanagment.apt;

import dev.jaaj.oasmr.lib.packagemanagment.apt.exceptions.ExceptionAptPackageNotFound;
import dev.jaaj.oasmr.lib.ProcessBuilderUtil;
import dev.jaaj.oasmr.lib.command.Command;

import java.io.IOException;
import java.io.Serializable;

public class CommandAptShow extends Command {
    private final String packageName;

    public CommandAptShow(String packageName) {
        this.packageName = packageName;
    }

    @Override
    public Serializable execute(Object... params) throws Exception {
        ProcessBuilder processBuilder = new ProcessBuilder("apt", "show", packageName);
        try {
            Process p = processBuilder.start();
            p.waitFor();
            int ret = p.exitValue();

            if (ret == 0) {
                return ProcessBuilderUtil.getOutput(p);
            }
            throw new ExceptionAptPackageNotFound(ProcessBuilderUtil.getOutputError(p));

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return e;
        }
    }

    @Override
    public String toString() {
        return "apt" + " " + "show" + " " + packageName;
    }
}

