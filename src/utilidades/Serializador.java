package utilidades;

import java.io.*;

public class Serializador {
    public static byte[] serializar(Object object) {
        try {
            ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
            ObjectOutputStream objOutput = new ObjectOutputStream(byteArray);
            objOutput.writeObject(object);
            byte[] bytes = byteArray.toByteArray();
            objOutput.flush();
            byteArray.close();
            return bytes;
        } catch (IOException eio) {
            eio.printStackTrace();
            return new byte[0];
        }
    }

    public static Object deserializar(byte[] bytes) {
        try {
            ByteArrayInputStream byteArray = new ByteArrayInputStream(bytes);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArray);
            return objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
