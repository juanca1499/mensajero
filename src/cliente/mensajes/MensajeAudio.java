package cliente.mensajes;

import java.io.Serializable;

public class MensajeAudio extends Mensaje implements Serializable {

    private byte[] bytes;

    public MensajeAudio(String origen, String destino) {
        super(origen, destino);
    }

    public MensajeAudio(byte[] bytes) {
        this.bytes = bytes;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }
}
