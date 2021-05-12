package cliente.interfaces;

import cliente.mensajes.MensajeArchivo;
import cliente.mensajes.MensajeTexto;

import java.io.File;

public interface ReceptorMensaje {
    void recibirMensaje(String mensaje);
    void recibirArchivo(byte[] archivo);
}
