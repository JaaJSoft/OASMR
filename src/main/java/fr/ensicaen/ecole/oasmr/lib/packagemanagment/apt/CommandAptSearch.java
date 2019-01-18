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

package fr.ensicaen.ecole.oasmr.lib.packagemanagment.apt;

import fr.ensicaen.ecole.oasmr.lib.ProcessBuilderUtil;
import fr.ensicaen.ecole.oasmr.lib.command.Command;
import fr.ensicaen.ecole.oasmr.lib.packagemanagment.apt.exceptions.ExceptionAptSearchFailure;

import java.io.IOException;
import java.io.Serializable;

public class CommandAptSearch extends Command {

    private final String seachingTerms;

    public CommandAptSearch(String packageName) {
        this.seachingTerms = packageName;
    }

    @Override
    public Serializable execute(Object... params) throws Exception {
        ProcessBuilder processBuilder = new ProcessBuilder("apt", "search", seachingTerms);
        try {
            Process p = processBuilder.start();
            p.waitFor();
            int ret = p.exitValue();

            switch (ret) {
                case 0:
                    return ProcessBuilderUtil.getOutput(p);
                default:
                    throw new ExceptionAptSearchFailure(ProcessBuilderUtil.getOutputError(p));


                //Il peut ne pas y avoir de résultat à la recherche meme si ça se passe bien
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return e;
        }
    }

    @Override
    public String toString() {
        return "apt" + " " + "search" + " " + seachingTerms;
    }
}
