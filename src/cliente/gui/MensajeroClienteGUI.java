package cliente.gui;

import cliente.mensajes.EnviadorMensajeCliente;
import cliente.mensajes.MensajeTexto;
import cliente.tcp.ClienteTCP;
import cliente.udp.ClienteUDP;
import gui.MensajeroGUI;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MensajeroClienteGUI extends MensajeroGUI {

    private JButton btnVideollamada;
    private JTextArea txtAreaMensajesPropios;
    private JTextArea txtAreaMensajesExternos;

    public MensajeroClienteGUI() throws Exception {
        this.cargarComponentes();
        this.agregarEventos();
        this.inicializarServicios();
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

    protected void inicializarServicios() {
        enviadorMensaje = new EnviadorMensajeCliente(
                new ClienteUDP("192.168.0.15",50000),
                new ClienteTCP("192.168.0.15",60000));
    }

    private class EnviarMensaje implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(!archivoAdjunto) {
                String texto = txtAreaMensaje.getText();
                MensajeTexto mensaje = new MensajeTexto("Cliente1","Cliente2",texto);
                enviadorMensaje.enviarMensaje(mensaje);
            } else {
                // Missing code
                archivoAdjunto = false;
                txtAreaMensaje.setEnabled(true);
                lblStatus.setText("Listo");
            }
        }
    }
}