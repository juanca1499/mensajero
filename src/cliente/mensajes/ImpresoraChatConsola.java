package cliente.mensajes;

import cliente.interfaces.ImpresoraChat;
import utilidades.Progreso;

public class ImpresoraChatConsola implements ImpresoraChat {
    @Override
    public void imprimirMensaje(Mensaje mensaje) {
        System.out.println(mensaje);
    }

    @Override
    public void imprimirProgreso(Progreso progreso) {
        //
    }
}
