package cliente.udp;

import cliente.interfaces.ReceptorMensaje;
import cliente.mensajes.MensajeTexto;
import conexion.ConexionCliente;

import java.net.*;
import java.io.*;
 
//declaramos la clase udp escucha
public class ClienteEscuchaUDP extends Thread {
    //Definimos el sockets, número de bytes del buffer, y mensaje.
    protected final int MAX_BUFFER=256;
    protected DatagramSocket socket;
    protected DatagramPacket servPaquete;
    private final ReceptorMensaje receptor;
    
    public ClienteEscuchaUDP(ConexionCliente conexionCliente, ReceptorMensaje receptor){
        socket=conexionCliente.getSocketUDP();
        this.receptor = receptor;
        System.out.println("CLIENTE ESCUCHANDO EN EL PUERTO (UDP): " + conexionCliente.getPuertoUDP());
    }
    public void run() {

        byte[] mensaje_bytes;

        try {
            while (true) {
                // Recibimos el paquete
                mensaje_bytes=new byte[MAX_BUFFER];
                servPaquete = new DatagramPacket(mensaje_bytes,MAX_BUFFER);
                socket.receive(servPaquete);
                ByteArrayInputStream byteStream = new ByteArrayInputStream(mensaje_bytes);
                ObjectInputStream objectStream = new ObjectInputStream(
                        new BufferedInputStream(byteStream));
                Object mensajeTexto = objectStream.readObject();
                // Lo mostramos por pantalla
                receptor.recibirMensaje((MensajeTexto) mensajeTexto);
                objectStream.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            System.err.println("Excepcion C: "+e.getMessage());
            System.exit(1);
        }
    }
}
