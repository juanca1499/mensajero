package cliente.udp;

import cliente.mensajes.MensajeTexto;
import conexion.ConexionCliente;
import conexion.ConexionServidor;

import java.net.*;
import java.io.*;
 
//declaramos la clase udp envia
public class ClienteEnviaUDP {
    //Definimos el sockets, número de bytes del buffer, y mensaje.
    protected final int MAX_BUFFER=256;
    protected final int PUERTO_SERVIDOR;
    protected final String IP_SERVIDOR;
    protected DatagramSocket socket;
    protected InetAddress address;
    protected DatagramPacket paquete;
    
    public ClienteEnviaUDP(ConexionCliente conexionCliente, ConexionServidor conexionServidor){
        socket = conexionCliente.getSocketUDP();
        IP_SERVIDOR= conexionServidor.getIp();
        PUERTO_SERVIDOR= conexionServidor.getPuertoUDP();
    }

    public void enviar(MensajeTexto mensaje) {
        byte[] mensaje_bytes;
        try {
            address=InetAddress.getByName(IP_SERVIDOR);
            ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
            ObjectOutputStream objectStream = new ObjectOutputStream(byteArray);
            objectStream.writeObject(mensaje);
            mensaje_bytes = byteArray.toByteArray();
            paquete = new DatagramPacket(mensaje_bytes,mensaje_bytes.length,address,PUERTO_SERVIDOR);
            socket.send(paquete);
        }
        catch (Exception e) {
            System.err.println("Exception "+ e.getMessage());
            System.exit(1);
        }
    }
}
