package cliente.mensajes;

public class MensajeTexto extends Mensaje {

    protected String texto;

    public MensajeTexto(String origen, String destino, String texto) {
        super(origen, destino);
        this.texto = texto;
    }

    @Override
    public String toString() {
        return this.texto;
    }
}
