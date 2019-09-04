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

import org.junit.Before;
import org.junit.Test;
import oshi.hardware.CentralProcessor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class CommandGetProcessorInfoTest {

    private CommandGetProcessorInfo c;
    private CentralProcessor processor;

    @Before
    public void setUp() {
        c = new CommandGetProcessorInfo();
        processor = SystemInfoSingleton.getHardware().getProcessor();
    }

    @Test
    public void execute() throws Exception {
        int[] cpuInfoFromCommand = (int[]) c.execute();
        int[] cpuInfo = new int[]{
                processor.getPhysicalPackageCount(),
                processor.getPhysicalProcessorCount(),
                processor.getLogicalProcessorCount()
        };
        assertEquals(cpuInfo.length, cpuInfoFromCommand.length);
        for(int i = 0; i < cpuInfo.length; i++){
            assertEquals(cpuInfo[i], cpuInfoFromCommand[i]);
        }
    }

    @Test
    public void executeFailure() throws Exception {
        int[] cpuInfoFromCommand = (int[]) c.execute();
        for (int info : cpuInfoFromCommand) {
            assertNotEquals(0, info);
        }
    }


}
