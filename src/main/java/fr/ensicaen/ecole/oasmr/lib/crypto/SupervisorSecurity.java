package fr.ensicaen.ecole.oasmr.lib.crypto;

import fr.ensicaen.ecole.oasmr.lib.command.Command;

import javax.crypto.spec.DHParameterSpec;
import java.io.Serializable;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;

public final class SupervisorSecurity extends Diffie_Hellman {
    private DHParameterSpec DHSpec;
    private KeyPair keyPair;
    private PrivateKey supervisorPrivateKey;
    private byte[] AESKey;

    public SupervisorSecurity() throws Exception {
        newDHSpec();
    }

    public SupervisorSecurity(DHParameterSpec spec) {
        DHSpec = spec;
    }

    public void newDHSpec() throws Exception {
        DHSpec = Diffie_Hellman.generateParameters();
    }

    private byte[] sign(byte[] message) throws Exception {
        Signature ecdsa = Signature.getInstance("SHA256withECDSA");
        ecdsa.initSign(supervisorPrivateKey);
        ecdsa.update(message);
        return ecdsa.sign();
    }

    public byte[] keyExchange() throws Exception {
        keyPair = Diffie_Hellman.createKeys(DHSpec);
        byte[] publicKey = keyPair.getPublic().getEncoded();
        return sign(publicKey);
    }

    public void setAESKey(PublicKey publicKey) throws Exception {
        AESKey = Diffie_Hellman.newKeyAgreement(publicKey, keyPair.getPrivate());
    }

    //public byte[] encrypt(Command command) { }

    //public Command decrypt(byte[] command) { }
}
