package fr.ensicaen.ecole.oasmr.lib.system;

import org.junit.Before;
import org.junit.Test;
import oshi.hardware.CentralProcessor;
import oshi.hardware.HardwareAbstractionLayer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class CommandGetProcessorInfoTest {

    private CommandGetProcessorInfo c;
    private CentralProcessor processor;

    @Before
    public void setUp() {
        c = new CommandGetProcessorInfo();
        processor = SystemInfoSingleton.getInstance().getHardware().getProcessor();
    }

    @Test
    public void execute() throws Exception {
        int[] cpuInfoFromCommand = (int[]) c.execute();
        int[] cpuInfo = {
                processor.getPhysicalPackageCount(),
                processor.getPhysicalProcessorCount(),
                processor.getLogicalProcessorCount()
        };
        assertEquals(cpuInfo.length, cpuInfoFromCommand.length);
        for(int i = 0; i < cpuInfo.length; i++){
            assertEquals(cpuInfo[i], cpuInfoFromCommand[i]);
        }
    }

    @Test
    public void executeFailure() throws Exception {
        int[] cpuInfoFromCommand = (int[]) c.execute();
        for(int i = 0; i < cpuInfoFromCommand.length; i++){
            assertEquals(0, cpuInfoFromCommand[i]);
        }
    }


}
