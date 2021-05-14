package conexion;

import java.net.DatagramSocket;

public class ConexionCliente extends ConexionBase {

    private String usuario;
    private DatagramSocket socketUDP;

    public ConexionCliente(String ip, String usuario) throws Exception {
        super(ip);
        this.usuario = usuario;
    }

    public String getUsuario() {
        return usuario;
    }
}