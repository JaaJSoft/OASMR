package fr.ensicaen.ecole.oasmr.lib.filemanagement;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CommandListFilesTest extends AbstractFileTest {

    @Test
    public void execute() throws Exception {
        CommandListFiles commandListFiles = new CommandListFiles(directoryName1);

        String[] res = (String[])commandListFiles.executeCommand();

        assertEquals(this.listFilesDir1, res);
    }
}
