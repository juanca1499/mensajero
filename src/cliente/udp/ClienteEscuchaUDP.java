package cliente.udp;

import cliente.interfaces.ReceptorMensaje;
import cliente.mensajes.MensajeTexto;

import java.net.*;
import java.io.*;
 
//declaramos la clase udp escucha
public class ClienteEscuchaUDP extends Thread {
    protected BufferedReader in;
    //Definimos el sockets, número de bytes del buffer, y mensaje.
    protected final int MAX_BUFFER=256;
    protected final int PUERTO_CLIENTE;
    protected DatagramSocket socket;
    protected InetAddress address;
    protected DatagramPacket servPaquete;
    private ReceptorMensaje receptor;
    //protected String SERVER;
    
    public ClienteEscuchaUDP(DatagramSocket socketNuevo, ReceptorMensaje receptor){
        socket=socketNuevo;
        //SERVER=servidor;
        PUERTO_CLIENTE=socket.getLocalPort();
        System.out.println("CLIENTE ESCUCHANDO EN EL PUERTO: " + PUERTO_CLIENTE);
        this.receptor = receptor;
    }
    public void run() {
        String mensaje="";
        byte[] mensaje_bytes=mensaje.getBytes();
        
        String cadenaMensaje="";

        byte[] recogerServidor_bytes;

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
