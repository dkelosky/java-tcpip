//http://stilius.net/java/java_ssl.php
//keytool -genkey -keystore mySrvKeystore -keyalg RSA
//java -Djavax.net.ssl.trustStore=mySrvKeystore -Djavax.net.ssl.trustStorePassword=changeit TcpClient

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.*;

public class TcpClient {

  public static void main(String args[]) {

    //parse parameters
    TcpParms parms = new TcpParms(args, true);
    parms.logOptions();

    //ssl option
    if (parms.isSsl()) {

      //listen for ssl
      new TcpClient().sendSslConnection(parms.getHost(), parms.getPort());
    }

    //non ssl option
    else {

      //listen for non-ssl
      new TcpClient().sendNonSslConnection(parms.getHost(), parms.getPort());

    }

  }

  private void sendSslConnection(String host, int port) {

    try {

      SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
      SSLSocket sslsocket = (SSLSocket) sslsocketfactory.createSocket(host, port);

      //Socket clientSocket = new Socket(InetAddress.getByName("10.132.3.112"), 6789, InetAddress.getByName("141.202.36.97"), 1676);

      InputStream inputstream = System.in;
      InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
      BufferedReader bufferedreader = new BufferedReader(inputstreamreader);

      OutputStream outputstream = sslsocket.getOutputStream();
      OutputStreamWriter outputstreamwriter = new OutputStreamWriter(outputstream);
      BufferedWriter bufferedwriter = new BufferedWriter(outputstreamwriter);

      System.out.println("\n");
      System.out.println("Ready to send data");
      System.out.println("------------------");

      String string = null;

      while ((string = bufferedreader.readLine()) != null) {

        bufferedwriter.write(string + '\n');
        bufferedwriter.flush();

        if (string.equals("quit"))
          break;

      }

      sslsocket.close();

    }

    catch (Exception exception) {

      exception.printStackTrace();

    }
  }


  private void sendNonSslConnection(String host, int port) {

    try {

      //SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
      //SSLSocket sslsocket = (SSLSocket) sslsocketfactory.createSocket("localhost", port);

      //Socket clientSocket = new Socket(InetAddress.getByName("10.132.3.112"), 6789, InetAddress.getByName("141.202.36.97"), 1676);

      Socket clientSocket = new Socket(InetAddress.getByName(host), port);
      //Socket clientSocket = new Socket(InetAddress.getByName("10.132.3.112"), 6789, InetAddress.getByName("141.202.36.97"), 1676);

      InputStream inputstream = System.in;
      InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
      BufferedReader bufferedreader = new BufferedReader(inputstreamreader);

      OutputStream outputstream = clientSocket.getOutputStream();
      OutputStreamWriter outputstreamwriter = new OutputStreamWriter(outputstream);
      BufferedWriter bufferedwriter = new BufferedWriter(outputstreamwriter);

      System.out.println("\n");
      System.out.println("Ready to send data");
      System.out.println("------------------");

      String string = null;

      while ((string = bufferedreader.readLine()) != null) {

        bufferedwriter.write(string + '\n');
        bufferedwriter.flush();

        if (string.equals("quit"))
          break;

      }

      clientSocket.close();

    }

    catch (Exception exception) {

      exception.printStackTrace();

    }
  }


}
