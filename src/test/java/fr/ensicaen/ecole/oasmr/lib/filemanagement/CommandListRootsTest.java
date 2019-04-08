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

package fr.ensicaen.ecole.oasmr.lib.filemanagement;

import org.junit.Test;

import java.io.File;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class CommandListRootsTest {

    @Test
    public void execute() throws Exception {
        CommandListRoots c = new CommandListRoots();
        String[] roots = (String[]) c.execute();
        File[] rootsFile = File.listRoots();
        assertEquals(roots.length, rootsFile.length);
        for(int i = 0; i<roots.length; i++){
            assertEquals(roots[i], rootsFile[i].getAbsolutePath());
        }
    }

}
