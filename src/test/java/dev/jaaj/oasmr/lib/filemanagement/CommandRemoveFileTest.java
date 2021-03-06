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

package dev.jaaj.oasmr.lib.filemanagement;

import org.junit.Test;

import static org.junit.Assert.assertFalse;

public class CommandRemoveFileTest extends AbstractFileTest {

    @Test
    public void execute() throws Exception {
        CommandRemoveFile command = new CommandRemoveFile(this.fileName1);
        command.executeCommand();
        assertFalse(file1.exists());
    }
}
