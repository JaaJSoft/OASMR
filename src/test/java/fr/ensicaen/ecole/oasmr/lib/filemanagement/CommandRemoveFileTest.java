package fr.ensicaen.ecole.oasmr.lib.filemanagement;

import org.junit.Test;

import static org.junit.Assert.assertFalse;

public class CommandRemoveFileTest extends AbstractFileTest {

    @Test
    public void execute() throws Exception {
        CommandRemoveFile command = new CommandRemoveFile(this.fileName1);
        command.executeCommand();
        assertFalse(file1.exists());
    }
}
