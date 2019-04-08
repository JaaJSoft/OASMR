/*
 *  Copyright (c) 2019. CCC-Development-Team
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package fr.ensicaen.ecole.oasmr.lib.network;

import java.io.*;
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

    public static byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(out);
        os.writeObject(obj);
        return out.toByteArray();
    }

    public static Object deserialize(byte[] data) throws IOException, ClassNotFoundException {
        ObjectInputStream is = new ObjectInputStream(new ByteArrayInputStream(data));
        return is.readObject();
    }


}
