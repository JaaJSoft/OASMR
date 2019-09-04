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

package dev.jaaj.oasmr.app.controller.node;

import org.junit.Test;

import static org.junit.Assert.*;

public class FileAdapterTest {
    FileAdapter fileAdapter = new FileAdapter("/jeej/AH.java", false);
    FileAdapter fileAdapter2 = new FileAdapter("/jeej/AH", true);
    FileAdapter fileAdapter3 = new FileAdapter("/jeej/AH/", true);


    @Test
    public void getNameFile() {
        assertEquals("AH.java", fileAdapter.getName());
    }

    @Test
    public void getNameDir() {
        assertEquals("AH", fileAdapter2.getName());
        assertEquals("AH", fileAdapter3.getName());

    }
}