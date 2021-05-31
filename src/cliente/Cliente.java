package cliente;

import cliente.mensajes.*;
import cliente.tcp.ClienteEscuchaTCP;
import gui.MensajeroClienteGUI;
import cliente.interfaces.EnviadorMensaje;
import cliente.interfaces.ImpresoraChat;
import cliente.interfaces.ReceptorMensaje;
import cliente.tcp.ClienteEnviaTCP;
import cliente.udp.ClienteEnviaUDP;
import cliente.udp.ClienteEscuchaUDP;
import conexion.ConexionCliente;
import conexion.ConexionServidor;
import utilidades.Alerta;
import utilidades.Progreso;
import utilidades.Serializador;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.DatagramSocket;


public class Cliente implements EnviadorMensaje, ReceptorMensaje {

    private String nombreUsuario;
    private ImpresoraChat impresora;
    private ConexionServidor conexionServidor;
    private ConexionCliente conexionCliente;
    private ClienteEnviaUDP clienteEnviaUDP;
    private ClienteEscuchaUDP clienteEscuchaUDP;
    private ClienteEnviaTCP clienteEnviaTCP;
    private ClienteEscuchaTCP clienteEscuchaTCP;
    private MensajeroClienteGUI clienteGUI;
    private DatagramSocket socketUDP;

    private Progreso progresoTransferencia;
    private ContadorTiempo contadorTiempo;

    public Cliente(ConexionCliente conexionCliente) throws Exception {
        this.conexionCliente = conexionCliente;
        this.nombreUsuario = conexionCliente.getUsuario();
        this.socketUDP = new DatagramSocket(conexionCliente.getPuertoUDP());
        clienteGUI = new MensajeroClienteGUI(nombreUsuario,this);
        clienteGUI.setVisible(true);
        impresora = clienteGUI;
    }

    private void inicializarServicios() throws Exception {
        clienteEnviaUDP = new ClienteEnviaUDP(socketUDP,conexionServidor);
        clienteEscuchaUDP = new ClienteEscuchaUDP(socketUDP,this);
        clienteEnviaTCP = new ClienteEnviaTCP(conexionServidor);
        clienteEscuchaTCP = new ClienteEscuchaTCP(conexionCliente,this);
        clienteEscuchaUDP.start();
        clienteEscuchaTCP.start();
    }

    @Override
    public void enviarMensaje(MensajeTexto mensaje) {
        mensaje.setOrigen(nombreUsuario);
        if(mensaje.getOrigen().equals("Juca")) {
            mensaje.setDestino("Juanito");
        } else {
            mensaje.setDestino("Juca");
        }
        clienteEnviaUDP.enviar(mensaje);
    }

    @Override
    public void enviarArchivo(MensajeArchivo archivo) {
        archivo.setOrigen(nombreUsuario);
        // El siguiente código será removido en caso de alcanzar a implementar la funcionalidad
        // de escoger el cliente a enviar desde la GUI.
        if(archivo.getOrigen().equals("Juca")) {
            archivo.setDestino("Juanito");
        } else {
            archivo.setDestino("Juca");
        }
        try {
            progresoTransferencia = new Progreso();
            // Se cálcula la latencia hacia el cliente para estimar el tiempo de envío
            calcularLatencia(archivo.getOrigen(),archivo.getDestino());
            FileInputStream fileInput = new FileInputStream(archivo.getArchivo());
            byte[] bytesArchivo = new byte[fileInput.available()];
            fileInput.read(bytesArchivo);
            archivo.setBytes(bytesArchivo);
            contadorTiempo = new ContadorTiempo();
            contadorTiempo.start();
            clienteEnviaTCP.enviar(archivo);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void recibirMensaje(MensajeTexto mensaje) {
        impresora.imprimirMensaje(mensaje);
    }

    @Override
    public void recibirArchivo(MensajeArchivo archivo) {
        File archivoDestino = Alerta.pedirUbicacion(clienteGUI,
        archivo.getArchivo().getName());
        if(archivoDestino != null) {
            impresora.imprimirMensaje(archivo);
            try {
                FileOutputStream fileOutput = new FileOutputStream(archivoDestino);
                fileOutput.write(archivo.getBytes());
                fileOutput.flush();
                fileOutput.close();
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void enviarVideo(MensajeVideo frame) {
        frame.setOrigen(nombreUsuario);
        if(frame.getOrigen().equals("Juca")) {
            frame.setDestino("Juanito");
        } else {
            frame.setDestino("Juca");
        }
        clienteEnviaTCP.enviar(frame);
    }

    @Override
    public void recibirVideo(MensajeVideo frame) {
        impresora.imprimirMensaje(frame);
    }

    @Override
    public void enviarAudio(MensajeAudio sample) {
        sample.setOrigen(nombreUsuario);
        if(sample.getOrigen().equals("Juca")) {
            sample.setDestino("Juanito");
        } else {
            sample.setDestino("Juca");
        }
        clienteEnviaUDP.enviar(sample);
    }

    @Override
    public void recibirAudio(MensajeAudio sample) {
        impresora.imprimirMensaje(sample);
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public ConexionCliente getConexionCliente() {
        return conexionCliente;
    }

    public void setServidor(ConexionServidor conexionServidor) throws Exception {
        this.conexionServidor = conexionServidor;
        inicializarServicios();
    }

    public void setConexionCliente(ConexionCliente conexionCliente) throws Exception {
        this.conexionCliente = conexionCliente;
        inicializarServicios();
    }

    public void calcularLatencia(String origen, String destino) {
        MensajeLatencia mensajeLatencia = new MensajeLatencia(origen,destino);
        mensajeLatencia.setTiempoInicial(System.currentTimeMillis());
        long cantBytes = Serializador.serializar(mensajeLatencia).length;
        mensajeLatencia.setCantBytes(cantBytes);
        clienteEnviaTCP.enviar(mensajeLatencia);
    }

    @Override
    public void recibirLatencia(MensajeLatencia latencia) {
        if(latencia.isPong()) {
            // El mensaje de latencia llegó de regresó al cliente que lo solicitó.
            progresoTransferencia.setLatencia(latencia.getLatencia());
            impresora.imprimirProgreso(progresoTransferencia);
        } else {
            // El mensaje de latencia llegó al detino que necesitaba alcanzar y ahora
            // tiene que regresar al que lo solicitó.
            System.out.println("Me llegó un mensaje para solicitar la latencia!!! :)");
            latencia.setTiempoFinal(System.currentTimeMillis());
            latencia.setPong(true);
            String nuevoDestino = latencia.getOrigen();
            String nuevoOrigen = latencia.getDestino();
            latencia.setOrigen(nuevoOrigen);
            latencia.setDestino(nuevoDestino);
            clienteEnviaTCP.enviar(latencia);
        }
    }

    private class ContadorTiempo extends Thread {
        private long tiempoInicio;
        private long tiempoActual;

        public ContadorTiempo() {
            tiempoInicio = System.currentTimeMillis();
        }
        @Override
        public void run() {
            while (true) {
                try {
                    tiempoActual = System.currentTimeMillis();
                    tiempoActual = (tiempoActual - tiempoInicio) / 1000;
                    progresoTransferencia.setTiempoTranscurrido(tiempoActual);
                    impresora.imprimirProgreso(progresoTransferencia);
                    Thread.sleep(1000);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
