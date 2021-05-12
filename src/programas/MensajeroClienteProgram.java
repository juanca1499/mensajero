package programas;

import cliente.gui.MensajeroClienteGUI;
import conexion.ServidorDNS;
import servidor.ConexionServidor;

public class MensajeroClienteProgram {
    public static void main(String[] args) throws Exception {
        ServidorDNS servidorDNS = new ServidorDNS("3.150.70.1");
        servidorDNS.agregarHost("messages.fenix.com","192.168.0.15");
        ConexionServidor conexionServidor = new ConexionServidor(
                servidorDNS.buscarHost("messages.fenix.com"),
                50000,60000
        );
        MensajeroClienteGUI ventanaMensajero = new MensajeroClienteGUI(conexionServidor);
        ventanaMensajero.setVisible(true);
    }
}
