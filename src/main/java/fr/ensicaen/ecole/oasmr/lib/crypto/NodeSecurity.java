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
import fr.ensicaen.ecole.oasmr.lib.crypto.exceptions.InvalidSignatureException;
import sun.security.provider.DSAPublicKey;
import javax.crypto.SealedObject;
import javax.crypto.spec.DHParameterSpec;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.DSAParameterSpec;
import java.security.spec.DSAPublicKeySpec;

public final class NodeSecurity extends Diffie_Hellman {
    private KeyPair keyPair;
    private DSAPublicKey supervisorPublicKey;
    private DSAParameterSpec DSASpec;
    private byte[] AESKey;

    public NodeSecurity() throws Exception {
        setDSAParameters();
    }

    private void setDSAParameters() throws  Exception {
        FileInputStream fis = new FileInputStream("nodeKeySpec");
        ObjectInputStream ois = new ObjectInputStream(fis);
        DSAPublicKeySpec ks = new DSAPublicKeySpec((BigInteger) ois.readObject(), (BigInteger) ois.readObject(),
                                                    (BigInteger) ois.readObject(), (BigInteger) ois.readObject());
        KeyFactory keyFactory = KeyFactory.getInstance("DSA");
        supervisorPublicKey = (DSAPublicKey) keyFactory.generatePublic(ks);
        DSASpec = new DSAParameterSpec(ks.getP(), ks.getQ(), ks.getG());
    }

    public boolean verifySignature(SignedObject signedKeyInit) throws Exception {
        Signature dsa = Signature.getInstance("SHA256withDSA");
        return signedKeyInit.verify(supervisorPublicKey, dsa);
    }

    public KeyInit keyExchange(SignedObject signedKeyInit) throws Exception {
        if (verifySignature(signedKeyInit)) {
            KeyInit keyInit = (KeyInit) signedKeyInit.getObject();
            PublicKey publicKey = getPublicKey(keyInit.getDHSpec());
            setAESKey(keyInit.getDHPublicKey());
            return new KeyInit(publicKey);
        } else {
            throw new InvalidSignatureException();
        }
    }

    private PublicKey getPublicKey(DHParameterSpec DHSpec) throws Exception {
        keyPair = Diffie_Hellman.createKeys(DHSpec);
        return keyPair.getPublic();
    }

    public void setAESKey(PublicKey publicKey) throws Exception {
        AESKey = Diffie_Hellman.newKeyAgreement(publicKey, keyPair.getPrivate());
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
