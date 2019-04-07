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

import java.nio.charset.StandardCharsets;

public class AES_Test {
    public static void main(String[] args)
    {
        String secretKey = "jaaaaaaaaaaaaaaaaaaaj";
        byte[] key = secretKey.getBytes(StandardCharsets.UTF_8);

        String originalString = "OASMR";
        byte[] message = originalString.getBytes(StandardCharsets.UTF_8);
        byte[] encryptedMessage = AES.encrypt(message, key) ;
        byte[] decryptedMessage = AES.decrypt(encryptedMessage, key) ;

        System.out.println(originalString);
        System.out.println(new String(encryptedMessage));
        System.out.println(new String(decryptedMessage));
    }
}
