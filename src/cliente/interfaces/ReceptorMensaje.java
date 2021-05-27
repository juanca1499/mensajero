package cliente.interfaces;

import cliente.mensajes.MensajeArchivo;
import cliente.mensajes.MensajeTexto;
import cliente.mensajes.MensajeVideo;

public interface ReceptorMensaje {
    void recibirMensaje(MensajeTexto mensaje);
    void recibirArchivo(MensajeArchivo archivo);
    void recibirVideo(MensajeVideo frame);
}
