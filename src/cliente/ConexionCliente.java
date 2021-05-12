package cliente;

import conexion.ConexionBase;

import java.net.DatagramSocket;

public class ConexionCliente extends ConexionBase {

    private DatagramSocket socket;

    public ConexionCliente(String ip, int puertoUDP, int puertoTCP) throws Exception {
        super(ip, puertoUDP, puertoTCP);
        socket = new DatagramSocket();
    }

    public DatagramSocket getSocket() {
        return socket;
    }
}