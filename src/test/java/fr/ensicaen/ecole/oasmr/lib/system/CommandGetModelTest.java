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

import org.junit.Before;
import org.junit.Test;
import oshi.hardware.HardwareAbstractionLayer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class CommandGetModelTest {

    private CommandGetModel c;
    private HardwareAbstractionLayer hal;

    @Before
    public void setUp() {
        c = new CommandGetModel();
        hal = SystemInfoSingleton.getHardware();
    }

    @Test
    public void execute() throws Exception {
        assertEquals(hal.getComputerSystem().getModel(), c.execute());
    }

    @Test
    public void executeFailure() throws Exception {
        assertNotEquals("", c.execute());
    }

}
