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
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Serializable;

/**
 * A command that copy a file.
 */
public class CommandCopyFile extends Command {

    /**
     * The source file path name to copy.
     */
    private String sourceFilePathName;

    /**
     * The destination file path name to copy.
     */
    private String destinationFilePathName;

    /**
     *
     * @param sourceFilePathName The source file path name to copy.
     * @param destinationFilePathName The destination file path name to copy.
     */
    public CommandCopyFile(String sourceFilePathName, String destinationFilePathName) {
        this.sourceFilePathName = sourceFilePathName;
        this.destinationFilePathName = destinationFilePathName;
    }

    @Override
    protected Serializable execute(Object... params) throws Exception {
        FileReader in = null;
        FileWriter out = null;

        try {
            in = new FileReader(sourceFilePathName);
            out = new FileWriter(destinationFilePathName);

            int c;
            while ((c = in.read()) != -1) {
                out.write(c);
            }
        }finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }

        return true;
    }

    @Override
    public String toString() {
        return null;
    }
}
