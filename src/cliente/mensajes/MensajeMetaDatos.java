package cliente.mensajes;

import java.io.Serializable;

public class MensajeMetaDatos extends Mensaje implements Serializable {

    private long tamanoBytes;

    public MensajeMetaDatos(String origen, String destino) {
        super(origen, destino);
    }

    public MensajeMetaDatos(String origen, String destino, long tamanoBytes) {
        this(origen,destino);
        this.tamanoBytes = tamanoBytes;
    }

    public long getTamanoBytes() {
        return tamanoBytes;
    }

    public void setTamanoBytes(long tamanoBytes) {
        this.tamanoBytes = tamanoBytes;
    }

    public long getTamanoEnBits() {
        return tamanoBytes * 8;
    }
}
