package cliente.mensajes;

import java.io.File;
import java.io.Serializable;

public class MensajeArchivo extends Mensaje implements Serializable {

    private File archivo;

    public MensajeArchivo(File archivo) {
        this.archivo = archivo;
    }

    public MensajeArchivo(String origen, String destino, File archivo) {
        super(origen, destino);
        this.archivo = archivo;
    }

    public File getArchivo() {
        return archivo;
    }
}
