/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serveurtcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author niass028652
 */
public class ThreadServeur extends Thread{
    private Socket client;
    public ThreadServeur(Socket client){
        this.client = client;
    }

    @Override
    public void run() {
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new PrintWriter(client.getOutputStream(), true);
            String pseudo = null;
            do {  
                pseudo = in.readLine();
                boolean existe = ServeurTCP.clients.containsKey(pseudo);
                if(existe){
                   out.println("Ce pseudo est deja pris, veillez choisir un autre");
                }
                else{
                    break;
                }
            } while (true);
            ServeurTCP.clients.put(pseudo, client);
            
            while(true){
               String message = in.readLine();
               if(message == null || message.equalsIgnoreCase("fin")){
                   break;
               }
                for (Map.Entry<String, Socket> en : ServeurTCP.clients.entrySet()) {
                    String pso = en.getKey();
                    if(!pso.equals(pseudo)){
                        Socket unclient = en.getValue();
                        PrintWriter outer = new PrintWriter(unclient.getOutputStream(), true);
                        outer.println(pseudo + " dit : "+message);
                    }
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            try {
                in.close();
                out.close();
                client.close();
            } catch (IOException ex) {
                
            }
        }
    }
    
    
}
