package fr.ensicaen.ecole.oasmr.lib;

import org.junit.Before;
import org.junit.Test;

public class commandProcessBuilderExampleTest {
    private CommandProcessBuilderExample c;

    @Before
    public void setUp() throws Exception {
        c = new CommandProcessBuilderExample();
    }

    @Test
    public void execute() {
        System.out.println(c.execute());
    }
}