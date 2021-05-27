package programas;

import cliente.Cliente;
import conexion.ConexionCliente;
import conexion.ConexionServidor;
import conexion.ServidorDNS;

public class MensajeroClienteProgram {
    public static void main(String[] args) throws Exception {
        // Se simula un servidor DNS que proporciona la IP del servidor en base
        // a su nombre de dominio.
        ServidorDNS servidorDNS = new ServidorDNS("3.150.70.1");
        servidorDNS.agregarHost("messages.fenix.com","192.168.0.15");
        // Se crea la información de conexión al servidor usando al DNS para buscar la ip del servidor.
        ConexionServidor conexionServidor = new ConexionServidor(
                servidorDNS.buscarHost("messages.fenix.com"),
                40000,50000
        );

        // Se le indican al cliente detalles sobre como conectarse al servidor.
        Cliente cliente = new Cliente(new ConexionCliente("192.168.0.15","Juca",40001,50001));
        cliente.setServidor(conexionServidor);

//        Cliente cliente2 = new Cliente(new ConexionCliente("192.168.0.15","Juanito",40002,50002));
//        cliente2.setServidor(conexionServidor);
    }
}
