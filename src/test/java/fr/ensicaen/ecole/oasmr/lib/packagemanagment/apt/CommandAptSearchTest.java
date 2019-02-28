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

import fr.ensicaen.ecole.oasmr.lib.packagemanagment.apt.exceptions.ExceptionAptSearchFailure;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CommandAptSearchTest {
    @Test
    public void execute() throws Exception{
        CommandAptSearch c = new CommandAptSearch("apcalc");
        assert(c.execute() instanceof String);
    }

    @Test
    public void execute1() throws Exception{
        CommandAptSearch c = new CommandAptSearch("eanfifjeaucbnaoebuifsq");
        assert(c.execute() instanceof String);
    }
    //how have exception?
}
