package servidor.udp;

import cliente.interfaces.ReceptorMensaje;
import cliente.mensajes.MensajeTexto;
import conexion.ConexionServidor;

import java.net.*;
import java.io.*;

public class ServidorEscuchaUDP extends Thread {
    protected DatagramSocket socket;
    protected int puertoCliente=0;
    
    protected InetAddress addressCliente;
    protected final int MAX_BUFFER=256;
    protected DatagramPacket paquete;
    protected byte[] mensaje_bytes;
    private ReceptorMensaje receptorMensaje;
    
    public ServidorEscuchaUDP(ConexionServidor conexionServidor, ReceptorMensaje receptorMensaje) throws Exception{
        //Creamos el socket
        socket = conexionServidor.getSocketUDP();
        this.receptorMensaje = receptorMensaje;
        System.out.println("SERVIDOR ESCUCHANDO EN EL PUERTO (UDP): " + conexionServidor.getPuertoUDP());
    }

    public void run() {
        try {
            while (true) {
                // Recibimos el paquete
                mensaje_bytes=new byte[MAX_BUFFER];
                paquete = new DatagramPacket(mensaje_bytes,MAX_BUFFER);
                socket.receive(paquete);
                ByteArrayInputStream byteStream = new ByteArrayInputStream(mensaje_bytes);
                ObjectInputStream objectStream = new ObjectInputStream(
                        new BufferedInputStream(byteStream));
                MensajeTexto mensajeTexto = (MensajeTexto) objectStream.readObject();
                // Lo mostramos por pantalla
                receptorMensaje.recibirMensaje(mensajeTexto);
                puertoCliente = paquete.getPort();
                addressCliente = paquete.getAddress();
                System.out.println("IP A SER ENVIADO: " + addressCliente);
                System.out.println("PUERTO A SER ENVIADO: " + puertoCliente);
                objectStream.close();
            }
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
}
