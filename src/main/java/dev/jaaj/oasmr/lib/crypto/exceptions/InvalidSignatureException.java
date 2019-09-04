package dev.jaaj.oasmr.lib.crypto.exceptions;

public class InvalidSignatureException extends  Exception {
    public InvalidSignatureException(){
        super("The signature is invalid.");
    }
}
