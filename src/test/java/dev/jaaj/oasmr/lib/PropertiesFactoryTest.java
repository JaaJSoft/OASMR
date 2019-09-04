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

package dev.jaaj.oasmr.lib;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import static org.junit.Assert.*;

public class PropertiesFactoryTest {
    private Properties p;

    @Before
    public void setUp() throws Exception {
        p = PropertiesFactory.getProperties("test.properties");
        p.setProperty("test", "test");

    }

    @After
    public void tearDown() throws Exception {
        //new File("test.properties").delete();
    }

    @Test
    public void getPropertiesWithNewFile() throws IOException {
        Properties p = PropertiesFactory.getProperties("jeej");
        new File("jeej").delete();
    }

    @Test
    public void getPropertiesWithExistantFile() throws IOException {
        new File("jeej").createNewFile();
        Properties p = PropertiesFactory.getProperties("jeej");
        new File("jeej").delete();
    }

    @Test
    public void getPropertiesDoesNotExist() {
        assertNull(p.getProperty("jeej"));

    }

    @Test
    public void getProperties() {
        assertEquals("test", p.getProperty("test"));
    }


}