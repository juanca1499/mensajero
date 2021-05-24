package conexion;

public abstract class ConexionBase {

    protected String ip;
    protected int puertoUDP;
    protected int puertoTCP;

    public ConexionBase(String ip) {
        this.ip = ip;
    }

    public ConexionBase(String ip, int puertoUDP, int puertoTCP) {
        this.ip = ip;
        this.puertoUDP = puertoUDP;
        this.puertoTCP = puertoTCP;
    }

    public String getIp() {
        return ip;
    }

    public int getPuertoUDP() {
        return puertoUDP;
    }

    public int getPuertoTCP() {
        return puertoTCP;
    }
}
