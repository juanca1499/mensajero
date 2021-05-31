package cliente.mensajes;

import java.io.Serializable;

public class MensajeLatencia extends Mensaje implements Serializable {

    private long tiempoInicial;
    private long tiempoFinal;
    private long cantBytes;
    private boolean pong;

    public MensajeLatencia() { }
    public MensajeLatencia(String origen, String destino) {
        super(origen,destino);
        tiempoInicial = System.currentTimeMillis();
        pong = false;
    }

    public long getTiempoInicial() {
        return tiempoInicial;
    }

    public void setTiempoInicial(long tiempoInicial) {
        this.tiempoInicial = tiempoInicial;
    }

    public long getTiempoFinal() {
        return tiempoFinal;
    }

    public void setTiempoFinal(long tiempoFinal) {
        this.tiempoFinal = tiempoFinal;
    }

    public long getCantBytes() {
        return cantBytes;
    }

    public void setCantBytes(long cantBytes) {
        this.cantBytes = cantBytes;
    }

    public long getLatencia() {
        return (tiempoFinal - tiempoInicial) / 1000;
    }

    public boolean isPong() {
        return pong;
    }

    public void setPong(boolean pong) {
        this.pong = pong;
    }
}
