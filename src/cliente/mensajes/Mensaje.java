package cliente.mensajes;

import java.util.Date;

public abstract class Mensaje {

    protected Date fecha;
    protected String origen;
    protected String destino;

    public Mensaje(String origen, String destino) {
        this.origen = origen;
        this.destino = destino;
        this.fecha = new Date();
    }

    public String getOrigen() {
        return origen;
    }

    public String getDestino() {
        return destino;
    }

    public Date getFecha() {
        return fecha;
    }
}
