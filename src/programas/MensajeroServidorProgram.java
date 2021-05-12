package programas;

import gui.MensajeroGUI;
import servidor.gui.MensajeroServidorGUI;

public class MensajeroServidorProgram {
    public static void main(String[] args) {
        MensajeroGUI ventanaMensajero = new MensajeroServidorGUI();
        ventanaMensajero.setVisible(true);
    }
}
