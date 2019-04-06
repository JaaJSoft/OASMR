package fr.ensicaen.ecole.oasmr.lib.crypto;

import javax.crypto.spec.DHParameterSpec;
import java.security.KeyPair;

public final class ServerSecurity extends Diffie_Hellman {
    private DHParameterSpec DHSpec;
    private KeyPair keyPair;
    private byte[] serverPrivateKey;
    private byte[] AESKey;


}
