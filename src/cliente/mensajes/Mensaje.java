package cliente.mensajes;

import java.io.Serializable;
import java.util.Date;

public abstract class Mensaje implements Serializable {

    protected Date fecha;
    protected String origen;
    protected String destino;

    public Mensaje() {
        this.fecha = new Date();
    }

    public Mensaje(String origen, String destino) {
        this.origen = origen;
        this.destino = destino;
        this.fecha = new Date();
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }
}
