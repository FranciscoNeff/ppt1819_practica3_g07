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
           System.out.println("Server connection: "+socket.getInetAddress().toString());
            dos = new DataOutputStream(socket.getOutputStream());
            //dos.write("200 OK\r\n".getBytes());
            dos.flush();
            
            //////////////////////
            BufferedReader buffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String reqline = buffer.readLine();
            System.out.println("SERVER ["+socket.getInetAddress().toString()+"] received>"+reqline);
            String response="HTTP/1.1 200 OK\\r\\nContent-type:text/html\\r\\nConten-length:39\\r\\n\\r\\n";
            String entity="<html><body><h1>HOLA</h1></body></html>";
            try {
           String path=  analizeRequest(reqline);
            }catch (HttpExcepcion400 e400) {
            	
            }catch (HttpExcepcion404 e404) {
            	
            }catch (HttpExcepcion405 e405) {
            	
            }catch (HttpExcepcion505 e505) {
            	
            }
            
            dos.write(("ECO "+reqline).getBytes());
            dos.flush();
            String [] items=reqline.split(" ");
            if(items.length==3){ // Método, Recurso y Versión
                if(items[2].equals("HTTP/0.9")||items[2].equals("HTTP/1.0")||items[2].equals("HTTP/1.1")){ //Versiones posibles correctas //la cabecera http/2.0 no esta
                   
                    if(items[2].compareTo("HTTP/1.1")==0){
                        //La versión de HTTP es 1.1
                        
                        if(items[0].equals("GET")||items[0].equals("POST")||items[0].equals("HEAD")||items[0].equals("OPTIONS")
                                ||items[0].equals("PUT")||items[0].equals("DELETE")||items[0].equals("TRACE")||items[0].equals("CONNECT")){//posibles metodos de http
                           
                            
                            if(items[0].equals("GET")){
                                //método GET
                                
                                //BUSCAR EL RECURSO. SI EXISTE, DEVOLVERLO. SI NO EXISTE 404 Not Found
                                
                            }else{
                                //505 HTTP Version Not Supported
                            }
                        }else{
                            //405 Method Not Allowed  
                        }
                    }else{
                    	//400 Bad Request (formato método) 
                    }
                }else{
                    //400 Bad Request (formato versión)
                }
            }else {
                //400 Bad Request (número de elementos en la URL)
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
    protected String analizeRequest(String reqline) throws  HttpExcepcion400, HttpExcepcion404, HttpExcepcion405,HttpExcepcion505 {
    	if (!reqline.startsWith("GET")) {throw new HttpExcepcion405();}
    	return "/";
		
		
    }
  
    
    public class HttpExcepcion400 extends IOException{
    	 private String readEntity(String path) throws HttpExcepcion400{
 	        //Leer del fichero
 	        return "<html><body><h1>ERROR CODE: 400</h1><p> Your client has issued a malformed or ilegal request</p></body></html>";
 	        
 	    }
    }
public class HttpExcepcion404 extends IOException{
	private String readEntity(String path) throws HttpExcepcion404{
        //Leer del fichero
        return "<html><body><h1> ERROR CODE: 404</h1><p> The request URL was not found</p></body></html>";   
    }
    }
public class HttpExcepcion405 extends IOException{
	private String readEntity(String path) throws HttpExcepcion405{
        //Leer del fichero
        return "<html><body><h1>ERROR CODE: 405 </h1> <p>HTTP verb used to access this page is not allowed</p></body></html>";  
    }
}
public class HttpExcepcion505 extends IOException{
	private String readEntity(String path) throws HttpExcepcion505{
        //Leer del fichero
        return "<html><body><h1>ERROR CODE: 505 </h1> <p>Internal server error</p></body></html>";    
    }
}
}