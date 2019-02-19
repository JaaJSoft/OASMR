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

package fr.ensicaen.ecole.oasmr.app;

import java.util.Properties;

public class Session {
    private static Properties session = new Properties();

    private Session() {

    }

    public static String getProperty(String key) {
        return session.getProperty(key);
    }

    public static String getProperty(String key, String defaultValue) {
        return session.getProperty(key, defaultValue);
    }

    public static void setProperty(String key, String value) {
        session.setProperty(key, value);
    }
}
