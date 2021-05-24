package programas;

import conexion.ConexionCliente;
import conexion.ConexionServidor;
import servidor.Servidor;

public class MensajeroServidorProgram {
    public static void main(String[] args) throws Exception {
        // Se crea el servidor intermediario para redireccionar los mensajes a su destino y
        // proveer futura escalabilidad
        Servidor servidor = new Servidor(new ConexionServidor("192.168.0.15",40000,50000));
        // Se agrega el cliente Juca a la lista de clientes conocidos.
        servidor.agregarCliente(new ConexionCliente("192.168.0.15","Juca", 40001, 50001));
        // Se agrega el cliente Juanito a la lista de clientes conocidos.
        servidor.agregarCliente(new ConexionCliente("192.168.0.34","Juanito", 40001, 50001));
        // TODO Check why when you first send a message from the server the program fails.
        // TODO Check why I specified two times the username.
    }
}
