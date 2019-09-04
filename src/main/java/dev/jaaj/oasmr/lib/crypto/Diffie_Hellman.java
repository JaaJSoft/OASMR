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

package dev.jaaj.oasmr.lib.crypto;

import java.security.*;

import javax.crypto.KeyAgreement;
import javax.crypto.spec.DHParameterSpec;

public class Diffie_Hellman {

    protected static KeyPair createKeys(DHParameterSpec dhSpec) throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("DiffieHellman");
        keyPairGenerator.initialize(dhSpec);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        return keyPair;
    }

    protected static byte[] newKeyAgreement(PublicKey publicKey, PrivateKey privateKey) throws Exception {
        KeyAgreement keyAgreement = KeyAgreement.getInstance("DiffieHellman");
        keyAgreement.init(privateKey);
        keyAgreement.doPhase(publicKey, true);
        return keyAgreement.generateSecret();
    }

    public static DHParameterSpec generateParameters() throws Exception {
        AlgorithmParameterGenerator paramGenerator = AlgorithmParameterGenerator.getInstance("DiffieHellman");
        paramGenerator.init(1024);

        AlgorithmParameters params = paramGenerator.generateParameters();
        DHParameterSpec DHSpec = params.getParameterSpec(DHParameterSpec.class);

        return DHSpec;
    }
}
