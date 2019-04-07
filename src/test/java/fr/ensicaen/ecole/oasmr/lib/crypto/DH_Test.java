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

public class DH_Test extends Diffie_Hellman
{
    public static void main(String[] args) throws Exception {

        DHParameterSpec DHSpec = Diffie_Hellman.generateParameters();

        System.out.println("p:" + DHSpec.getP());
        System.out.println("g:" + DHSpec.getG());
        System.out.println("l:" + DHSpec.getL());

        KeyPair keyPairA = Diffie_Hellman.createKeys(DHSpec);
        KeyPair keyPairB = Diffie_Hellman.createKeys(DHSpec);

        byte[] keyA = Diffie_Hellman.newKeyAgreement(keyPairA.getPublic(), keyPairB.getPrivate());
        byte[] keyB = Diffie_Hellman.newKeyAgreement(keyPairB.getPublic(), keyPairA.getPrivate());

        System.out.println(new String(keyA).equals(new String(keyB)));

        byte[] message = "OASMR".getBytes();
        System.out.println(new String(message));
        byte[] encryptedMessage = AES.encrypt(message, keyA);
        System.out.println(new String(AES.decrypt(encryptedMessage, keyB)));
    }
}

