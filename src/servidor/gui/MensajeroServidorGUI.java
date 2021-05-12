package servidor.gui;

import cliente.gui.MensajeroClienteGUI;
import cliente.interfaces.EnviadorMensaje;
import cliente.mensajes.EnviadorMensajeCliente;
import cliente.mensajes.MensajeTexto;
import cliente.tcp.ClienteTCP;
import cliente.udp.ClienteUDP;
import gui.MensajeroGUI;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MensajeroServidorGUI extends MensajeroGUI {

    private JTextArea txtAreaMensajes;

    public MensajeroServidorGUI() {
        this.cargarComponentes();
        this.agregarEventos();
        this.inicializarServicios();
    }

    protected void cargarComponentes() {
        super.cargarComponentes();
        lblUsuario = new JLabel("Servidor");
        lblUsuario.setFont(fuente);
        panelSuperior.add(lblUsuario,BorderLayout.LINE_START);
        panelGeneral.add(panelSuperior,BorderLayout.PAGE_START);

        panelCentral = new JPanel(new GridLayout(0,1));
        txtAreaMensajes = new JTextArea("Logs...");
        //txtAreaMensajes = new JTextArea("Mensajes del servidor...");
        txtAreaMensajes.setFont(fuente);
        txtAreaMensajes.setEditable(false);
        txtAreaMensajes.setBorder(bordePanel);
        panelCentral.add(txtAreaMensajes);
        panelGeneral.add(panelCentral);
    }

    protected void agregarEventos() {
        super.agregarEventos();
        btnEnviarMensaje.addActionListener(new EnviarMensaje());
    }

    protected void inicializarServicios() {
        // Code
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
