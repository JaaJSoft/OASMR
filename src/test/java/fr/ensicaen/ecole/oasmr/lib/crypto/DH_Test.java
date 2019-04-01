package fr.ensicaen.ecole.oasmr.lib.crypto;

import javax.crypto.spec.DHParameterSpec;

import static fr.ensicaen.ecole.oasmr.lib.crypto.Diffie_Hellman.generateParameters;

public class DH_Test {
    public static void main(String[] args) throws Exception {

        DHParameterSpec dhSpec = generateParameters();

        System.out.println("p:" + dhSpec.getP());
        System.out.println("g:" + dhSpec.getG());
        System.out.println("l:" + dhSpec.getL());
    }
}
