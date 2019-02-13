package fr.ensicaen.ecole.oasmr.lib.system;

import org.junit.Before;
import org.junit.Test;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.OSProcess;
import oshi.software.os.OperatingSystem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class CommandGetProcessesTest {

    private CommandGetProcesses c;
    private OperatingSystem os;
    private GlobalMemory mem;

    @Before
    public void setUp() {
        c = new CommandGetProcesses();
        os = SystemInfoSingleton.getInstance().getOperatingSystem();
        mem = SystemInfoSingleton.getInstance().getHardware().getMemory();
    }



    @Test
    public void execute() throws Exception {

    }

    @Test
    public void executeFailure() throws Exception {

    }

}
