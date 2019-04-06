package fr.ensicaen.ecole.oasmr.lib.crypto;

import javax.crypto.spec.DHParameterSpec;

import java.security.KeyPair;

public class DH_Test extends Diffie_Hellman
{
    public static void main(String[] args) throws Exception {

        DHParameterSpec DHSpec = Diffie_Hellman.generateParameters();

        System.out.println("p:" + DHSpec.getP());
        System.out.println("g:" + DHSpec.getG());
        System.out.println("l:" + DHSpec.getL());

        KeyPair keyPairA = Diffie_Hellman.createKeys(DHSpec);
        KeyPair keyPairB = Diffie_Hellman.createKeys(DHSpec);

        byte[] keyA = Diffie_Hellman.newKeyAgreement(keyPairA.getPublic(), keyPairB.getPrivate());
        byte[] keyB = Diffie_Hellman.newKeyAgreement(keyPairB.getPublic(), keyPairA.getPrivate());

        System.out.println(new String(keyA).equals(new String(keyB)));

        String message = "OASMR";
        System.out.println(message);
        String m = AES.encrypt(message, keyA);
        System.out.println(AES.decrypt(m, keyB));
    }
}

