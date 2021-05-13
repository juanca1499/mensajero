package cliente.interfaces;

import cliente.mensajes.MensajeArchivo;
import cliente.mensajes.MensajeTexto;

import java.io.File;

public interface ReceptorMensaje {
    void recibirMensaje(MensajeTexto mensaje);
    void recibirArchivo(MensajeArchivo archivo);
}
