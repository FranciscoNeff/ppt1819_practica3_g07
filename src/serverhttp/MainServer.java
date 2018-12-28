package serverhttp;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MainServer {
static ServerSocket server= null;
    
    public static void main(String[] args) {
        
        
        try {
            InetAddress serveraddr=InetAddress.getLocalHost(); //consigue la ip del equipo
            server= new ServerSocket (200,5,serveraddr); //servidor puerto 8000, 5 conexiones máximas,ip equipo
            System.out.println("Server waiting for HTTP connections...");
            
            while(true){
            Socket s=server.accept();
            HttpConnection conn = new HttpConnection(s);
            new Thread(conn).start();//el recolecto de basura de java elemina las instancias en memoria//Garbage Collector
            
            }
            
        } catch (UnknownHostException ex) {
            Logger.getLogger(MainServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
        Logger.getLogger(MainServer.class.getName()).log(Level.SEVERE, null, ex);
    }
        
        
       
        
        
        
    }
    
}
