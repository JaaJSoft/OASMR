package fr.ensicaen.ecole.oasmr.supervisor;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/* Pour le moment on n'utilise pas le salt */

public class HashUtil {
    private byte[] _salt;

    public HashUtil() {
        try {
            _salt = getSalt();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }


    public static String get_SHA_SecurePassword(String passwordToHash, /*byte[] salt,*/ String SHAtype)//SHAtype can be "SHA-1", "SHA-256", "SHA-384" or "SHA-512"
    {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance(SHAtype);
            //md.update(salt);
            byte[] bytes = md.digest(passwordToHash.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    private byte[] getSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }


}


