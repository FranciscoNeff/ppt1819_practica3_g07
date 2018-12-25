package serverhttp;


import java.text.SimpleDateFormat;
import java.util.Date;

public class HttpHeaders {
private static final String DATE="Date: ";//Formato Fri, 15 May 2015 11:06:54 GMT try("EEE, dd MMM yyyy hh:mm:ss Z")
private static final String SERVER="Server: Corgi \r\n";
//private String type="Content-Type: ";//Content-Type: text/html; charset=UTF-8 \r\n
private static final String LENGTH="Content-Length: ";
private static final String ALLOW="Allow: ";//GET, POST,
private static final String CONNECTION = "Connection: close \r\n";
private static final String CRLF = " \r\n";

public String HeaderHttpDate(){
SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss Z");
Date fecha = new Date();
String timestamp =DATE +  format.format(fecha.getTime())+CRLF;
return timestamp;
}
public String HeaderHttpServer() {
	return SERVER;
}
public String HeaderHttpType(String mimeVersion) {
	String type="Content-Type: ";//Content-Type: text/html; charset=UTF-8 \r\n
	if (mimeVersion.endsWith(".html")) {
		type=type+"text/html;charset=UTF-8"+CRLF;
	}else if(mimeVersion.endsWith(".jpeg")) {
		type=type+"image/jpeg"+CRLF;
	}else if(mimeVersion.endsWith(".txt")) {
		type=type+"text/plain"+CRLF;
	}else {
		type=type+"text/html;charset=UTF-8"+CRLF;
	}//si por lo que sea no encuentra el type se manda el html para las posibles respuestas
	
	return type;
}
public String HeaderHttpLength(int length) {
	String s_length="Content-Length: "+length+CRLF;
	return s_length;
}
public String HeaderHttpAllow() {
	return ALLOW;
}
public String HeaderHttpConnection() {
	return CONNECTION;
}
}
