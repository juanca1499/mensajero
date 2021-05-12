package gui;

import cliente.interfaces.EnviadorMensaje;
import cliente.interfaces.ReceptorMensaje;
import cliente.mensajes.EnviadorMensajeCliente;
import cliente.mensajes.MensajeTexto;
import cliente.tcp.ClienteTCP;
import cliente.udp.ClienteUDP;
import com.sun.org.apache.bcel.internal.generic.Select;
import servidor.tcp.ServidorTCP;
import servidor.udp.ServidorUDP;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class MensajeroGUI extends JFrame {

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

    public MensajeroGUI() {
        super("Mensajero Fenix");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600,600);
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

    protected abstract void inicializarServicios() throws Exception;

    private class SelectorArchivo implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser selector = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            selector.setDialogTitle("Escoge el archivo a enviar:");
            selector.setFileSelectionMode(JFileChooser.FILES_ONLY);

            int valorRetorno = selector.showOpenDialog(panelCentral);
            if(valorRetorno == JFileChooser.APPROVE_OPTION) {
                lblStatus.setText("Archivo seleccionado: " + selector.getSelectedFile().getPath());
                txtAreaMensaje.setEnabled(false);
                archivoAdjunto = true;
            }
        }
    }
}