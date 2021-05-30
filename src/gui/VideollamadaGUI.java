package gui;

import cliente.interfaces.EnviadorMensaje;
import cliente.interfaces.ImpresoraChat;
import cliente.mensajes.Mensaje;
import cliente.mensajes.MensajeAudio;
import cliente.mensajes.MensajeVideo;
import com.github.sarxos.webcam.Webcam;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;

public class VideollamadaGUI extends JFrame implements ImpresoraChat {
    private JPanel panelGeneral;
    private JPanel panelVideo;
    private JPanel panelOpciones;
    private JLabel lblImagenLocal;
    private JLabel lblImagenExterna;
    private JButton btnColgarVllamada;
    //private JButton btnIniciarVllamada;
    private Webcam webcam;
    private CapturadorVideo capturadorVideo;
    private CapturadorAudio capturadorAudio;
    private AudioFormat formatoAudio;
    private TargetDataLine microfono;
    private SourceDataLine bocinas;

    private EnviadorMensaje enviador;
    private String usuario;

    public VideollamadaGUI(String usuario, EnviadorMensaje enviador) {
        super("Mensajero Fenix");
        this.usuario = usuario;
        this.enviador = enviador;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600,600);
        setLayout(new BorderLayout());
        setResizable(false);
        setLocationRelativeTo(null);
        cargarComponentes();
        agregarEventos();
        iniciarAudio();
        //iniciarVideo();
    }

    private void cargarComponentes() {
        panelGeneral = new JPanel(new BorderLayout());
        add(panelGeneral, BorderLayout.CENTER);

        panelVideo = new JPanel(new FlowLayout());
        lblImagenLocal = new JLabel();
        lblImagenExterna = new JLabel();
        panelVideo.add(lblImagenLocal);
        panelVideo.add(lblImagenExterna);
        panelGeneral.add(panelVideo,BorderLayout.CENTER);

        panelOpciones = new JPanel(new FlowLayout());
        //btnIniciarVllamada = new JButton("Iniciar videollamada");
        btnColgarVllamada = new JButton("Colgar videollamda");
        //panelOpciones.add(btnIniciarVllamada);
        panelOpciones.add(btnColgarVllamada);
        panelGeneral.add(panelOpciones, BorderLayout.PAGE_END);
    }

    private void agregarEventos() {
        btnColgarVllamada.addActionListener(new ColgarVideollamda(this));
    }

    private void iniciarVideo() {
        webcam = Webcam.getDefault();
        webcam.setViewSize(new Dimension(320,240));
        webcam.open();
        capturadorVideo = new CapturadorVideo();
        capturadorVideo.start();
    }

    private void detenerVideo() {
        capturadorVideo.interrupt();
        webcam.close();
    }

    private void iniciarAudio() {
        try {
            // Se especifica el formato del audio a ser enviado.
            formatoAudio = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100,
                    16, 2, 4, 44100, true);
            // Se abre el microfono del dispositivo
            microfono = AudioSystem.getTargetDataLine(formatoAudio);

            DataLine.Info info = new DataLine.Info(TargetDataLine.class, formatoAudio);
            microfono = (TargetDataLine) AudioSystem.getLine(info);
            microfono.open(formatoAudio);
            microfono.start();

            // Se abren las bocinas del dispositivo
            DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, formatoAudio);
            bocinas = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
            bocinas.open(formatoAudio);
            bocinas.start();
            capturadorAudio = new CapturadorAudio();
            capturadorAudio.start();

        } catch (LineUnavailableException ex) {
            ex.printStackTrace();
        }
    }

    private void enviarFrame(ImageIcon frame) {
        MensajeVideo mensajeVideo = new MensajeVideo(frame);
        enviador.enviarVideo(mensajeVideo);
    }

    private void enviarSampleAudio(byte[] bytes) {
        MensajeAudio mensajeAudio = new MensajeAudio(bytes);
        enviador.enviarAudio(mensajeAudio);
    }

    private class CapturadorVideo extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    Image frame = webcam.getImage();
                    ImageIcon icono = new ImageIcon(frame);
                    enviarFrame(icono);
                    lblImagenLocal.setIcon(icono);
                    Thread.sleep(50);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private class CapturadorAudio extends Thread {
        @Override
        public void run() {
            ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
            int numBytesLeer;
            int tamanoChunck = 1024;
            try {
                byte[] bytes = new byte[microfono.getBufferSize() / 5];
                while (true) {
                    byteOutput.reset();
                    numBytesLeer = microfono.read(bytes, 0, tamanoChunck);
                    //  bytesRead += numBytesRead;
                    // write the mic data to a stream for use later
                    byteOutput.write(bytes, 0, numBytesLeer);
                    // write mic data to stream for immediate playback
                    //bocinas.write(bytes, 0, numBytesLeer);
                    enviarSampleAudio(byteOutput.toByteArray());
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private class ColgarVideollamda implements ActionListener {
        private VideollamadaGUI ventana;

        public ColgarVideollamda(VideollamadaGUI ventana) {
            this.ventana = ventana;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            ventana.detenerVideo();
            ventana.dispose();
        }
    }

    @Override
    public void imprimirMensaje(Mensaje mensaje) {
        if(mensaje instanceof MensajeVideo) {
            // Se imprime el frame recibido del otro cliente.
            lblImagenExterna.setIcon(((MensajeVideo) mensaje).getFrame());
        } else if(mensaje instanceof MensajeAudio) {
            System.out.println("RECIBIENDO INFORMACIÓN");
            MensajeAudio mensajeAudio = ((MensajeAudio) mensaje);
            bocinas.write(mensajeAudio.getBytes(), 0, 1024);
        }
    }
}