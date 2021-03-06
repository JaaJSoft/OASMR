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

import dev.jaaj.oasmr.lib.command.Command;

import java.io.File;
import java.io.FilenameFilter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Lists all the files of a directory.
 */
public class CommandListFiles extends Command {

    /**
     * The directory path name to list.
     */
    private final String directoryPathName;

    /**
     * A filter
     */
    private final FilenameFilter filter;

    /**
     * @param directoryPathName The directory path name to list.
     */
    public CommandListFiles(String directoryPathName) {
        this.directoryPathName = directoryPathName;
        this.filter = null;
    }

    /**
     * @param directoryPathName The directory path name to list.
     * @param filter            a filter
     */
    public CommandListFiles(String directoryPathName, FilenameFilter filter) {
        this.directoryPathName = directoryPathName;
        this.filter = filter;
    }

    @Override
    protected Serializable execute(Object... params) throws Exception {
        File file = new File(directoryPathName);
        File[] listFile;
        List<String> listPath = new ArrayList<>();
        if(file.canRead()){
            if (filter == null)
                listFile = file.listFiles();
            else
                listFile = file.listFiles(filter);
            for (File f : listFile) {
                listPath.add(f.getAbsolutePath());
            }
        }
        listPath.sort(String::compareTo);
        return listPath.toArray(new String[listPath.size()]);
    }

    @Override
    public String toString() {
        return "Command get files list";
    }
}
