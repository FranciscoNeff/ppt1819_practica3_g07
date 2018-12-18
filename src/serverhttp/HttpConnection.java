package serverhttp;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HttpConnection implements Runnable{
    Socket socket= null;
    public HttpHeaders head = new HttpHeaders();
    String headers="";
    //headers=headers+head.HeaderHttpType(path);
    public HttpConnection (Socket s){
        socket=s;
    }
    public String directory= "C:\\Users\\Corgi\\git\\ppt1819_practica3_g07\\www\\";
    //revisar rutas de directorios por internet
    String uri = directory.replace("/","\\");
   
    @Override
    public void run() {
       DataOutputStream out = null;
        
           System.out.println("Server connection: "+socket.getInetAddress().toString());
            try {
				out = new DataOutputStream(socket.getOutputStream());
				out.flush();
            //dos.write("200 OK\r\n".getBytes());
            
            
            //////////////////////
            BufferedReader buffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String reqline = buffer.readLine();
           // System.out.println("SERVER ["+socket.getInetAddress().toString()+"] received>"+reqline);
          //String response="HTTP/1.1 200 OK\\r\\nContent-type:text/html\\r\\nConten-length:39\\r\\n\\r\\n";
           
            
            try {
           String path=  analizeRequest(reqline);
           String response="HTTP/1.1 200 OK \r\n";
           String entity="<html><body><h1>HOLA</h1></body></html>";//para probar
           
           headers=headers+head.HeaderHttpDate()+head.HeaderHttpServer()+head.HeaderHttpLength(entity.length());
           out = new DataOutputStream(socket.getOutputStream());
           response= response +headers;
           out.write(response.getBytes());
           out.write(entity.getBytes());
           //String entity="<html><body><h1>HOLA</h1></body></html>";//para probar
            }catch (HttpExcepcion400 e400) {
            	
            }catch (HttpExcepcion404 e404) {
            	
            }catch (HttpExcepcion405 e405) {
            	
            }catch (HttpExcepcion505 e505) {
            	
            
            }
        }catch (IOException e) {
    				e.printStackTrace();
    			
        }finally {
        	/*dos.write(("ECO "+reqline).getBytes());
            dos.flush();*/
        	  try {
				out.close();
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
       	}
  }
           
    protected String analizeRequest(String reqline) throws  HttpExcepcion400, HttpExcepcion404, HttpExcepcion405, HttpExcepcion505 {
    	 String [] items=reqline.split(" ");
         if(items.length==3){ // items[0]Método (GET | POST), items[1]Recurso(lo que se pide) y items[2]Versión HTTP/X.X
        	  if(items[2].equals("HTTP/0.9")||items[2].equals("HTTP/1.0")||items[2].equals("HTTP/1.1")){ //Versiones posibles correctas //la cabecera http/2.0 no esta
                  
                  if(items[2].equals("HTTP/1.1")){
                      //La versión de HTTP1.1 es la unica que debe aceptar
                      
                      if(items[0].equals("GET")||items[0].equals("POST")||items[0].equals("HEAD")||items[0].equals("OPTIONS")
                              ||items[0].equals("PUT")||items[0].equals("DELETE")||items[0].equals("TRACE")||items[0].equals("CONNECT")){//posibles metodos de http
                         
                          
                          if(!items[0].startsWith("GET")){
                      		
                          }
                          
                      
                  }else{
                  	//400 Bad Request (formato método)
                	  throw new HttpExcepcion400();
                  }
              }else{
                  //400 Bad Request (formato versión)
            	  throw new HttpExcepcion400();
              }
          }else {
              //405 Method Not Allowed
            	  throw new HttpExcepcion405();        
          }
         }else{
             //505 HTTP Version Not Supported
        	 throw new HttpExcepcion505();
         }
         return "/";
 }
    
 
    public class HttpExcepcion400 extends IOException{
    	 private String readEntity(String path) throws HttpExcepcion400{
    		 String entity="<html><body><h1>ERROR CODE: 400</h1><p> Your client has issued a malformed or ilegal request</p></body></html>";
 	        //Leer del fichero
 	        return entity;
 	        
 	    }
    }
public class HttpExcepcion404 extends IOException{
	private String readEntity(String path) throws HttpExcepcion404{
		String entity="<html><body><h1> ERROR CODE: 404</h1><p> The request URL was not found</p></body></html>";   
		//Leer del fichero
        return entity;
    }
    }
public class HttpExcepcion405 extends IOException{
	private String readEntity(String path) throws HttpExcepcion405{
       String entity="<html><body><h1>ERROR CODE: 405 </h1> <p>HTTP verb used to access this page is not allowed</p></body></html>";  
		//Leer del fichero
        return entity;
    }
}
public class HttpExcepcion505 extends IOException{
	private String readEntity(String path) throws HttpExcepcion505{
        String entity="<html><body><h1>ERROR CODE: 505 </h1> <p>Internal server error</p></body></html>";    
		//Leer del fichero
        return entity;
    }
}

private byte[]readRecurso(String uri)throws FileNotFoundException,IOException{
	byte[] bytes=null;//por si acaso el archivo no esta=null
	try {
		File resource = new File (uri);
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(uri));
		bytes = new byte[(int) resource.length()];
		
	}catch(FileNotFoundException fix) {throw new HttpExcepcion404();
	}catch(IOException ioex) {throw new HttpExcepcion404();}
		return bytes;
}

}