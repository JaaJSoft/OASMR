package fr.ensicaen.ecole.oasmr.lib.crypto;

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
