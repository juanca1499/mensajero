package cliente.interfaces;

import cliente.mensajes.MensajeArchivo;
import cliente.mensajes.MensajeTexto;

public interface EnviadorMensaje {
    void enviarMensaje(MensajeTexto mensaje);
    void enviarArchivo(MensajeArchivo archivo);
}
