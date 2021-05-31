package cliente.interfaces;

import cliente.mensajes.Mensaje;
import utilidades.Progreso;

public interface ImpresoraChat {
    void imprimirMensaje(Mensaje mensaje);
    void imprimirProgreso(Progreso progreso);
}