package fr.ensicaen.ecole.oasmr.lib.filemanagement;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CommandRenameFileTest extends AbstractFileTest {

    @Test
    public void execute() throws Exception {
        CommandRenameFile command = new CommandRenameFile(this.fileName1, this.unusedFileName1.replace(this.directoryName1, ""));

        assertFalse(unusedFile1.exists());
        command.executeCommand();

        assertTrue(unusedFile1.exists());
        assertFalse(file1.exists());

    }
}
