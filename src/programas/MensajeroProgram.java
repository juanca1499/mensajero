package programas;

import cliente.Cliente;
import conexion.ConexionCliente;
import servidor.Servidor;

public class MensajeroProgram {
    public static void main(String[] args) throws Exception {
        // Se simula un servidor DNS que proporciona la IP del servidor en base
        // a su nombre de dominio.
//        ServidorDNS servidorDNS = new ServidorDNS("3.150.70.1");
//        servidorDNS.agregarHost("messages.fenix.com","192.168.0.15");
//        ConexionServidor conexionServidor = new ConexionServidor(
//                servidorDNS.buscarHost("messages.fenix.com"),
//                50000,60000
//        );

        // Se crea el servidor y los clientes.
        Servidor servidor = new Servidor();
        // Se le indican al cliente detalles sobre como conectarse al servidor.
        Cliente cliente = new Cliente(servidor.getConexion(),"Juca");
        cliente.setConexionCliente(new ConexionCliente("192.168.0.15",50001,60001,"Juca"));
        Cliente cliente2 = new Cliente(servidor.getConexion(),"Juanito");
        cliente2.setConexionCliente(new ConexionCliente("192.168.0.15",55000,65000,"Juanito"));

        servidor.agregarCliente(cliente.getConexionCliente());
        servidor.agregarCliente(cliente2.getConexionCliente());

        System.out.println("CLIENTE JUCA:  " + cliente.getConexionCliente().getSocket().getInetAddress() + ":"
                + cliente.getConexionCliente().getSocket().getLocalPort());
        System.out.println("CLIENTE JUANITO:  " + cliente2.getConexionCliente().getSocket().getInetAddress() + ":"
                + cliente2.getConexionCliente().getSocket().getLocalPort());
    }
}
