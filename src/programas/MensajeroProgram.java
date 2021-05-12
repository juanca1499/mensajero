package programas;

import cliente.gui.MensajeroClienteGUI;
import conexion.ServidorDNS;
import servidor.ConexionServidor;
import servidor.gui.MensajeroServidorGUI;

public class MensajeroProgram {
    public static void main(String[] args) {
        try {
            ServidorDNS servidorDNS = new ServidorDNS("3.150.70.1");
            servidorDNS.agregarHost("messages.fenix.com","192.168.0.15");
            ConexionServidor conexionServidor = new ConexionServidor(
                    servidorDNS.buscarHost("messages.fenix.com"),
                    50000,60000
            );
            MensajeroServidorGUI mensajeroServidorGUI = new MensajeroServidorGUI();
            mensajeroServidorGUI.setVisible(true);
            MensajeroClienteGUI mensajeroClienteGUI = new MensajeroClienteGUI(conexionServidor);
            mensajeroClienteGUI.setVisible(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
