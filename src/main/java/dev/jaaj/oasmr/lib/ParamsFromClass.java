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

package dev.jaaj.oasmr.lib;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

public class ParamsFromClass {

    public static List<Parameter[]> getParamsFromClass(Class c) {
        Constructor[] constructors = c.getConstructors();
        List<Parameter[]> parameters = new ArrayList<>(constructors.length);
        for (Constructor constructor : constructors) {
            Parameter[] constructorParameters = constructor.getParameters();
            parameters.add(constructorParameters);
        }
        return parameters;
    }
}