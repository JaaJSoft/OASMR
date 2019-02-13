package fr.ensicaen.ecole.oasmr.lib.system;

import fr.ensicaen.ecole.oasmr.lib.command.Command;
import oshi.hardware.NetworkIF;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class CommandGetNetworkInterfaces extends Command {

    @Override
    public Serializable execute(Object... params) throws Exception {
        NetworkIF[] networkInterfaces = SystemInfoSingleton.getInstance().getHardware().getNetworkIFs();
        List<HashMap<String, String>> allNetIF = new ArrayList<>();
        for (NetworkIF net : networkInterfaces) {
            HashMap<String, String> netInfo = new HashMap<>();
            netInfo.put("NAME", net.getName());
            netInfo.put("MAC", net.getMacaddr());
            netInfo.put("IPv4", Arrays.toString(net.getIPv4addr()));
            netInfo.put("IPv6", Arrays.toString(net.getIPv6addr()));
            allNetIF.add(netInfo);
        }
        return allNetIF.toArray();
    }

    @Override
    public String toString() {
        return "Command get Network Inferfaces";
    }
}
