/*
 *  Copyright (c) 2019. CCC-Development-Team
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package fr.ensicaen.ecole.oasmr.lib.crypto;

import fr.ensicaen.ecole.oasmr.lib.command.Command;
import fr.ensicaen.ecole.oasmr.lib.system.CommandGetProcessor;
import fr.ensicaen.ecole.oasmr.lib.system.CommandGetSystem;
import javax.crypto.SealedObject;
import javax.crypto.spec.DHParameterSpec;
import java.security.SignedObject;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CommandEncryptionTest {
    private Command originalCommand;
    private SupervisorSecurity supervisorSecurity;
    private NodeSecurity nodeSecurity;

    @Before
    public void setUp() throws Exception {
        originalCommand = new CommandGetSystem();
        supervisorSecurity = new SupervisorSecurity(Diffie_Hellman.generateParameters(512));
        nodeSecurity = new NodeSecurity();
        SignedObject signedObject = supervisorSecurity.keyExchange();
        KeyInit keyInit = nodeSecurity.keyExchange(signedObject);
        supervisorSecurity.setAESKey(keyInit);
    }

    @Test
    public void executeSupervisorToNode() throws Exception {
        SealedObject encryptedCommand = supervisorSecurity.encrypt(originalCommand);
        Command decryptedCommand = nodeSecurity.decrypt(encryptedCommand);
        assertEquals(originalCommand.executeCommand(), decryptedCommand.executeCommand());
    }

    @Test
    public void executeNodeToSupervisor() throws Exception {
        SealedObject encryptedCommand = nodeSecurity.encrypt(originalCommand);
        Command decryptedCommand = supervisorSecurity.decrypt(encryptedCommand);
        assertEquals(originalCommand.executeCommand(), decryptedCommand.executeCommand());
    }
}
