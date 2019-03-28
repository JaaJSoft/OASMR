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

import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static java.nio.file.StandardCopyOption.*;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 * A command that copy a file.
 */
public class CommandCopyFile extends Command {

    /**
     * The source file path name to copy.
     */
    private final String sourceFilePathName;

    /**
     * The destination file path name to copy.
     */
    private final String destinationFilePathName;

    /**
     * @param sourceFilePathName      The source file path name to copy.
     * @param destinationFilePathName The destination file path name to copy.
     */
    public CommandCopyFile(String sourceFilePathName, String destinationFilePathName) {
        this.sourceFilePathName = sourceFilePathName;
        this.destinationFilePathName = destinationFilePathName;
    }

    @Override
    protected Serializable execute(Object... params) throws Exception {

        if(Paths.get(sourceFilePathName).equals(Paths.get(destinationFilePathName))){
            return false;
        }

        //TODO : CopyDir
        Path result = Files.copy(Paths.get(sourceFilePathName), Paths.get(destinationFilePathName), StandardCopyOption.REPLACE_EXISTING);
        return Files.exists(result);
    }

    @Override
    public String toString() {
        return "copy file " + sourceFilePathName + " -> " + destinationFilePathName;
    }
}
