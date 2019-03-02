package fr.ensicaen.ecole.oasmr.lib.filemanagement;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public abstract class AbstractFileTest {

    protected final String directoryName1 = "dir";

    protected final String directoryName2 = "dir/subdir";

    protected final String unusedDirName = "dir/unuseddir";

    protected final String unusedFileName1 = "dir/unusedFile";

    protected final String unusedFileName2 = "dir/subdir/unusedFile";

    protected final String fileName1 = "dir/file";

    protected final String fileName2 = "dir/subdir/file";

    protected final String contentFile1 = "This is the content of file 1.";

    protected final String contentFile2 = "This is the content of file 2.";

    protected final String testString = "This is a test String.";

    protected final String listFilesDir1[] = {"subdir", "file"};

    protected File dir, subdir, file1, file2, unusedFile1, unusedFile2, unusedDir;

    @Before
    public void before() throws IOException {
        FileWriter writer;
        dir = new File(directoryName1);
        subdir = new File(directoryName2);
        file1 = new File(fileName1);
        file2 = new File(fileName2);
        unusedFile1 = new File(unusedFileName1);
        unusedFile2 = new File(unusedFileName2);
        unusedDir = new File(unusedDirName);

        dir.mkdirs();
        subdir.mkdir();
        file1.createNewFile();
        file2.createNewFile();

        writer = new FileWriter(fileName1);
        writer.write(contentFile1);
        writer.close();

        writer = new FileWriter(fileName2);
        writer.write(contentFile2);
        writer.close();

    }

    @After
    public void after() {
        unusedFile1.delete();
        unusedFile2.delete();
        unusedDir.delete();
        file1.delete();
        file2.delete();
        subdir.delete();
        dir.delete();
    }

}
