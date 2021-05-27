package cliente.mensajes;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

public class MensajeVideo extends Mensaje implements Serializable {
    private ImageIcon frame;

    public MensajeVideo(ImageIcon frame) {
        this.frame = frame;
    }

    public MensajeVideo(String origen, String destino, ImageIcon frame) {
        super(origen,destino);
        this.frame = frame;
    }

    public ImageIcon getFrame() {
        return frame;
    }

    public void setFrame(ImageIcon frame) {
        this.frame = frame;
    }

    @Override
    public String toString() {
        return frame.toString();
    }
}