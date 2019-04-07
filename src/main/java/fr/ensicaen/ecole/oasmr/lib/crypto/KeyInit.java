package fr.ensicaen.ecole.oasmr.lib.crypto;

import javax.crypto.spec.DHParameterSpec;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.PublicKey;

public class KeyInit implements Serializable {

    private PublicKey DHPublicKey;
    private BigInteger p;
    private BigInteger g;
    private int l;

    public KeyInit(DHParameterSpec DHSpec, PublicKey DHPublicKey) {
        this.DHPublicKey = DHPublicKey;
        p = DHSpec.getP();
        g = DHSpec.getG();
        l = DHSpec.getL();
    }

    public DHParameterSpec getDHSpec() {
        return new DHParameterSpec(p, g, l);
    }

    public PublicKey getDHPublicKey() {
        return DHPublicKey;
    }
}
