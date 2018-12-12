package serverhttp;

import java.text.SimpleDateFormat;

public class HttpHeaders {
private static final String DATE="Date: ";//Formato Fri, 15 May 2015 11:06:54 GMT try("EEE, dd MMM yyyy hh:mm:ss Z")
private static final String SERVER="Server: Corgi";
private static final String TYPE="Content-Type: ";//Content-Type: text/html; charset=UTF-8 \r\n
private static final String LENGTH="Content-Length: ";
private static final String ALLOW="Allow: ";//GET, POST,
private static final String CONNECTION = "Connection: close \r\n";

SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss Z");


}
