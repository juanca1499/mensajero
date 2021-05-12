package cliente.mensajes;

import cliente.interfaces.ImpresoraChat;
import cliente.interfaces.ReceptorMensaje;
import servidor.tcp.ServidorEscuchaTCP;
import servidor.udp.ServidorEscuchaUDP;

public class ReceptorMensajeServidor implements ReceptorMensaje {

    private ImpresoraChat impresora;
    private ServidorEscuchaUDP servidorEscuchaUDP;
    private ServidorEscuchaTCP servidorEscuchaTCP;

    public ReceptorMensajeServidor(ImpresoraChat impresora) {
        this.impresora = impresora;
        try {
            servidorEscuchaUDP = new ServidorEscuchaUDP(50000,this);
            servidorEscuchaTCP = new ServidorEscuchaTCP(60000,this);
            servidorEscuchaUDP.start();
            servidorEscuchaTCP.start();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void recibirMensaje(String mensaje) {
        Mensaje mensj = new MensajeTexto("Cliente","Servidor",mensaje);
        impresora.imprimirMensaje(mensj);
    }

    @Override
    public void recibirArchivo(byte[] archivo) {
        Mensaje mensj = new MensajeArchivo("Cliente","Servidor",archivo);
        impresora.imprimirMensaje(mensj);
    }
}
