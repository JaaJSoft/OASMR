package fr.ensicaen.ecole.oasmr.lib.crypto;

import java.security.SignedObject;

public class NodeSecurityTest {
    public static void main(String[] args) throws Exception {
        SupervisorSecurity supervisorSecurity = new SupervisorSecurity();
        NodeSecurity nodeSecurity = new NodeSecurity();
        SignedObject signedObject = supervisorSecurity.keyExchange();
        nodeSecurity.keyExchange(signedObject);
    }
}
