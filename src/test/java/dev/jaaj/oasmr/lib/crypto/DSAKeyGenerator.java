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

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.interfaces.DSAPrivateKey;
import java.security.spec.DSAPrivateKeySpec;
import java.security.spec.DSAPublicKeySpec;

public class DSAKeyGenerator {
    public static void main(String[] args) throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("DSA");
        keyPairGenerator.initialize(1024, new SecureRandom());
        KeyPair keyPair = keyPairGenerator.genKeyPair();
        Class spec = Class.forName("java.security.spec.DSAPrivateKeySpec");
        KeyFactory kf = KeyFactory.getInstance("DSA");
        DSAPrivateKeySpec prks = (DSAPrivateKeySpec) kf.getKeySpec(keyPair.getPrivate(), spec);
        System.out.println(prks.getX());
        System.out.println(prks.getP());
        System.out.println(prks.getQ());
        System.out.println(prks.getG());
        FileOutputStream fos = new FileOutputStream("supervisorKeySpec");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(prks.getX());
        oos.writeObject(prks.getP());
        oos.writeObject(prks.getQ());
        oos.writeObject(prks.getG());

        Class spec2 = Class.forName("java.security.spec.DSAPublicKeySpec");
        DSAPublicKeySpec puks = (DSAPublicKeySpec) kf.getKeySpec(keyPair.getPublic(), spec2);
        System.out.println(puks.getY());
        System.out.println(puks.getP());
        System.out.println(puks.getQ());
        System.out.println(puks.getG());
        FileOutputStream fos2 = new FileOutputStream("nodeKeySpec");
        ObjectOutputStream oos2 = new ObjectOutputStream(fos2);
        oos2.writeObject(puks.getY());
        oos2.writeObject(puks.getP());
        oos2.writeObject(puks.getQ());
        oos2.writeObject(puks.getG());
    }
}
