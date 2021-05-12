package cliente.mensajes;

import cliente.interfaces.ImpresoraChat;
import cliente.interfaces.ReceptorMensaje;
import cliente.udp.ClienteEscuchaUDP;

import java.net.DatagramSocket;

public class ReceptorMensajeCliente implements ReceptorMensaje {

    private ImpresoraChat impresora;
    private ClienteEscuchaUDP clienteEscuchaUDP;

    public ReceptorMensajeCliente(DatagramSocket socket, ImpresoraChat impresora) {
        this.impresora = impresora;
        clienteEscuchaUDP = new ClienteEscuchaUDP(socket, this);
    }

    @Override
    public void recibirMensaje(String mensaje) {
        Mensaje mensj = new MensajeTexto("Servidor","Cliente",mensaje);
        impresora.imprimirMensaje(mensj);
    }

    @Override
    public void recibirArchivo(byte[] archivo) {
        Mensaje mensj = new MensajeArchivo("Servidor","Cliente",archivo);
        impresora.imprimirMensaje(mensj);
    }
}
