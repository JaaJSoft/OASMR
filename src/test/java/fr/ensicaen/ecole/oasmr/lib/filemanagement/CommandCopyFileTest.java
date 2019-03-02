package fr.ensicaen.ecole.oasmr.lib.filemanagement;

import org.junit.Test;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;

import static junit.framework.TestCase.assertTrue;

public class CommandCopyFileTest  extends AbstractFileTest{

    @Test
    public void execute() throws Exception {
        CommandCopyFile commandCopyFile = new CommandCopyFile(this.fileName1, this.unusedFileName1);

        commandCopyFile.executeCommand();

        assertTrue(file1.exists());
        assertTrue(unusedFile1.exists());

        Files.isSameFile(file1.toPath(), unusedFile1.toPath());

    }

}
