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

package fr.ensicaen.ecole.oasmr.lib.crypto;

import javax.crypto.spec.DHParameterSpec;
import java.security.KeyPair;
import java.security.SignedObject;

public class DH_Test extends Diffie_Hellman
{
    public static void main(String[] args) throws Exception {

        DHParameterSpec DHSpec = Diffie_Hellman.generateParameters(512);

        System.out.println("p:" + DHSpec.getP());
        System.out.println("g:" + DHSpec.getG());
        System.out.println("l:" + DHSpec.getL());

        SupervisorSecurity supervisorSecurity = new SupervisorSecurity(DHSpec);
        SignedObject signedObject = supervisorSecurity.keyExchange();

        NodeSecurity nodeSecurity = new NodeSecurity();
        KeyInit keyInit = nodeSecurity.keyExchange(signedObject);

        supervisorSecurity.setAESKey(keyInit);

        byte[] message = "OASMR".getBytes();
        System.out.println(new String(message));
        byte[] encryptedMessage = supervisorSecurity.encrypt(message);
        System.out.println(new String(nodeSecurity.decrypt(encryptedMessage)));
    }
}
