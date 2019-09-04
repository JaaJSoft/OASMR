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

import org.junit.Before;
import org.junit.Test;
import oshi.PlatformEnum;
import oshi.SystemInfo;

import static org.junit.Assert.assertEquals;

public class CommandGetSystemTest {

    private CommandGetSystem c;
    private PlatformEnum os;

    @Before
    public void setUp() {
        c = new CommandGetSystem();
        os = SystemInfo.getCurrentPlatformEnum();
    }

    @Test
    public void execute() throws Exception {
        System.out.println(os);
        assertEquals(os.toString(), c.execute());
    }


}
