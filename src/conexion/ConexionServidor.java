package conexion;

import java.net.DatagramSocket;

public class ConexionServidor extends ConexionBase {

    public ConexionServidor(String ip) throws Exception {
        super(ip);
    }

    public ConexionServidor(String ip, int puertoUDP, int puertoTCP) {
        super(ip, puertoUDP, puertoTCP);
    }
}
