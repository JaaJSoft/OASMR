package fr.ensicaen.ecole.oasmr.lib.crypto;

import javax.crypto.spec.DHParameterSpec;
import java.security.KeyPair;
import java.security.PublicKey;

public final class ServerSecurity extends Diffie_Hellman {
    private DHParameterSpec DHSpec;
    private KeyPair keyPair;
    private byte[] serverPrivateKey;
    private byte[] AESKey;

    public ServerSecurity() throws Exception {
        DHSpec = Diffie_Hellman.generateParameters();
    }

    private byte[] sign(String s) {
        return AESKey;
    }

    public byte[] keyExchange() throws Exception {
        keyPair = Diffie_Hellman.createKeys(DHSpec);
        String publicKey = keyPair.getPublic().toString();
        return sign(DHSpec.toString() + publicKey);
    }

    public void setAESKey(PublicKey publicKey) throws Exception {
        AESKey = Diffie_Hellman.newKeyAgreement(publicKey, keyPair.getPrivate());
    }
}
