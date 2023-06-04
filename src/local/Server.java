package local;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public void run() throws InterruptedException {
        try (ServerSocket server= new ServerSocket(3345)){
            Socket client = server.accept();
            System.out.print("Connection accepted.");

            // Запись
            DataOutputStream out = new DataOutputStream(client.getOutputStream());
            System.out.println("DataOutputStream  created");

            // Чтение
            DataInputStream in = new DataInputStream(client.getInputStream());
            System.out.println("DataInputStream created");


            while(!client.isClosed()){
                System.out.println("Server reading from channel");

                String entry = in.readUTF();
                System.out.println("READ from client message - "+entry);
                System.out.println("Server try writing to channel");

                if(entry.equalsIgnoreCase("quit")){
                    System.out.println("Client initialize connections suicide ...");
                    out.writeUTF("Server reply - "+entry + " - OK");
                    out.flush();
                    Thread.sleep(3000);
                    break;
                }

                out.writeUTF("Server reply - "+entry + " - OK");
                System.out.println("Server Wrote message to client.");

                out.flush();
            }

            System.out.println("Client disconnected");
            System.out.println("Closing connections & channels.");

            in.close();
            out.close();

            client.close();
            System.out.println("Closing connections & channels - DONE.");

        } catch (ConnectException e) {
            System.out.println("no connection");
        } catch (IOException e) {
            // e.printStackTrace();
        }
    }
}
