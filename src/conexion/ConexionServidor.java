package conexion;

public class ConexionServidor extends ConexionBase {

    public ConexionServidor(String ip) {
        super(ip);
    }
    public ConexionServidor(String ip, int puertoUDP, int puertoTCP) {
        super(ip, puertoUDP, puertoTCP);
    }
}
