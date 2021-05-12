package conexion;

import java.util.HashMap;
import java.util.Map;

public class ServidorDNS {

    private String direccionIP;
    private Map<String,String> tablaNombres;

    public ServidorDNS(String direccionIP) {
        this.direccionIP = direccionIP;
        tablaNombres = new HashMap<>();
    }

    public void agregarHost(String dominio, String direccion) {
        tablaNombres.put(dominio,direccion);
    }

    public String buscarHost(String dominio) {
        return tablaNombres.get(dominio);
    }

    public String getDireccionIP() {
        return direccionIP;
    }
}
