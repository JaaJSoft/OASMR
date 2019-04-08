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

public class CommandGetCpuUsageTest {

    private CommandGetCpuUsage c;
    private HardwareAbstractionLayer hal;

    @Before
    public void setUp() {
        c = new CommandGetCpuUsage();
        hal = SystemInfoSingleton.getHardware();
    }

    @Test
    public void execute() throws Exception {
        long[] cpuUsage = hal.getProcessor().getSystemCpuLoadTicks();
        long[] cpuUsageFromCommand = (long[]) c.execute();
        assertEquals(cpuUsage.length, cpuUsageFromCommand.length);
        for(int i = 0; i < cpuUsage.length; i++){
            assertEquals(cpuUsage[i], cpuUsageFromCommand[i]);
        }
    }

    @Test
    public void executeFailure() throws Exception {
        long[] cpuUsageFromCommand = (long[]) c.execute();
        for (long usage : cpuUsageFromCommand) {
            assertNotEquals(-1, usage);
        }
    }

}
