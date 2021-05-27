package servidor.udp;

import cliente.interfaces.ReceptorMensaje;
import cliente.mensajes.Mensaje;
import cliente.mensajes.MensajeTexto;
import cliente.mensajes.MensajeVideo;

import java.net.*;
import java.io.*;

public class ServidorEscuchaUDP extends Thread {
    protected DatagramSocket socket;
    protected final int MAX_BUFFER=256;
    protected DatagramPacket paquete;
    protected byte[] mensaje_bytes;
    private ReceptorMensaje receptorMensaje;
    
    public ServidorEscuchaUDP(DatagramSocket socket, ReceptorMensaje receptorMensaje) {
        //Creamos el socket
        this.socket = socket;
        this.receptorMensaje = receptorMensaje;
        System.out.println("SERVIDOR ESCUCHANDO EN EL PUERTO (UDP): " + socket.getLocalPort());
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
                Mensaje mensaje = (Mensaje) objectStream.readObject();
                reedireccionarMensaje(mensaje);
                objectStream.close();
            }
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    private void reedireccionarMensaje(Mensaje mensaje) {
        if(mensaje instanceof MensajeTexto) {
            receptorMensaje.recibirMensaje((MensajeTexto) mensaje);
        } else if(mensaje instanceof MensajeVideo) {
            receptorMensaje.recibirVideo((MensajeVideo) mensaje);
        }
    }
}
