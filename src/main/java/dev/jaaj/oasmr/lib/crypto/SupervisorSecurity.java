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

import dev.jaaj.oasmr.lib.command.Command;
import sun.security.provider.DSAPrivateKey;

import javax.crypto.SealedObject;
import javax.crypto.spec.DHParameterSpec;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.DSAParameterSpec;
import java.security.spec.DSAPrivateKeySpec;

public final class SupervisorSecurity extends Diffie_Hellman {
    private DHParameterSpec DHSpec;
    private KeyPair keyPair;
    private DSAPrivateKey supervisorPrivateKey;
    private DSAParameterSpec DSASpec;
    private byte[] AESKey;

    public SupervisorSecurity() throws Exception {
        newDHSpec();
        setDSAParameters();
    }

    public SupervisorSecurity(DHParameterSpec spec) throws Exception {
        DHSpec = spec;
        setDSAParameters();
    }

    public void newDHSpec() throws Exception {
        DHSpec = Diffie_Hellman.generateParameters();
    }

    private void setDSAParameters() throws  Exception{
        FileInputStream fis = new FileInputStream("supervisorKeySpec");
        ObjectInputStream ois = new ObjectInputStream(fis);
        DSAPrivateKeySpec ks = new DSAPrivateKeySpec((BigInteger) ois.readObject(), (BigInteger) ois
                .readObject(), (BigInteger) ois.readObject(), (BigInteger) ois.readObject());
        KeyFactory kf = KeyFactory.getInstance("DSA");
        supervisorPrivateKey = (DSAPrivateKey) kf.generatePrivate(ks);
        DSASpec = new DSAParameterSpec(ks.getP(), ks.getQ(), ks.getG());
    }

    private SignedObject sign(KeyInit keyInit) throws Exception {
        Signature dsa = Signature.getInstance("DSA");
        return new SignedObject(keyInit, supervisorPrivateKey, dsa);
    }

    public SignedObject keyExchange() throws Exception {
        keyPair = Diffie_Hellman.createKeys(DHSpec);
        return sign(new KeyInit(DHSpec, keyPair.getPublic()));
    }

    public void setAESKey(PublicKey publicKey) throws Exception {
        AESKey = Diffie_Hellman.newKeyAgreement(publicKey, keyPair.getPrivate());
    }

    public SealedObject encrypt(Command command) {
        return AES.encrypt(command, AESKey);
    }

    public Command decrypt(SealedObject sealedCommand) {
        return AES.decrypt(sealedCommand, AESKey);
    }
}
