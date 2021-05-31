package utilidades;

public class Progreso {
    private long latencia;
    private long bitRate;
    private long tiempoTranscurrido;
    private long tiempoRestante;

    public Progreso() {
        latencia = 0;
        bitRate = 0;
        tiempoTranscurrido = 0;
        tiempoRestante = 0;
    }

    public long getLatencia() {
        return latencia;
    }

    public void setLatencia(long latencia) {
        this.latencia = latencia;
    }

    public long getBitRate() {
        return bitRate;
    }

    public void setBitRate(long bitRate) {
        this.bitRate = bitRate;
    }

    public long getTiempoTranscurrido() {
        return tiempoTranscurrido;
    }

    public void setTiempoTranscurrido(long tiempoTranscurrido) {
        this.tiempoTranscurrido = tiempoTranscurrido;
    }

    public long getTiempoRestante() {
        return tiempoRestante;
    }

    public void setTiempoRestante(long tiempoRestante) {
        this.tiempoRestante = tiempoRestante;
    }
}
