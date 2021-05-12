package cliente.mensajes;

import cliente.interfaces.EnviadorMensaje;
import cliente.tcp.ClienteTCP;
import cliente.udp.ClienteUDP;

public class EnviadorMensajeCliente implements EnviadorMensaje {

    private ClienteUDP clienteUDP;
    private ClienteTCP clienteTCP;

    public EnviadorMensajeCliente(ClienteUDP clienteUDP, ClienteTCP clienteTCP) {
        this.clienteUDP = clienteUDP;
        this.clienteTCP = clienteTCP;
    }

    @Override
    public void enviarMensaje(MensajeTexto mensaje) {

    }

    @Override
    public void enviarArchivo(MensajeArchivo archivo) {

    }
}
