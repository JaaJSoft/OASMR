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

import fr.ensicaen.ecole.oasmr.lib.packagemanagment.apt.exceptions.ExceptionAptPackageNotFound;
import org.junit.Test;
import static org.junit.Assert.*;

/*ToDo: Complete this test: how check efficiently if we can pass the test?? */

public class CommandAptShowTest {

    @Test
    public void execute() throws Exception{
        CommandAptShow c = new CommandAptShow("gcc");
        assertEquals("".getClass().getTypeName(), c.execute().getClass().getTypeName());
    }

    @Test(expected = ExceptionAptPackageNotFound.class)
    public void executeFailure() throws Exception{
        CommandAptShow c = new CommandAptShow("jaaj");
        c.execute();
    }
}
