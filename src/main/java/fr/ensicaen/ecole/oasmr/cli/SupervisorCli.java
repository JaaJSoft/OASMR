package fr.ensicaen.ecole.oasmr.cli;

import picocli.CommandLine;

import java.net.InetAddress;

public class SupervisorCli {

    @CommandLine.Option(names = {"-s", "--supervisor"}, required = true, description = "Supervisor address")
    InetAddress supervisorAddress;

    @CommandLine.Option(names = {"-p", "--port"}, description = "Supervisor port")
    int port = 40404;

    @CommandLine.Option(names = {"-h", "--help"}, usageHelp = true, description = "display a help message")
    boolean help = false;

    @CommandLine.Option(names = {"-u", "--user"}, description = "username")
    String username = "admin";

}
