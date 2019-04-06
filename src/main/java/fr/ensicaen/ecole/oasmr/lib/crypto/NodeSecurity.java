package fr.ensicaen.ecole.oasmr.lib.crypto;

import fr.ensicaen.ecole.oasmr.lib.command.Command;
import sun.security.provider.DSAPrivateKey;
import sun.security.provider.DSAPublicKey;

import javax.crypto.SealedObject;
import javax.crypto.spec.DHParameterSpec;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.DSAParameterSpec;
import java.security.spec.DSAPrivateKeySpec;
import java.security.spec.DSAPublicKeySpec;

public final class NodeSecurity extends Diffie_Hellman {
    private KeyPair keyPair;
    private DSAPublicKey supervisorPublicKey;
    private DSAParameterSpec DSASpec;
    private byte[] AESKey;

    public NodeSecurity() throws Exception {
        setDSAParameters();
    }

    private void setDSAParameters() throws  Exception{
        FileInputStream fis = new FileInputStream("nodeKeySpec");
        ObjectInputStream ois = new ObjectInputStream(fis);
        DSAPublicKeySpec ks = new DSAPublicKeySpec((BigInteger) ois.readObject(), (BigInteger) ois
                .readObject(), (BigInteger) ois.readObject(), (BigInteger) ois.readObject());
        KeyFactory kf = KeyFactory.getInstance("DSA");
        supervisorPublicKey = (DSAPublicKey) kf.generatePublic(ks);
        DSASpec = new DSAParameterSpec(ks.getP(), ks.getQ(), ks.getG());
    }

    public boolean verifySignature(SignedObject signedKeyInit) throws Exception {
        Signature dsa = Signature.getInstance("DSA");
        return signedKeyInit.verify(supervisorPublicKey, dsa);
    }

    public byte[] keyExchange(DHParameterSpec DHSpec) throws Exception {
        keyPair = Diffie_Hellman.createKeys(DHSpec);
        return keyPair.getPublic().getEncoded();
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
