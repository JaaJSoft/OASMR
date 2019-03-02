package fr.ensicaen.ecole.oasmr.lib.filemanagement;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CommandMoveFileTest extends AbstractFileTest {

    @Test
    public void execute() throws Exception {
        CommandMoveFile command = new CommandMoveFile(this.fileName1, this.unusedFileName1);

        command.executeCommand();

        assertFalse(file1.exists());
        assertTrue(unusedFile1.exists());

    }
}
