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

import fr.ensicaen.ecole.oasmr.lib.command.Command;

import java.io.File;
import java.io.Serializable;

/**
 * A command that creates a directory given a path name.
 */
public class CommandMakeDiretory extends Command {

    /**
     * The directory path name to be created.
     */
    private String directoryPathName;

    /**
     *
     * @param directoryPathName The directory path name to be created.
     */
    public CommandMakeDiretory(String directoryPathName) {
        this.directoryPathName = directoryPathName;
    }

    @Override
    protected Serializable execute(Object... params) throws Exception {
        File newDirectory = new File(directoryPathName);

        return newDirectory.mkdir();
    }

    @Override
    public String toString() {
        return null;
    }
}
