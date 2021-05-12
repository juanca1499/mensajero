package servidor.udp;

import cliente.interfaces.EnviadorMensaje;
import cliente.interfaces.ReceptorMensaje;
import servidor.tcp.ServidorEscuchaTCP;

public class ServidorUDP{
    public final int PUERTO_SERVER;

    public ServidorUDP(int puertoS, ReceptorMensaje receptorMensaje) {
        PUERTO_SERVER = puertoS;
    }

    public void inicia() throws Exception {
        //ServidorEscuchaUDP servidorEscuchaUDP = new ServidorEscuchaUDP(PUERTO_SERVER);
        //ServidorEscuchaTCP servidorEscuchaTCP = new ServidorEscuchaTCP(PUERTO_SERVER);
        //servidorEscuchaUDP.start();
        //servidorEscuchaTCP.start();
    }
}
