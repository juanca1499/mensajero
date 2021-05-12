package cliente.gui;

import cliente.Cliente;
import cliente.ConexionCliente;
import cliente.interfaces.ImpresoraChat;
import cliente.mensajes.*;
import cliente.tcp.ClienteEnviaTCP;
import cliente.tcp.ClienteTCP;
import cliente.udp.ClienteEnviaUDP;
import cliente.udp.ClienteEscuchaUDP;
import cliente.udp.ClienteUDP;
import gui.MensajeroGUI;
import servidor.ConexionServidor;
import servidor.Servidor;
import sun.reflect.annotation.ExceptionProxy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.DatagramSocket;

public class MensajeroClienteGUI extends MensajeroGUI {

    private JButton btnVideollamada;
    private JTextArea txtAreaMensajesPropios;
    private JTextArea txtAreaMensajesExternos;

    private ImpresoraChat impresora;

    private Cliente cliente;
    private ConexionServidor conServidor;

    public MensajeroClienteGUI(ConexionServidor conServidor) throws Exception {
        this.conServidor = conServidor;
        this.cargarComponentes();
        this.agregarEventos();
        this.inicializarServicios();
        impresora = new ImpresoraChatEscritorio(txtAreaMensajesExternos);
    }

    protected void cargarComponentes() {
        super.cargarComponentes();
        panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBorder(bordePanel);
        lblUsuario = new JLabel("Cliente");
        lblUsuario.setFont(fuente);
        panelSuperior.add(lblUsuario,BorderLayout.LINE_START);
        btnVideollamada = new JButton(iconVideollamada);
        btnVideollamada.setFont(fuente);
        panelSuperior.add(btnVideollamada,BorderLayout.LINE_END);
        panelGeneral.add(panelSuperior,BorderLayout.PAGE_START);

        panelCentral = new JPanel(new GridLayout(0,2));
        txtAreaMensajesExternos = new JTextArea("Mensajes recibidos...");
        txtAreaMensajesExternos.setFont(fuente);
        txtAreaMensajesExternos.setEditable(false);
        txtAreaMensajesExternos.setBorder(bordePanel);
        panelCentral.add(txtAreaMensajesExternos);
        txtAreaMensajesPropios = new JTextArea("Mensajes enviados...");
        txtAreaMensajesPropios.setFont(fuente);
        txtAreaMensajesPropios.setEditable(false);
        txtAreaMensajesPropios.setBorder(bordePanel);
        panelCentral.add(txtAreaMensajesPropios);
        panelGeneral.add(panelCentral,BorderLayout.CENTER);
    }

    protected void agregarEventos() {
        super.agregarEventos();
        btnEnviarMensaje.addActionListener(new EnviarMensaje());
    }

    protected void inicializarServicios() throws Exception {
        cliente = new Cliente(conServidor,"Juca",impresora);
    }

    private class EnviarMensaje implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(!archivoAdjunto) {
                lblStatus.setText("Enviando mensaje...");
                String texto = txtAreaMensaje.getText();
                MensajeTexto mensaje = new MensajeTexto("Cliente1","Cliente2",texto);
                cliente.enviarMensaje(mensaje);
                lblStatus.setText("Listo");
                txtAreaMensaje.setText("");
                txtAreaMensajesPropios.append("\n" + mensaje);
            } else {
                // Missing code
                archivoAdjunto = false;
                txtAreaMensaje.setEnabled(true);
                lblStatus.setText("Listo");
            }
        }
    }

    public JTextArea getCajaMensajes() {
        return txtAreaMensajesExternos;
    }
}