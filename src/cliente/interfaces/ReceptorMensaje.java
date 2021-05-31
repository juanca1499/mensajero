package cliente.interfaces;

import cliente.mensajes.*;

public interface ReceptorMensaje {
    void recibirMensaje(MensajeTexto mensaje);
    void recibirArchivo(MensajeArchivo archivo);
    void recibirVideo(MensajeVideo frame);
    void recibirAudio(MensajeAudio sample);
    void recibirLatencia(MensajeLatencia latencia);
}
