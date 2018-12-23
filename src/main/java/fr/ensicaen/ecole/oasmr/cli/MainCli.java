package fr.ensicaen.ecole.oasmr.cli;

import fr.ensicaen.ecole.oasmr.supervisor.node.Node;
import fr.ensicaen.ecole.oasmr.supervisor.node.request.RequestGetNodes;
import fr.ensicaen.ecole.oasmr.supervisor.request.RequestManager;
import fr.ensicaen.ecole.oasmr.supervisor.request.RequestManagerFlyweightFactory;
import picocli.CommandLine;

import java.util.Set;

class MainCli {

    public static void main(String[] args) throws Exception {
        SupervisorCli s = new SupervisorCli();
        CommandLine cmd = new CommandLine(s);
        cmd.parse(args);
        if (cmd.isUsageHelpRequested()) {
            cmd.usage(System.out);
        } else if (cmd.isVersionHelpRequested()) {
            cmd.printVersionHelp(System.out);
        } else {
            RequestManager r = RequestManagerFlyweightFactory.getInstance().getRequestManager(s.supervisorAddress, s.port);
            Set<Node> nodes = (Set<Node>) r.sendRequest(new RequestGetNodes());
            System.out.println(nodes);
        }

    }

}
