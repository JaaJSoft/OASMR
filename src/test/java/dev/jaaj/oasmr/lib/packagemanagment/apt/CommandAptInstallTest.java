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

import dev.jaaj.oasmr.lib.packagemanagment.apt.exceptions.ExceptionAptInstallFailure;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CommandAptInstallTest {
    @Test
    public void execute() throws Exception{
        CommandAptInstall c = new CommandAptInstall("apcalc");
        assertEquals("".getClass().getName(), c.execute().getClass().getName());
    }

    @Test
    public void executeAlreadyInstalledPackage() throws Exception{
        CommandAptInstall c = new CommandAptInstall("gcc");
        assertEquals("".getClass().getName(), c.execute().getClass().getName());
    }

    @Test(expected = ExceptionAptInstallFailure.class)
    public void executeFailure() throws Exception{
        CommandAptInstall c = new CommandAptInstall("eanfifjeaucbnaoebuifsq");
        c.execute();
    }
}
