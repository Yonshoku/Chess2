package local;

import chessgui.Game;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    public void run() throws InterruptedException {

        try(Socket socket = new Socket("localhost", 3345);
            DataOutputStream oos = new DataOutputStream(socket.getOutputStream());
            DataInputStream ois = new DataInputStream(socket.getInputStream()); )
        {

            System.out.println("Client connected to socket.");
            System.out.println();
            System.out.println("Client writing channel = oos & reading channel = ois initialized.");

            while(!socket.isOutputShutdown()){
                if(ois.read() > -1)     {
                    String serverResponse = ois.readUTF();
                    String line = ois.readUTF();
                    String moveResponse;
                    if (line.length() == 4) {
                        moveResponse = Game.processMove(Integer(Integer.parseInt(line[0]), Integer.parseInt(line[1]), Integer.parseInt(line[2]), Integer.parseInt(line[3]));
                    }

                    oos.writeUTF(moveResponse);
                }
            }


        } catch (UnknownHostException e) {
            new Server().run();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
