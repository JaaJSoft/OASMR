package fr.ensicaen.ecole.oasmr.supervisor;

import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class CommandFinderTest {
    CommandFinder finder;

    @Before
    public void setUp() throws Exception {
        finder = new CommandFinder("commands");
    }

    @Test
    public void scanJarTest() {

        finder.scanJar(new File("commands/packagemanagment.jar"));

    }
}