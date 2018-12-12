package fr.ensicaen.ecole.oasmr.lib.packagemanagment.apt.exceptions;

public class ExceptionAptRemoveInexistantPackage extends Exception {
    public ExceptionAptRemoveInexistantPackage(String message){
        super(message);
    }
}
