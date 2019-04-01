package fr.ensicaen.ecole.oasmr.lib.crypto;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.AlgorithmParameterGenerator;
import java.security.AlgorithmParameters;

import javax.crypto.spec.DHParameterSpec;
import javax.crypto.spec.DHPublicKeySpec;

public class Diffie_Hellman {

    public static void createKey(DHParameterSpec dhSpec) throws Exception {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("DiffieHellman");

        kpg.initialize(dhSpec);
        KeyPair kp = kpg.generateKeyPair();
        KeyFactory kfactory = KeyFactory.getInstance("DiffieHellman");

        DHPublicKeySpec kspec = (DHPublicKeySpec) kfactory.getKeySpec(kp.getPublic(),
                DHPublicKeySpec.class);
    }


    public static DHParameterSpec generateParameters() throws Exception {
        AlgorithmParameterGenerator paramGen = AlgorithmParameterGenerator.getInstance("DH");
        paramGen.init(1024);

        AlgorithmParameters params = paramGen.generateParameters();
        DHParameterSpec dhSpec = (DHParameterSpec) params.getParameterSpec(DHParameterSpec.class);

        return dhSpec;
    }
}