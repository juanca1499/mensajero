package programas;

import cliente.Cliente;
import conexion.ServidorDNS;
import conexion.ConexionServidor;
import servidor.Servidor;

public class MensajeroProgram {
    public static void main(String[] args) throws Exception {
        // Se simula un servidor DNS que proporciona la IP del servidor en base
        // a su nombre de dominio.
        ServidorDNS servidorDNS = new ServidorDNS("3.150.70.1");
        servidorDNS.agregarHost("messages.fenix.com","192.168.0.15");
        ConexionServidor conexionServidor = new ConexionServidor(
                servidorDNS.buscarHost("messages.fenix.com"),
                50000,60000
        );

        // Se crea el servidor y los clientes.
        Servidor servidor = new Servidor();
        // Se le indican al cliente detalles sobre como conectarse al servidor.
        Cliente cliente = new Cliente(conexionServidor, "Juca");
        // Se agrega la información de conexión de cada cliente al servidor.
        cliente.setServidor(servidor.agregarCliente(cliente.getConexion()));

        //MensajeroClienteGUI mensajeroClienteGUIJuanito = new MensajeroClienteGUI("Juanito",conexionServidor);
        //mensajeroClienteGUIJuanito.setVisible(true);

        //mensajeroServidorGUI.agregarCliente(mensajeroClienteGUIJuanito.getConexion());
    }
}
