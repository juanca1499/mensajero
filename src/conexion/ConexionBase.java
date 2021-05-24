package conexion;

import java.net.DatagramSocket;
import java.net.ServerSocket;

public abstract class ConexionBase {

    protected String ip;
    protected int puertoUDP;
    protected int puertoTCP;
    protected DatagramSocket socketUDP;
    protected ServerSocket socketTCP;

    public ConexionBase(String ip) throws Exception {
        this.ip = ip;
        socketUDP = new DatagramSocket();
        socketTCP = new ServerSocket();
    }

    public ConexionBase(String ip, int puertoUDP, int puertoTCP) throws Exception {
        this.ip = ip;
        this.puertoUDP = puertoUDP;
        this.puertoTCP = puertoTCP;
        socketUDP = new DatagramSocket(puertoUDP);
        socketTCP = new ServerSocket(puertoTCP);
    }

    public String getIp() {
        return ip;
    }

    public int getPuertoUDP() {
        return socketUDP.getLocalPort();
    }

    public int getPuertoTCP() {
        return socketTCP.getLocalPort();
    }

    public DatagramSocket getSocketUDP() {
        return socketUDP;
    }
}
