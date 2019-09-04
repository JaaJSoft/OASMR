/*
 * Copyright (c) 2019. JaaJ-dev
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.jaaj.oasmr.lib.crypto;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

//TODO : Use the salt

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
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
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


