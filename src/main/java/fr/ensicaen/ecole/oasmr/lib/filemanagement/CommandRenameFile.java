/*
 *  Copyright (c) 2018. CCC-Development-Team
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
 * A command that renames a file
 */
public class CommandRenameFile extends Command {

    /**
     * The file name path to be renamed
     */
    private String fileNamePathToBeRenamed;

    /**
     * The new name of the file
     */
    private String newFileName;

    /**
     *
     * @param fileNamePathToBeRenamed The file name path to be renamed.
     * @param newFileName The new file name, without the path.
     */
    public CommandRenameFile(String fileNamePathToBeRenamed, String newFileName) {
        this.fileNamePathToBeRenamed = fileNamePathToBeRenamed;
        this.newFileName = newFileName;
    }

    @Override
    protected Serializable execute(Object... params) throws Exception {
        File file = new File(fileNamePathToBeRenamed);

        return file.renameTo(new File(file.getAbsolutePath().replace(file.getName(), "").concat(newFileName)));
    }

    @Override
    public String toString() {
        return null;
    }
}
