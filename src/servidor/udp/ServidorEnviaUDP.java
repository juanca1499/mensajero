package servidor.udp;

import conexion.ConexionCliente;
import cliente.mensajes.MensajeTexto;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ServidorEnviaUDP {
    //Definimos el sockets, número de bytes del buffer, y mensaje.
    protected final int MAX_BUFFER=256;
    protected final int PUERTO_SERVER;
    protected DatagramSocket socket;
    protected InetAddress address;
    protected DatagramPacket paquete;
    protected final String SERVER;

    public ServidorEnviaUDP(ConexionCliente conexion) throws Exception {
        SERVER= conexion.getIp();
        PUERTO_SERVER= conexion.getPuertoUDP();
        socket = new DatagramSocket(50001);
    }

    public void enviar(MensajeTexto mensaje) {
        byte[] mensaje_bytes;
        try {
            address=InetAddress.getByName(SERVER);
            ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
            ObjectOutputStream objectStream = new ObjectOutputStream(byteArray);
            objectStream.writeObject(mensaje);
            mensaje_bytes = byteArray.toByteArray();
            paquete = new DatagramPacket(mensaje_bytes,mensaje_bytes.length,address,PUERTO_SERVER);
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
