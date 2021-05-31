package gui;

import cliente.interfaces.ImpresoraChat;
import cliente.mensajes.Mensaje;
import utilidades.Alerta;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public abstract class MensajeroGUI extends JFrame implements ImpresoraChat {

    protected JPanel panelGeneral;
    protected JPanel panelCentral;
    protected JPanel panelSuperior;
    protected JPanel panelInferior;
    protected JPanel panelOpsEnvio;

    protected JLabel lblUsuario;
    protected JLabel lblStatus;

    protected JButton btnAdjuntarArchivo;
    protected JButton btnEnviarMensaje;

    protected JTextArea txtAreaMensaje;

    protected Font fuente;
    protected Border bordePanel;
    protected Icon iconVideollamada;
    protected Icon iconEnviarMensaje;
    protected Icon iconAdjuntarArchivo;

    protected boolean archivoAdjunto;

    protected String rutaArchivo;

    public MensajeroGUI() {
        super("Mensajero Fenix");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700,700);
        setLayout(new BorderLayout());
        setResizable(false);
        setLocationRelativeTo(null);
        archivoAdjunto = false;
    }

    protected void cargarComponentes() {
        panelGeneral = new JPanel(new BorderLayout());
        add(panelGeneral,BorderLayout.CENTER);

        fuente = new Font("Arial", Font.PLAIN, 15);
        bordePanel = BorderFactory.createLineBorder(Color.GRAY, 1, true);
        iconVideollamada = new ImageIcon("images/video-call.png");
        iconEnviarMensaje = new ImageIcon("images/send-message.png");
        iconAdjuntarArchivo = new ImageIcon("images/file-attach.png");

        panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBorder(bordePanel);
        lblUsuario = new JLabel("Cliente");
        lblUsuario.setFont(fuente);
        panelSuperior.add(lblUsuario,BorderLayout.LINE_START);
        panelGeneral.add(panelSuperior,BorderLayout.PAGE_START);

        panelInferior = new JPanel(new BorderLayout());
        panelOpsEnvio = new JPanel(new GridLayout(0,2));
        btnAdjuntarArchivo = new JButton(iconAdjuntarArchivo);
        btnAdjuntarArchivo.setFont(fuente);
        btnEnviarMensaje = new JButton(iconEnviarMensaje);
        btnEnviarMensaje.setFont(fuente);
        panelOpsEnvio.add(btnAdjuntarArchivo);
        panelOpsEnvio.add(btnEnviarMensaje);
        panelInferior.add(panelOpsEnvio,BorderLayout.LINE_END);

        //txtAreaMensaje = new JTextArea("Escribe un mensaje...");
        txtAreaMensaje = new JTextArea();
        txtAreaMensaje.setFont(fuente);
        panelInferior.add(txtAreaMensaje,BorderLayout.CENTER);
        panelInferior.setBorder(bordePanel);

        lblStatus = new JLabel("Listo");
        lblStatus.setFont(fuente);
        panelInferior.add(lblStatus,BorderLayout.PAGE_END);

        panelGeneral.add(panelInferior,BorderLayout.PAGE_END);
    }

    protected void agregarEventos() {
        btnAdjuntarArchivo.addActionListener(new SelectorArchivo());
    }

    private class SelectorArchivo implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            File archivo = Alerta.pedirArchivo(panelCentral);
            if(archivo != null) {
                rutaArchivo = archivo.getAbsolutePath();
                lblStatus.setText("Archivo seleccionado: " + rutaArchivo);
                txtAreaMensaje.setEnabled(false);
                archivoAdjunto = true;
            }
        }
    }

    public abstract void imprimirMensaje(Mensaje mensaje);
}