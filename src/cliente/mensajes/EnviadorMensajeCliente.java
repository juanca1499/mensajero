package cliente.mensajes;

import cliente.interfaces.EnviadorMensaje;
import cliente.tcp.ClienteEnviaTCP;
import cliente.udp.ClienteEnviaUDP;

public class EnviadorMensajeCliente implements EnviadorMensaje {

    private ClienteEnviaUDP clienteEnviaUDP;
    private ClienteEnviaTCP clienteEnviaTCP;

    public EnviadorMensajeCliente(ClienteEnviaUDP clienteEnviaUDP, ClienteEnviaTCP clienteEnviaTCP) {
        this.clienteEnviaUDP = clienteEnviaUDP;
        this.clienteEnviaTCP = clienteEnviaTCP;
    }

    @Override
    public void enviarMensaje(MensajeTexto mensaje) {
        clienteEnviaUDP.enviar(mensaje.toString());
    }

    @Override
    public void enviarArchivo(MensajeArchivo archivo) {
        //clienteEnviaTCP.run();
    }
}
