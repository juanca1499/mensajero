package cliente.mensajes;

import java.io.Serializable;

public class MensajeTexto extends Mensaje implements Serializable {

    private String texto;

    public MensajeTexto(String texto) {
        super();
        this.texto = texto;
    }

    public MensajeTexto(String origen, String destino, String texto) {
        super(origen, destino);
        this.texto = texto;
    }



    @Override
    public String toString() {
        return this.texto;
    }
}
