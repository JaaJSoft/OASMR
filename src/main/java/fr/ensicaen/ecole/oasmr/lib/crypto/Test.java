package fr.ensicaen.ecole.oasmr.lib.crypto;

import java.math.BigInteger;
import java.util.Random;

public class Test {

    public static void main(String[] args) {
        BigInteger jeej = new BigInteger(4096, new Random());
        System.out.println(jeej);
        BigInteger jooj = new BigInteger(4096, 100, new Random());
        System.out.println(jooj);
    }
}
