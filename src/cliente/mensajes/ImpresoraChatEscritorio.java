package cliente.mensajes;

import cliente.interfaces.ImpresoraChat;

import javax.swing.JTextArea;

public class ImpresoraChatEscritorio implements ImpresoraChat {

    private JTextArea chat;

    public ImpresoraChatEscritorio(JTextArea chat) {
        this.chat = chat;
    }

    @Override
    public void imprimirMensaje(Mensaje mensaje) {
        if (mensaje instanceof MensajeTexto) {
           chat.append(mensaje.toString());
        }
    }
}
