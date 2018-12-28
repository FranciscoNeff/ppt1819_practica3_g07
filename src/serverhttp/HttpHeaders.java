package serverhttp;


import java.text.SimpleDateFormat;
import java.util.Date;
/*
 * Clase para formar la cabeceras de HTTP
 * se puede formar de una a una o por bloque completo
 */
public class HttpHeaders {
private static final String DATE="Date: ";//Formato Fri, 15 May 2015 17:06:54 GMT try("EEE, dd MMM yyyy HH:mm:ss Z")
private static final String SERVER="Server: Corgi \r\n";
private static final String LENGTH="Content-Length: ";
private static final String ALLOW="Allow: GET \r\n";//GET, POST,
private static final String CONNECTION = "Connection: close \r\n";
private static final String CRLF = " \r\n";

public String GetHeaderHTTP(String version, int length) {
	String headers="";
	SimpleDateFormat formato = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z");
	String type="Content-Type: ";//Content-Type: text/html; charset=UTF-8 \r\n
	if (version.endsWith(".html")) {
		type=type+"text/html; charset=UTF-8"+CRLF;
	}else if(version.endsWith(".jpg")) {
		type=type+"image/jpg"+CRLF;
	}else if(version.endsWith(".txt")) {
		type=type+"text/plain"+CRLF;
	}else {
		type=type+"text/html;charset=UTF-8"+CRLF;
	}//si por lo que sea no encuentra el type se manda el html para las posibles respuestas
	
	headers =DATE+formato.format(new Date())+CRLF
			+SERVER
			+ALLOW
			+CONNECTION
			+"Content-Length: "+length+CRLF
			+type
			+"\r\n";
	return headers;
}
public String HeaderHttpDate(){
SimpleDateFormat formato = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z");
String timestamp =DATE+formato.format(new Date())+CRLF;
return timestamp;
}
public String HeaderHttpServer() {
	return SERVER;
}
public String HeaderHttpType(String mimeVersion) {
	String type="Content-Type: ";//Content-Type: text/html; charset=UTF-8 \r\n
	if (mimeVersion.endsWith(".html")) {
		type=type+"text/html; charset=UTF-8"+CRLF;
	}else if(mimeVersion.endsWith(".jpg")) {
		type=type+"image/jpg"+CRLF;
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
