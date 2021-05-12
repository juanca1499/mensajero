package cliente.mensajes;

public class MensajeArchivo extends Mensaje {

    protected byte[] archivoBytes;

    public MensajeArchivo(String origen, String destino, byte[] archivoBytes) {
        super(origen, destino);
        this.archivoBytes = archivoBytes;
    }
}
