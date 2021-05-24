package conexion;

public class ConexionCliente extends ConexionBase {

    private String usuario;

    public ConexionCliente(String ip, String usuario) {
        super(ip);
        this.usuario = usuario;
    }

    public ConexionCliente(String ip, String usuario, int puertoUDP, int puertoTCP) {
        super(ip,puertoUDP,puertoTCP);
        this.usuario = usuario;
    }

    public String getUsuario() {
        return usuario;
    }
}