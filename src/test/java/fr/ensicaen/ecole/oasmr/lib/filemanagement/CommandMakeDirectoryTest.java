package fr.ensicaen.ecole.oasmr.lib.filemanagement;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class CommandMakeDirectoryTest extends AbstractFileTest {

    @Test
    public void execute() throws Exception {
        CommandMakeDiretory commandMakeDiretory = new CommandMakeDiretory(this.unusedDirName);

        commandMakeDiretory.executeCommand();

        assertTrue(unusedDir.isDirectory());
    }
}
