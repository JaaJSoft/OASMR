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

package fr.ensicaen.ecole.oasmr.lib.system;

import oshi.SystemInfo;

import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.OperatingSystem;

public class SystemInfoSingleton {

    private static SystemInfo instance = null;

    private static SystemInfo getInstance(){
        if(instance == null) {
            instance = new SystemInfo();//TODO AAAAAAAAAAAHHHHH
        }
        return instance;
    }

    public static HardwareAbstractionLayer getHardware(){
        return getInstance().getHardware();
    }

    public static OperatingSystem getOperatingSystem(){
        return getInstance().getOperatingSystem();
    }

}
