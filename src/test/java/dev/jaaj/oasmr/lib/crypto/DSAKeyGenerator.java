package dev.jaaj.oasmr.lib.crypto;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.interfaces.DSAPrivateKey;
import java.security.spec.DSAPrivateKeySpec;
import java.security.spec.DSAPublicKeySpec;

public class DSAKeyGenerator {
    public static void main(String[] args) throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("DSA");
        keyPairGenerator.initialize(1024, new SecureRandom());
        KeyPair keyPair = keyPairGenerator.genKeyPair();
        Class spec = Class.forName("java.security.spec.DSAPrivateKeySpec");
        KeyFactory kf = KeyFactory.getInstance("DSA");
        DSAPrivateKeySpec prks = (DSAPrivateKeySpec) kf.getKeySpec(keyPair.getPrivate(), spec);
        System.out.println(prks.getX());
        System.out.println(prks.getP());
        System.out.println(prks.getQ());
        System.out.println(prks.getG());
        FileOutputStream fos = new FileOutputStream("supervisorKeySpec");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(prks.getX());
        oos.writeObject(prks.getP());
        oos.writeObject(prks.getQ());
        oos.writeObject(prks.getG());

        Class spec2 = Class.forName("java.security.spec.DSAPublicKeySpec");
        DSAPublicKeySpec puks = (DSAPublicKeySpec) kf.getKeySpec(keyPair.getPublic(), spec2);
        System.out.println(puks.getY());
        System.out.println(puks.getP());
        System.out.println(puks.getQ());
        System.out.println(puks.getG());
        FileOutputStream fos2 = new FileOutputStream("nodeKeySpec");
        ObjectOutputStream oos2 = new ObjectOutputStream(fos2);
        oos2.writeObject(puks.getY());
        oos2.writeObject(puks.getP());
        oos2.writeObject(puks.getQ());
        oos2.writeObject(puks.getG());
    }
}
