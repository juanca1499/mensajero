package conexion;

import java.net.DatagramSocket;

public class ConexionCliente extends ConexionBase {

    private String usuario;
    private DatagramSocket socket;

    public ConexionCliente(String ip, int puertoUDP, int puertoTCP, String usuario) throws Exception {
        super(ip, puertoUDP, puertoTCP);
        this.usuario = usuario;
        socket = new DatagramSocket();
    }
    public DatagramSocket getSocket() {
        return socket;
    }

    public String getUsuario() {
        return usuario;
    }
}