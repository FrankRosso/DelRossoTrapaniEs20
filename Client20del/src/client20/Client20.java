package client20;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author FRANCESCODELROSSO
 */
public class Client20 {

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    public Client20() throws IOException {
        socket = new Socket("127.0.0.1", 13);
        socket.setSoTimeout(1000000);
        out = new DataOutputStream(socket.getOutputStream());
        in = new DataInputStream(socket.getInputStream());
    }

    public void inviaScelta(int scelta) throws IOException {
        out.writeInt(scelta);
    }

    public void inviaRicevi(int scelta, int id) throws IOException {
        out.writeInt(id);
        int risposta = in.readInt();
        if (scelta == 1) {
            if (risposta == 1) {
                System.out.println("Cassonetto aperto");
            } else if (risposta == -1) {
                System.out.println("La tessera non e' presente nei nostri archivi");
            } else if (risposta == -2) {
                System.out.println("Apertura negata");
            }
        } else if (scelta == 3) {
            if (risposta == 1) {
                System.out.println("La tessera e' stata eliminata con successo");
            }else {
                System.out.println("La tessera non e' presente nei nostri archivi");
            }   
        }
    }
    
    public void riceviId() throws IOException {
        int id = in.readInt();
        System.out.println("Registrazione avvenuta con successo!\n");
        System.out.println("Id: " + id);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int scelta = 0;
        int id;
        Scanner input = new Scanner(System.in);
        try {
            Client20 client = new Client20();
            while (true) {
                System.out.println("1. Apertura cassonetto");
                System.out.println("2. Registra tessera");
                System.out.println("3. Denuncia tessera");
                System.out.println("4. Esci\n");
                System.out.print("La tua scelta: ");
                scelta = input.nextInt();
                client.inviaScelta(scelta);

                switch (scelta) {
                    case 1:
                        System.out.print("Inserire il tuo id: ");
                        id = input.nextInt();
                        client.inviaRicevi(scelta, id);
                        break;
                    case 2:
                        client.riceviId();
                        break;
                    case 3:
                        System.out.print("Inserire id: ");
                        id = input.nextInt();
                        client.inviaRicevi(scelta, id);
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
    }

}
