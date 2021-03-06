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

package dev.jaaj.oasmr.app.controller.node;

import java.io.File;

public class FileAdapter {

    private String path = "";
    private String name = "";
    private boolean isDir = false;

    public FileAdapter() {
    }

    public FileAdapter(String path, boolean isDir) {
        this.path = path;
        String[] filename = path.split(File.separator);
        if (filename.length > 0) {
            this.name = filename[filename.length - 1];
        } else {
            this.name = path;
        }
        this.isDir = isDir;
    }

    public boolean isDir() {
        return isDir;
    }

    public String getPath() {
        return path;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
