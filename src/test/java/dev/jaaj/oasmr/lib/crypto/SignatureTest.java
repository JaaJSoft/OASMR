package dev.jaaj.oasmr.lib.crypto;

import java.security.SignedObject;

public class SignatureTest {

    public static void main(String[] args) throws Exception {
        SupervisorSecurity supervisorSecurity = new SupervisorSecurity();
        NodeSecurity nodeSecurity = new NodeSecurity();
        SignedObject signedObject = supervisorSecurity.keyExchange();
        System.out.println(nodeSecurity.verifySignature(signedObject));
    }
}
