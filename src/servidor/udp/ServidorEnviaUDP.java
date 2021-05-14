package servidor.udp;

import cliente.mensajes.MensajeTexto;
import conexion.ConexionCliente;
import conexion.ConexionServidor;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ServidorEnviaUDP {
    //Definimos el sockets, número de bytes del buffer, y mensaje.
    protected final int MAX_BUFFER=256;
    protected final int PUERTO_DESTINO;
    protected DatagramSocket socket;
    protected InetAddress address;
    protected DatagramPacket paquete;
    protected final String IP_DESTINO;

    public ServidorEnviaUDP(ConexionCliente conexionCliente) throws Exception {
        IP_DESTINO = conexionCliente.getIp();
        PUERTO_DESTINO= conexionCliente.getSocket().getLocalPort();
        socket = new DatagramSocket();
    }

    public void enviar(MensajeTexto mensaje) {
        byte[] mensaje_bytes;
        try {
            System.out.println("ENVIANDO MENSAJE DESDE SERVIDOR:  " + IP_DESTINO + ":" + PUERTO_DESTINO);
            address=InetAddress.getByName(IP_DESTINO);
            ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
            ObjectOutputStream objectStream = new ObjectOutputStream(byteArray);
            objectStream.writeObject(mensaje);
            mensaje_bytes = byteArray.toByteArray();
            paquete = new DatagramPacket(mensaje_bytes,mensaje_bytes.length,address,PUERTO_DESTINO);
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
