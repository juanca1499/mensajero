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

    public Servidor() throws Exception {
        this.listaClientes = new ArrayList<>();
        conexionServidor = new ConexionServidor("192.168.0.15");
        servidorEscuchaUDP = new ServidorEscuchaUDP(conexionServidor,this);
        servidorEscuchaTCP = new ServidorEscuchaTCP(conexionServidor.getPuertoTCP(),this);
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
        //ConexionServidor conexionServidor = new ConexionServidor(conexion.getIp());
        //int[] puertos = getPuertosDisponibles();
        //conexionServidor.setPuertoUDP(puertos[0]);
        //conexionServidor.setPuertoTCP(puertos[1]);
        //listaClientes.add(cliente);
        //return conexionServidor;
    }

    public ConexionCliente buscarCliente(String usuario) {
        for(ConexionCliente cliente : listaClientes) {
            if(cliente.getUsuario().equals(usuario)) {
                return cliente;
            }
        }
        return null;
    }

    private int[] getPuertosDisponibles() {
        int[] puertos = new int[2];
        if(!listaClientes.isEmpty()) {
            ConexionCliente ultimaConexion = listaClientes.get(listaClientes.size() - 1);
            int ultimoPuertoUDP = ultimaConexion.getPuertoUDP();
            int ultimoPuertoTCP = ultimaConexion.getPuertoTCP();
            puertos[0] = ultimoPuertoUDP + 1;
            puertos[1] = ultimoPuertoTCP + 1;
        } else {
            puertos[0] = conexionServidor.getPuertoUDP();
            puertos[1] = conexionServidor.getPuertoTCP();
        }
        return puertos;
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