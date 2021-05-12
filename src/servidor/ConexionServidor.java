package servidor;

import conexion.ConexionBase;

public class ConexionServidor extends ConexionBase {

    public ConexionServidor(String ip, int puertoUDP, int puertoTCP) {
        super(ip, puertoUDP, puertoTCP);
    }
}
