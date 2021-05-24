package programas;

import cliente.Cliente;
import conexion.ConexionCliente;
import conexion.ConexionServidor;
import servidor.Servidor;

public class MensajeroClienteProgram {
    public static void main(String[] args) throws Exception {
        // Se simula un servidor DNS que proporciona la IP del servidor en base
        // a su nombre de dominio.
//        ServidorDNS servidorDNS = new ServidorDNS("3.150.70.1");
//        servidorDNS.agregarHost("messages.fenix.com","192.168.0.15");
//        ConexionServidor conexionServidor = new ConexionServidor(
//                servidorDNS.buscarHost("messages.fenix.com"),
//                50000,60000
//        );

        // Se le indican al cliente detalles sobre como conectarse al servidor.
        Cliente cliente = new Cliente(new ConexionServidor("192.168.0.15",40000,50000),"Juca");
        cliente.setConexionCliente(new ConexionCliente("192.168.0.15","Juca",40001,50001));
    }
}
