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
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
/*
 * author: Francisco Jose Neff Hernandez
 * proyecto realizado en eclipse
 */
public class HttpConnection implements Runnable{
	private static final String STATUS_200="HTTP/1.1 200 OK\r\n";
	private static final String STATUS_400="HTTP/1.1 400 Bad Request\r\n";
	private static final String HTML_400="<html><body><h1>ERROR CODE: 400 Bad Request</h1><p> Your client has issued a malformed or ilegal request</p></body></html>";
	private static final String STATUS_404="HTTP/1.1 404 Not Found\r\n";
	private static final String HTML_404="<html><body><h1> ERROR CODE: 404 Not Found</h1><p> The request URL was not found</p></body></html>";
	private static final String STATUS_405="HTTP/1.1 405 Method Not Allowed\r\n";
	private static final String HTML_405="<html><body><h1>ERROR CODE: 405 Method Not Allowed</h1> <p>HTTP verb used to access this page is not allowed</p></body></html>";
	private static final String STATUS_505="HTTP/1.1 505 HTTP Version Not Supported\r\n";
	private static final String HTML_505="<html><body><h1>ERROR CODE: 505 HTTP Version Not Supported</h1> <p>Internal server error</p></body></html>"; 
	Socket socket= null;
    public HttpHeaders head = new HttpHeaders();
    String headers="";
    String url="";
    public HttpConnection (Socket s){
        socket=s;
    }
    public String uri= "C:/Users/Corgi/git/ppt1819_practica3_g07/www";
  
   
    @Override
    public void run() {
       DataOutputStream out = null;
        byte[] resource=null;
            try {
            	System.out.println("Server connection: "+socket.getInetAddress().toString());
            	String reqline="";
            BufferedReader buffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             reqline = buffer.readLine();
            System.out.println(reqline);
            try {
           url= AnalizeRequest(reqline);
           resource = ReadResource(url);
           headers=STATUS_200
           +head.GetHeaderHTTP(url,resource.length);
          System.out.println(headers);
          
            }catch (HttpExcepcion400 e400) {
            	resource=HTML_400.getBytes();
            	headers=STATUS_400
            	+head.GetHeaderHTTP(url,resource.length);
            	System.out.println(headers);
            }catch (HttpExcepcion404 e404) {
            	resource=HTML_404.getBytes();
            	headers=STATUS_404
            	+head.GetHeaderHTTP(url,resource.length);
            	System.out.println(headers);
            }catch (HttpExcepcion405 e405) {
            	resource=HTML_405.getBytes();
            	headers=STATUS_405
            	+head.GetHeaderHTTP(url,resource.length);
            	System.out.println(headers);
            }catch (HttpExcepcion505 e505) {
            	resource=HTML_505.getBytes();
            	headers=STATUS_505
            	+head.GetHeaderHTTP(url,resource.length);
            	System.out.println(headers);
            }
        }catch (IOException e) {
        	System.out.println("Error server connection: "+socket.getInetAddress().toString());
    				e.printStackTrace();
        }finally {
        	  try {//añadir url
        		  out = new DataOutputStream(socket.getOutputStream());
        		  out.write(headers.getBytes());
        		  out.write(resource);
        		  out.flush();
				out.close();
				socket.close();
			} catch (IOException e) {
				System.out.println("Error server connection: "+socket.getInetAddress().toString());
				e.printStackTrace();
			}
       	}
  }
           
   

	protected String AnalizeRequest(String reqline) throws  HttpExcepcion400, HttpExcepcion404, HttpExcepcion405, HttpExcepcion505 {
    	String path=""; 
    	if (reqline!=null) {
    	String[] items=reqline.split(" ");
         if(items.length==3){ // items[0]Método (GET | POST), items[1]Recurso(lo que se pide) y items[2]Versión HTTP/X.X
        	  if(items[2].equals("HTTP/0.9")||items[2].equals("HTTP/1.0")||items[2].equals("HTTP/1.1")){ //Versiones posibles correctas //la cabecera http/2.0 no esta
        		  if(items[2].equals("HTTP/1.1")){//La versión de HTTP1.1 es la unica que debe aceptar
                      if(items[0].equals("GET")||items[0].equals("POST")||items[0].equals("HEAD")||items[0].equals("OPTIONS")
                              ||items[0].equals("PUT")||items[0].equals("DELETE")||items[0].equals("TRACE")||items[0].equals("CONNECT")){//posibles metodos de http
                         //Posible recursos q tenemos
                    	  if(items[1].equals("/index.html")){
                      		path="/index.html";
                      	}else if(items[1].equals("/img/escudouja.jpg")) {
                      		path="/img/escudouja.jpg";
                      	}else {throw new HttpExcepcion404();}//404 Not Found
                  }else{throw new HttpExcepcion405();}//405 Method Not Allowed
              }else{ throw new HttpExcepcion400(); }//400 Bad Request (formato versión)
          }else { throw new HttpExcepcion405();} //405 Method Not Allowed
         }else{ throw new HttpExcepcion400(); }//400 peticion mal formada
    	}else { throw new HttpExcepcion400();}//400 Fallo al recibir la peticion
         return path;
 }

	
public class HttpExcepcion400 extends IOException{}
public class HttpExcepcion404 extends IOException{}
public class HttpExcepcion405 extends IOException{}
public class HttpExcepcion505 extends IOException{}

protected byte[]ReadResource(String url)throws FileNotFoundException,IOException{
	byte[] bytes=null;
	url=uri+url;//ruta donde se encuentra el archivo
	try {
		File resource = new File (url);
		if(resource.exists()) {
		FileInputStream fis = new FileInputStream(url);
		BufferedInputStream bis = new BufferedInputStream(fis);
		long tam=resource.length();
		bytes = new byte[(int) (tam)];
		bis.read(bytes,0,bytes.length);
		fis.close();}
	}catch(FileNotFoundException fix) {throw new HttpExcepcion404();
	}catch(IOException ioex) {throw new HttpExcepcion404();
	}
		return bytes;
}

}