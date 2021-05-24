package servidor;

import conexion.ConexionCliente;
import cliente.interfaces.EnviadorMensaje;
import cliente.interfaces.ImpresoraChat;
import cliente.interfaces.ReceptorMensaje;
import cliente.mensajes.MensajeArchivo;
import cliente.mensajes.MensajeTexto;
import conexion.ConexionServidor;
import gui.MensajeroServidorGUI;
import servidor.tcp.ServidorEscuchaTCP;
import servidor.udp.ServidorEnviaUDP;
import servidor.udp.ServidorEscuchaUDP;

import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class Servidor implements EnviadorMensaje, ReceptorMensaje {

    private ImpresoraChat impresora;
    private List<ConexionCliente> listaClientes;
    private ConexionServidor conexionServidor;
    private ServidorEscuchaUDP servidorEscuchaUDP;
    private ServidorEscuchaTCP servidorEscuchaTCP;
    private ServidorEnviaUDP servidorEnviaUDP;
    private MensajeroServidorGUI servidorGUI;

    private DatagramSocket socketUDP;

    public Servidor(ConexionServidor conexionServidor) throws Exception {
        this.listaClientes = new ArrayList<>();
        this.conexionServidor = conexionServidor;
        this.socketUDP = new DatagramSocket(conexionServidor.getPuertoUDP());
        servidorEscuchaUDP = new ServidorEscuchaUDP(socketUDP,this);
        servidorEscuchaTCP = new ServidorEscuchaTCP(conexionServidor,this);
        inicializarEscuchadores();
        servidorGUI = new MensajeroServidorGUI(this);
        servidorGUI.setVisible(true);
        impresora = servidorGUI;
    }

    private synchronized void inicializarEscuchadores() {
        servidorEscuchaUDP.start();
        //servidorEscuchaTCP.start();
    }
    @Override
    public void enviarMensaje(MensajeTexto mensaje) {
        // Se hace un broadcast a todos los clientes conectados.
        if(mensaje.getDestino() == null) {
            mensaje.setOrigen("SERVIDOR");
            for(ConexionCliente conexionCliente : listaClientes) {
                enviarMensajeTexto(conexionCliente, mensaje);
            }
        } else {
            // Se envía a un cliente específico.
            ConexionCliente conexionCliente = buscarCliente(mensaje.getDestino());
            enviarMensajeTexto(conexionCliente,mensaje);
        }
    }

    @Override
    public void enviarArchivo(MensajeArchivo archivo) {

    }

    @Override
    public void recibirMensaje(MensajeTexto mensaje) {
        impresora.imprimirMensaje(mensaje);
        enviarMensaje(mensaje);
    }

    @Override
    public void recibirArchivo(MensajeArchivo archivo) {
        impresora.imprimirMensaje(archivo);
        enviarArchivo(archivo);
    }

    public void agregarCliente(ConexionCliente cliente) {
        listaClientes.add(cliente);
    }

    public ConexionCliente buscarCliente(String usuario) {
        for(ConexionCliente cliente : listaClientes) {
            if(cliente.getUsuario().equals(usuario)) {
                return cliente;
            }
        }
        return null;
    }

    public ConexionServidor getConexion() {
        return conexionServidor;
    }

    private void enviarMensajeTexto(ConexionCliente conexionCliente, MensajeTexto mensaje) {
        try {
            servidorEnviaUDP = new ServidorEnviaUDP(conexionCliente);
            servidorEnviaUDP.enviar(mensaje);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}