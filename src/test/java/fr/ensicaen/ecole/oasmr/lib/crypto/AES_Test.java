package fr.ensicaen.ecole.oasmr.lib.crypto;

public class AES_Test {
    public static void main(String[] args)
    {
        final String secretKey = "jaaaaaaaaaaaaaaaaaaaj";

        String originalString = "OASMR";
        String encryptedString = AES.encrypt(originalString, secretKey) ;
        String decryptedString = AES.decrypt(encryptedString, secretKey) ;

        System.out.println(originalString);
        System.out.println(encryptedString);
        System.out.println(decryptedString);
    }
}
