package dev.jaaj.oasmr.lib.crypto;

import java.nio.charset.StandardCharsets;

public class AES_Test {
    public static void main(String[] args)
    {
        String secretKey = "jaaaaaaaaaaaaaaaaaaaj";
        byte[] key = secretKey.getBytes(StandardCharsets.UTF_8);

        String originalString = "OASMR";
        byte[] message = originalString.getBytes(StandardCharsets.UTF_8);
        byte[] encryptedMessage = AES.encrypt(message, key) ;
        byte[] decryptedMessage = AES.decrypt(encryptedMessage, key) ;

        System.out.println(originalString);
        System.out.println(new String(encryptedMessage));
        System.out.println(new String(decryptedMessage));
    }
}
