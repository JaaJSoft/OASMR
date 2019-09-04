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

import java.nio.file.Files;

import static junit.framework.TestCase.assertTrue;

public class CommandCopyFileTest  extends AbstractFileTest{

    @Test
    public void execute() throws Exception {
        CommandCopyFile commandCopyFile = new CommandCopyFile(this.fileName1, this.unusedFileName1);

        commandCopyFile.executeCommand();

        assertTrue(file1.exists());
        assertTrue(unusedFile1.exists());

        Files.isSameFile(file1.toPath(), unusedFile1.toPath());

    }

}
