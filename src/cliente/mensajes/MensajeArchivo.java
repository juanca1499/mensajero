package cliente.mensajes;

import java.io.File;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MensajeArchivo extends Mensaje implements Serializable {

    private static final long serialVersionUID = 4L;
    private File archivo;
    private byte[] bytes;

    public MensajeArchivo(File archivo) {
        this.archivo = archivo;
    }

    public MensajeArchivo(String origen, String destino, File archivo) throws Exception {
        super(origen, destino);
        this.archivo = archivo;
        bytes = Files.readAllBytes(Paths.get(archivo.getAbsolutePath()));
    }

    public File getArchivo() {
        return archivo;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    @Override
    public String toString() {
        return archivo.getName();
    }
}
