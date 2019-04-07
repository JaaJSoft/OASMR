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

import fr.ensicaen.ecole.oasmr.lib.command.Command;
import sun.security.provider.DSAPrivateKey;
import javax.crypto.SealedObject;
import javax.crypto.spec.DHParameterSpec;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.DSAPrivateKeySpec;

public final class SupervisorSecurity extends Diffie_Hellman {
    private DHParameterSpec DHSpec;
    private KeyPair keyPair;
    private DSAPrivateKey supervisorPrivateKey;
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
        DSAPrivateKeySpec ks = new DSAPrivateKeySpec((BigInteger) ois.readObject(), (BigInteger) ois.readObject(),
                                                     (BigInteger) ois.readObject(), (BigInteger) ois.readObject());
        KeyFactory keyFactory = KeyFactory.getInstance("DSA");
        supervisorPrivateKey = (DSAPrivateKey) keyFactory.generatePrivate(ks);
    }

    private SignedObject sign(KeyInit keyInit) throws Exception {
        Signature dsa = Signature.getInstance("SHA256withDSA");
        return new SignedObject(keyInit, supervisorPrivateKey, dsa);
    }

    public SignedObject keyExchange() throws Exception {
        keyPair = Diffie_Hellman.createKeys(DHSpec);
        return sign(new KeyInit(DHSpec, keyPair.getPublic()));
    }

    public void setAESKey(KeyInit keyInit) throws Exception {
        AESKey = Diffie_Hellman.newKeyAgreement(keyInit.getDHPublicKey(), keyPair.getPrivate());
    }

    public byte[] encrypt(byte[] message) {
        return AES.encrypt(message, AESKey);
    }

    public byte[] decrypt(byte[] message) {
        return AES.decrypt(message, AESKey);
    }

    public SealedObject encrypt(Command command) {
        return AES.encrypt(command, AESKey);
    }

    public Command decrypt(SealedObject sealedCommand) {
        return AES.decrypt(sealedCommand, AESKey);
    }
}
