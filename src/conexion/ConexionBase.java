package conexion;

import java.net.DatagramSocket;

public abstract class ConexionBase {

    protected String ip;
    protected int puertoUDP;
    protected int puertoTCP;
    protected DatagramSocket socketUDP;

    public ConexionBase(String ip) throws Exception {
        this.ip = ip;
        socketUDP = new DatagramSocket();
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
        return socketUDP.getLocalPort();
    }

    public int getPuertoTCP() {
        return puertoTCP;
    }

    public DatagramSocket getSocketUDP() {
        return socketUDP;
    }
}
