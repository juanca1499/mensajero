package programas;

import cliente.Cliente;
import conexion.ConexionCliente;
import conexion.ConexionServidor;

public class MensajeroClienteProgram {
    public static void main(String[] args) throws Exception {
        Cliente cliente = new Cliente(new ConexionServidor("192.168.0.15",50000,60000),"Juca");
        cliente.setConexionCliente(new ConexionCliente("192.168.0.15",50001,60001,"Juca"));
    }
}
