package server20;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ANDREATRAPANI
 */
public class Server20 extends Thread {

    private ServerSocket server;
    private ArrayList<Tessera> registrate = new ArrayList<>();

    public Server20() throws IOException {
        server = new ServerSocket(13);
        server.setSoTimeout(10000000);
    }

    @Override
    public void run() {
        Socket connection = null;

        try {
            connection = server.accept();
            DataInputStream in = new DataInputStream(connection.getInputStream());
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            int id, risposta, scelta;

            while (!Thread.interrupted()) {
                scelta = in.readInt();

                switch (scelta) {
                    case 1:
                        id = in.readInt();
                        risposta = apri(id);
                        out.writeInt(risposta);
                        break;
                    case 2:
                        risposta = registra();
                        out.writeInt(risposta);
                        break;
                    case 3:
                        id = in.readInt();
                        risposta = elimina(id);
                        out.writeInt(risposta);
                        break;
                    case 4:
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Scelta non valida!");
                }
            }
        } catch (IOException ex) {
        }
        try {
            server.close();
        } catch (IOException ex) {
            Logger.getLogger(Server20.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private int apri(int id) {
        for (int i = 0; i < registrate.size(); i++) {
            if (registrate.get(i).getId() == id) {
                if (registrate.get(i).isValida()) {
                    registrate.get(i).setAperturaRecente(LocalDateTime.now());
                    registrate.get(i).setValida(false);
                    return 1;
                } else {
                    return -2;
                }
            }
        }
        return -1;
    }

    private int registra() {
        Tessera t = new Tessera(registrate.size() + 1);
        registrate.add(t);
        return t.getId();
    }

    private int elimina(int id) {
        for (int i = 0; i < registrate.size(); i++) {
            if (registrate.get(i).getId() == id) {
                registrate.remove(i);
                return 1;
            }
        }
        return -1;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try {
            Server20 server = new Server20();
            server.start();
            server.join();
        } catch (IOException | InterruptedException ex) {
        }
    }

}
