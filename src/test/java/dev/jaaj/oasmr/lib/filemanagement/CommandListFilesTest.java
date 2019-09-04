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

package dev.jaaj.oasmr.lib.filemanagement;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;

public class CommandListFilesTest extends AbstractFileTest {

    @Test
    public void execute() throws Exception {
        CommandListFiles commandListFiles = new CommandListFiles(directoryName1);
        String[] res = (String[])commandListFiles.executeCommand();
        Arrays.sort(res);
        Arrays.sort(this.listFilesDir1);
        assertArrayEquals(this.listFilesDir1, res);
    }
}
