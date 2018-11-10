package fr.ensicaen.ecole.oasmr.lib.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

public class util {
    public static void sendSerializable(Socket socket, Serializable c) throws IOException {
        ObjectOutputStream outToServer = new ObjectOutputStream(socket.getOutputStream());
        outToServer.writeObject(c);
    }

    public static Serializable receiveSerializable(Socket socket) throws IOException, ClassNotFoundException {
        ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
        return (Serializable) input.readObject();
    }
}
