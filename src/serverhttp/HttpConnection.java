package serverhttp;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HttpConnection implements Runnable{
    Socket socket= null;
    
    public HttpConnection (Socket s){
        socket=s;
    }

    @Override
    public void run() {
       DataOutputStream dos = null;
        try {
            /*System.out.println("Nueva conexión http con: "+socket.getInetAddress().toString());
            dos = new DataOutputStream(socket.getOutputStream());
            dos.write("200 OK\r\n".getBytes());
            dos.flush();*/
            
             /*dos.write(("ECO"+line+"\r\n).getBytes());
            dos.flush();*/
            
            BufferedReader buffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String reqline = buffer.readLine();
            
            String [] items=reqline.split(" ");
            if(items.length==3){ // Método, Recurso y Versión
                if(items[2].equals("HTTP/0.9")||items[2].equals("HTTP/1.0")||items[2].equals("HTTP/1.1")||items[2].equals("HTTP/2.0")){ //Versiones posibles correctas
                   
                    if(items[2].compareTo("HTTP/1.1")==0){
                        //La versión de HTTP es 1.1
                        
                        if(items[0].equals("GET")||items[0].equals("POST")||items[0].equals("HEAD")||items[0].equals("OPTIONS")
                                ||items[0].equals("PUT")||items[0].equals("DELETE")||items[0].equals("TRACE")||items[0].equals("CONNECT")){//posibles metodos de http
                           
                            
                            if(items[0].equals("GET")){
                                //método GET
                                
                                //BUSCAR EL RECURSO. SI EXISTE, DEVOLVERLO. SI NO EXISTE 404 Not Found
                                
                            }else{
                                //405 Method Not Allowed
                            }
                        }else{
                            //400 Bad Request (formato método)
                        }
                    }else{
                        //505 HTTP Version Not Supported
                    }
                }else{
                    //400 Bad Request (formato versión)
                }
            }else {
                //400 Bad Request (número de elementos en la RL)
            }
           
        } catch (IOException ex) {
            Logger.getLogger(HttpConnection.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                dos.close();
                socket.close();
            } catch (IOException ex) {
                Logger.getLogger(HttpConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}