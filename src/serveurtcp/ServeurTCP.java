/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serveurtcp;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author niass028652
 */
public class ServeurTCP {

    static Map<String, Socket> clients;
    public static void main(String[] args) {
        clients = new HashMap<>();
        ServerSocket  serverSocket = null;
        try {
            serverSocket = new ServerSocket(5001);
            System.out.println("Serveur lancé sur le port 5001");
            Socket client = null;
            int i = 0;
            while (true) {                
                client = serverSocket.accept();
                ++i;
                System.out.println("Client n° "+i);
                new ThreadServeur(client).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
