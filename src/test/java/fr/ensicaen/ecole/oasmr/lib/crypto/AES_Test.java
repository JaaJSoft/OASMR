package fr.ensicaen.ecole.oasmr.lib.crypto;

import java.nio.charset.StandardCharsets;

public class AES_Test {
    public static void main(String[] args)
    {
        final String secretKey = "jaaaaaaaaaaaaaaaaaaaj";
        final byte[] key = secretKey.getBytes(StandardCharsets.UTF_8);

        String originalString = "OASMR";
        String encryptedString = AES.encrypt(originalString, key) ;
        String decryptedString = AES.decrypt(encryptedString, key) ;

        System.out.println(originalString);
        System.out.println(encryptedString);
        System.out.println(decryptedString);
    }
}
