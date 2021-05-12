package cliente.udp;

import cliente.mensajes.Mensaje;

import java.net.*;
import java.io.*;
 
//declaramos la clase udp envia
public class ClienteEnviaUDP {
    protected BufferedReader in;
    //Definimos el sockets, número de bytes del buffer, y mensaje.
    protected final int MAX_BUFFER=256;
    protected final int PUERTO_SERVER;
    protected DatagramSocket socket;
    protected InetAddress address;
    protected DatagramPacket paquete;
    protected final String SERVER;
    
    public ClienteEnviaUDP(DatagramSocket nuevoSocket, String servidor, int puertoServidor){
        socket = nuevoSocket;
        SERVER=servidor;
        PUERTO_SERVER=puertoServidor;
    }
    
    public void enviar(String mensaje) {
        byte[] mensaje_bytes;
        try {
            address=InetAddress.getByName(SERVER);
            mensaje_bytes = mensaje.getBytes();
            paquete = new DatagramPacket(mensaje_bytes,mensaje.length(),address,PUERTO_SERVER);
            socket.send(paquete);

//            String mensajeMandado=new String(paquete.getData(),0,paquete.getLength()).trim();
//            System.out.println("Mensaje \""+ mensajeMandado +
//            "\" enviado a "+paquete.getAddress() + "#"+paquete.getPort());
        }
        catch (Exception e) {
            System.err.println("Exception "+ e.getMessage());
            System.exit(1);
        }
    }
}
