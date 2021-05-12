package cliente.mensajes;

import cliente.interfaces.ImpresoraChat;

public class ImpresoraChatConsola implements ImpresoraChat {
    @Override
    public void imprimirMensaje(Mensaje mensaje) {
        System.out.println(mensaje);
    }
}
