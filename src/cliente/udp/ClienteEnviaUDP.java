package cliente.udp;

import cliente.mensajes.Mensaje;
import cliente.mensajes.MensajeTexto;
import conexion.ConexionCliente;
import conexion.ConexionServidor;

import java.net.*;
import java.io.*;
import java.util.zip.GZIPOutputStream;

//declaramos la clase udp envia
public class ClienteEnviaUDP {
    //Definimos el sockets, número de bytes del buffer, y mensaje.
    protected final int MAX_BUFFER=256;
    protected final int PUERTO_SERVIDOR;
    protected final String IP_SERVIDOR;
    protected DatagramSocket socket;
    protected InetAddress address;
    protected DatagramPacket paquete;
    
    public ClienteEnviaUDP(DatagramSocket socket, ConexionServidor conexionServidor){
        this.socket = socket;
        IP_SERVIDOR= conexionServidor.getIp();
        PUERTO_SERVIDOR= conexionServidor.getPuertoUDP();
    }

    public void enviar(Mensaje mensaje) {
        byte[] mensaje_bytes;
        try {
            System.out.println("\n\nENVIANDO MENSAJE DESDE CLIENTE " + mensaje.getOrigen() + " CON DESTINO AL USUARIO " + mensaje.getDestino());
            address=InetAddress.getByName(IP_SERVIDOR);
            ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
            ObjectOutputStream objectStream = new ObjectOutputStream(byteArray);
            objectStream.writeObject(mensaje);
            mensaje_bytes = byteArray.toByteArray();
            System.out.println("TAMANO DEL MENSAJE A ENVIAR: " + mensaje_bytes.length);
            paquete = new DatagramPacket(mensaje_bytes,mensaje_bytes.length,address,PUERTO_SERVIDOR);
            socket.send(paquete);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            System.exit(1);
        }
    }
}
