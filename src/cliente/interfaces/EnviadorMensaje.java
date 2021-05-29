package cliente.interfaces;

import cliente.mensajes.MensajeArchivo;
import cliente.mensajes.MensajeAudio;
import cliente.mensajes.MensajeTexto;
import cliente.mensajes.MensajeVideo;

public interface EnviadorMensaje {
    void enviarMensaje(MensajeTexto mensaje);
    void enviarArchivo(MensajeArchivo archivo);
    void enviarVideo(MensajeVideo frame);
    void enviarAudio(MensajeAudio sample);
}
