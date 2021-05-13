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

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPuertoUDP() {
        return puertoUDP;
    }

    public void setPuertoUDP(int puertoUDP) {
        this.puertoUDP = puertoUDP;
    }

    public int getPuertoTCP() {
        return puertoTCP;
    }

    public void setPuertoTCP(int puertoTCP) {
        this.puertoTCP = puertoTCP;
    }
}
