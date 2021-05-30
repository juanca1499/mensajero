package utilidades;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class Convertidor {
    public static byte[] aBytes(Object object) {
        try {
            ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
            ObjectOutputStream objOutput = new ObjectOutputStream(byteArray);
            objOutput.writeObject(object);
            objOutput.flush();
            byte[] bytes = byteArray.toByteArray();
            byteArray.close();
            return bytes;
        } catch (IOException eio) {
            eio.printStackTrace();
            return new byte[0];
        }
    }
}
