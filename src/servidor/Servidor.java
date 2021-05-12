package servidor;

import cliente.Cliente;
import cliente.ConexionCliente;
import cliente.interfaces.EnviadorMensaje;
import cliente.interfaces.ImpresoraChat;
import cliente.interfaces.ReceptorMensaje;
import cliente.mensajes.Mensaje;
import cliente.mensajes.MensajeArchivo;
import cliente.mensajes.MensajeTexto;
import servidor.tcp.ServidorEscuchaTCP;
import servidor.udp.ServidorEscuchaUDP;

import java.util.ArrayList;
import java.util.List;

public class Servidor implements EnviadorMensaje, ReceptorMensaje {

    private ImpresoraChat impresora;
    private List<ConexionCliente> listaClientes;
    private ConexionServidor conexion;
    private ServidorEscuchaUDP servidorEscuchaUDP;
    private ServidorEscuchaTCP servidorEscuchaTCP;

    public Servidor(ImpresoraChat impresora) throws Exception {
        this.impresora = impresora;
        this.listaClientes = new ArrayList<>();
        conexion = new ConexionServidor("192.168.0.15",50000,60000);
        servidorEscuchaUDP = new ServidorEscuchaUDP(conexion.getPuertoUDP(),this);
        servidorEscuchaTCP = new ServidorEscuchaTCP(conexion.getPuertoTCP(),this);
        servidorEscuchaUDP.start();
        servidorEscuchaTCP.start();
    }

    @Override
    public void enviarMensaje(MensajeTexto mensaje) {

    }

    @Override
    public void enviarArchivo(MensajeArchivo archivo) {

    }

    @Override
    public void recibirMensaje(String mensaje) {
        Mensaje mensj = new MensajeTexto("Cliente","Servidor",mensaje);
        impresora.imprimirMensaje(mensj);
    }

    @Override
    public void recibirArchivo(byte[] archivo) {

    }

    public void agregarCliente(Cliente cliente) {
        listaClientes.add(cliente.getConexion());
    }

    public ConexionServidor getConexion() {
        return conexion;
    }
}