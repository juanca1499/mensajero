package conexion;

public class ConexionCliente extends ConexionBase {

    private String usuario;

    public ConexionCliente(String ip, String usuario) throws Exception {
        super(ip);
        this.usuario = usuario;
    }

    public ConexionCliente(String ip, String usuario, int puertoUDP, int puertoTCP) throws Exception {
        super(ip,puertoUDP,puertoTCP);
        this.usuario = usuario;
    }

    public String getUsuario() {
        return usuario;
    }
}