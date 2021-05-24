package servidor;

import cliente.mensajes.Mensaje;
import conexion.ConexionCliente;
import cliente.interfaces.EnviadorMensaje;
import cliente.interfaces.ImpresoraChat;
import cliente.interfaces.ReceptorMensaje;
import cliente.mensajes.MensajeArchivo;
import cliente.mensajes.MensajeTexto;
import conexion.ConexionServidor;
import gui.MensajeroServidorGUI;
import servidor.tcp.ServidorEnviaTCP;
import servidor.tcp.ServidorEscuchaTCP;
import servidor.udp.ServidorEnviaUDP;
import servidor.udp.ServidorEscuchaUDP;

import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.List;

public class Servidor implements EnviadorMensaje, ReceptorMensaje {

    private ImpresoraChat impresora;
    private List<ConexionCliente> listaClientes;
    private ConexionServidor conexionServidor;
    private ServidorEscuchaUDP servidorEscuchaUDP;
    private ServidorEscuchaTCP servidorEscuchaTCP;
    private ServidorEnviaUDP servidorEnviaUDP;
    private ServidorEnviaTCP servidorEnviaTCP;
    private MensajeroServidorGUI servidorGUI;
    private DatagramSocket socketUDP;

    public Servidor(ConexionServidor conexionServidor) throws Exception {
        this.listaClientes = new ArrayList<>();
        this.conexionServidor = conexionServidor;
        this.socketUDP = new DatagramSocket(conexionServidor.getPuertoUDP());
        servidorEscuchaUDP = new ServidorEscuchaUDP(socketUDP,this);
        servidorEscuchaTCP = new ServidorEscuchaTCP(conexionServidor,this);
        inicializarServicios();
        servidorGUI = new MensajeroServidorGUI(this);
        servidorGUI.setVisible(true);
        impresora = servidorGUI;
    }

    private synchronized void inicializarServicios() {
        servidorEscuchaUDP.start();
        servidorEscuchaTCP.start();
    }

    @Override
    public void enviarMensaje(MensajeTexto mensaje) {
        // Se hace un broadcast a todos los clientes conectados.
        if(mensaje.getDestino() == null) {
            mensaje.setOrigen("SERVIDOR");
            for(ConexionCliente conexionCliente : listaClientes) {
                enviarMensaje(conexionCliente, mensaje);
            }
        } else {
            // Se envía a un cliente específico.
            ConexionCliente conexionCliente = buscarCliente(mensaje.getDestino());
            enviarMensaje(conexionCliente,mensaje);
        }
    }

    @Override
    public void enviarArchivo(MensajeArchivo archivo) {

    }

    @Override
    public void recibirMensaje(MensajeTexto mensaje) {
        impresora.imprimirMensaje(mensaje);
        // Se hace un broadcast a todos los clientes conectados.
        if(mensaje.getDestino() == null) {
            mensaje.setOrigen("SERVIDOR");
            for(ConexionCliente conexionCliente : listaClientes) {
                enviarMensaje(conexionCliente, mensaje);
            }
        } else {
            // Se envía a un cliente específico.
            ConexionCliente conexionCliente = buscarCliente(mensaje.getDestino());
            enviarMensaje(conexionCliente,mensaje);
        }
    }

    @Override
    public void recibirArchivo(MensajeArchivo archivo) {
        System.out.println("UN ARCHIVO ANDA POR AQUÍ");
        impresora.imprimirMensaje(archivo);
        // Se hace un broadcast a todos los clientes conectados.
        if(archivo.getDestino() == null) {
            archivo.setOrigen("SERVIDOR");
            for(ConexionCliente conexionCliente : listaClientes) {
                enviarMensaje(conexionCliente, archivo);
            }
        } else {
            // Se envía a un cliente específico.
            ConexionCliente conexionCliente = buscarCliente(archivo.getDestino());
            enviarMensaje(conexionCliente,archivo);
        }
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

    private void enviarMensaje(ConexionCliente conexionCliente, Mensaje mensaje) {
        try {
            if(mensaje instanceof MensajeTexto) {
                // Los mensajes de texto se envían utilizando el protocolo UDP.
                servidorEnviaUDP = new ServidorEnviaUDP(conexionCliente);
                servidorEnviaUDP.enviar((MensajeTexto) mensaje);
            } else if(mensaje instanceof MensajeArchivo) {
                // Los archivos se envían utilizando el protocolo TCP.
                servidorEnviaTCP = new ServidorEnviaTCP(conexionCliente);
                servidorEnviaTCP.enviar((MensajeArchivo)mensaje);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}