package cliente.mensajes;

import java.awt.*;

public class MensajeVideo extends Mensaje {
    private Image frame;

    public MensajeVideo(Image frame) {
        this.frame = frame;
    }

    public MensajeVideo(String origen, String destino, Image frame) {
        super(origen,destino);
        this.frame = frame;
    }

    public Image getFrame() {
        return frame;
    }

    public void setFrame(Image frame) {
        this.frame = frame;
    }

    @Override
    public String toString() {
        return frame.toString();
    }
}