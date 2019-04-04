package fr.ensicaen.ecole.oasmr.lib.crypto;

import java.security.*;

import javax.crypto.spec.DHParameterSpec;

public class Diffie_Hellman {

    public static KeyPair createKeys(DHParameterSpec dhSpec) throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("DiffieHellman");
        keyPairGenerator.initialize(dhSpec);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        return keyPair;
    }

    //public static byte[] keyAgreement(PublicKey publicKey, PrivateKey privateKey) { }

    public static DHParameterSpec generateParameters() throws Exception {
        AlgorithmParameterGenerator paramGenerator = AlgorithmParameterGenerator.getInstance("DiffieHellman");
        paramGenerator.init(1024);

        AlgorithmParameters params = paramGenerator.generateParameters();
        DHParameterSpec DHSpec = params.getParameterSpec(DHParameterSpec.class);

        return DHSpec;
    }
}