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

package fr.ensicaen.ecole.oasmr.lib;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ProcessBuilderUtil {

    private static BufferedReader getOutputStream(Process p) {
        return new BufferedReader(new InputStreamReader(p.getInputStream()));
    }

    private static BufferedReader getErrorStream(Process p) {
        return new BufferedReader(new InputStreamReader(p.getErrorStream()));
    }

    private static String StreamToString(BufferedReader output) throws IOException {
        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = output.readLine()) != null) {
            builder.append(line);
            builder.append(System.getProperty("line.separator"));
        }
        return builder.toString();
    }

    public static String getOutputError(Process p) throws IOException {
        BufferedReader output = getErrorStream(p);
        return StreamToString(output);
    }

    public static String getOutput(Process p) throws IOException {
        BufferedReader output = getOutputStream(p);
        return StreamToString(output);
    }


}
