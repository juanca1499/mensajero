package servidor;

import cliente.mensajes.*;
import conexion.ConexionCliente;
import cliente.interfaces.EnviadorMensaje;
import cliente.interfaces.ImpresoraChat;
import cliente.interfaces.ReceptorMensaje;
import conexion.ConexionServidor;
import gui.MensajeroServidorGUI;
import servidor.tcp.ServidorEnviaTCP;
import servidor.tcp.ServidorEscuchaTCP;
import servidor.udp.ServidorEnviaUDP;
import servidor.udp.ServidorEscuchaUDP;

import java.io.FileInputStream;
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
                enviarMensajeUDP(conexionCliente, mensaje);
            }
        } else {
            // Se envía a un cliente específico.
            ConexionCliente conexionCliente = buscarCliente(mensaje.getDestino());
            enviarMensajeUDP(conexionCliente,mensaje);
        }
    }

    @Override
    public void enviarArchivo(MensajeArchivo archivo) {
        // Se hace un broadcast a todos los clientes conectados.
        if(archivo.getDestino() == null) {
            archivo.setOrigen("SERVIDOR");
            for(ConexionCliente conexionCliente : listaClientes) {
                try {
                    FileInputStream fileInput = new FileInputStream(archivo.getArchivo());
                    byte[] bytesArchivo = new byte[fileInput.available()];
                    fileInput.read(bytesArchivo);
                    archivo.setBytes(bytesArchivo);
                    enviarMensajeTCP(conexionCliente, archivo);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } else {
            // Se envía a un cliente específico.
            ConexionCliente conexionCliente = buscarCliente(archivo.getDestino());
            enviarMensajeTCP(conexionCliente,archivo);
        }
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

    @Override
    public void enviarVideo(MensajeVideo frame) {
        ConexionCliente conexionCliente = buscarCliente(frame.getDestino());
        enviarMensajeTCP(conexionCliente,frame);
    }

    @Override
    public void recibirVideo(MensajeVideo frame) {
        enviarVideo(frame);
    }

    @Override
    public void enviarAudio(MensajeAudio sample) {
        ConexionCliente conexionCliente = buscarCliente(sample.getDestino());
        enviarMensajeUDP(conexionCliente,sample);
    }

    @Override
    public void recibirAudio(MensajeAudio sample) {
        enviarAudio(sample);
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

    private void enviarMensajeUDP(ConexionCliente conexionCliente, Mensaje mensaje) {
        try {
            servidorEnviaUDP = new ServidorEnviaUDP(conexionCliente);
            servidorEnviaUDP.enviar(mensaje);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void enviarMensajeTCP(ConexionCliente conexionCliente, Mensaje mensaje) {
        try {
            servidorEnviaTCP = new ServidorEnviaTCP(conexionCliente);
            servidorEnviaTCP.enviar(mensaje);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}