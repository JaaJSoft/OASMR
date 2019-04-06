package fr.ensicaen.ecole.oasmr.lib.crypto;

import javax.crypto.spec.DHParameterSpec;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;

public final class NodeSecurity extends Diffie_Hellman {
    private KeyPair keyPair;
    private PublicKey supervisorPublicKey;
    private byte[] AESKey;


    private boolean verifySignature(byte[] message, byte[] signature) throws Exception {
        Signature ecdsa = Signature.getInstance("SHA256withECDSA");
        ecdsa.initVerify(supervisorPublicKey);
        ecdsa.update(message);
        return ecdsa.verify(signature);
    }

    public byte[] keyExchange(DHParameterSpec DHSpec) throws Exception {
        keyPair = Diffie_Hellman.createKeys(DHSpec);
        return keyPair.getPublic().getEncoded();
    }

    public void setAESKey(PublicKey publicKey) throws Exception {
        AESKey = Diffie_Hellman.newKeyAgreement(publicKey, keyPair.getPrivate());
    }

    //public byte[] encrypt(Command command) { }

    //public Command decrypt(byte[] command) { }
}
