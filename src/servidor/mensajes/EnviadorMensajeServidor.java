package servidor.mensajes;

import cliente.interfaces.EnviadorMensaje;
import cliente.mensajes.MensajeArchivo;
import cliente.mensajes.MensajeTexto;
import servidor.tcp.ServidorEnviaTCP;
import servidor.tcp.ServidorTCP;
import servidor.udp.ServidorEnviaUDP;
import servidor.udp.ServidorUDP;


// ConexionServidor()
    // ** Obtener los puertos y la ip de alguna forma **
    // Servidor(puertoUDP, puertoTCP, enviador, receptor)
        // ServidorUDP(puerto,)
            // ServidorEsuchaUDP
            // ServidorEnviaUDP
        // ServidorTCP(puerto)
            // ServidorEscuchaTCP
            // ServidorEnviaTCP

// ConexionCliente
    // Cliente
        // ClienteUDP
            // Enviador
            // Receptor
        // ClienteTCP
            // Enviador
            // Receptor

public class EnviadorMensajeServidor implements EnviadorMensaje {

    private ServidorEnviaUDP servidorEnviaUDP;
    private ServidorEnviaTCP servidorEnviaTCP;

    public EnviadorMensajeServidor(ServidorEnviaUDP servidorEnviaUDP, ServidorEnviaTCP servidorEnviaTCP) {
        this.servidorEnviaUDP = servidorEnviaUDP;
        this.servidorEnviaTCP = servidorEnviaTCP;
    }

    @Override
    public void enviarMensaje(MensajeTexto mensaje) {
        try {
            //servidorEnviaUDP.inicia();
        } catch(Exception ex) {
            System.out.println((ex.getMessage()));
        }
    }

    @Override
    public void enviarArchivo(MensajeArchivo archivo) {
        try {
            //servidorEnviaTCP.inicia();
        } catch(Exception ex) {
            System.out.println((ex.getMessage()));
        }
    }
}
