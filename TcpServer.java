//http://stilius.net/java/java_ssl.php
//keytool -genkey -keystore mySrvKeystore -keyalg RSA
//java -Djavax.net.ssl.keyStore=mySrvKeystore -Djavax.net.ssl.keyStorePassword=changeit TcpServer

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;

public class TcpServer {

  public static void main(String args[]) {

    //parse parameters
    TcpParms parms = new TcpParms(args);
    parms.logOptions();

    //ssl option
    if (parms.isSsl()) {

      //listen for ssl
      new TcpServer().acceptSslConnection(parms.getPort());
    }

    //non ssl option
    else {

      //listen for non-ssl
      new TcpServer().acceptNonSslConnection(parms.getPort());


    }

  }


  private void acceptSslConnection(int port) {

    try {

      /**
       * Secure socket
       */
      SSLServerSocketFactory sslserversocketfactory =
      (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();

      SSLServerSocket sslserversocket =
      (SSLServerSocket) sslserversocketfactory.createServerSocket(port);

      System.out.println("\n");
      System.out.println("Waiting for connection...");

      SSLSocket sslsocket = (SSLSocket) sslserversocket.accept();

      /**
       * Unsecure socket
       */
      //ServerSocket welcomeSocket = new ServerSocket(6789);
      //Socket connectionSocket = welcomeSocket.accept();

      InputStream inputstream = sslsocket.getInputStream();
      InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
      BufferedReader bufferedreader = new BufferedReader(inputstreamreader);

      System.out.println("\n");
      System.out.println("Ready to receive data");
      System.out.println("---------------------");

      String string = null;

      while ((string = bufferedreader.readLine()) != null) {

        System.out.println(string);
        System.out.flush();

        if (string.equals("quit"))
          break;

      }

      sslserversocket.close();

    }

    catch (Exception exception) {

      exception.printStackTrace();

    }
  }


  private void acceptNonSslConnection(int port) {

    try {

      /**
       * Secure socket
       */
      //SSLServerSocketFactory sslserversocketfactory =
      //(SSLServerSocketFactory) SSLServerSocketFactory.getDefault();

      //SSLServerSocket sslserversocket =
      //(SSLServerSocket) sslserversocketfactory.createServerSocket(port);


      //SSLSocket sslsocket = (SSLSocket) sslserversocket.accept();

      /**
       * Unsecure socket
       */
      ServerSocket welcomeSocket = new ServerSocket(port);

      System.out.println("\n");
      System.out.println("Waiting for connection...");

      Socket connectionSocket = welcomeSocket.accept();

      InputStream inputstream = connectionSocket.getInputStream();
      InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
      BufferedReader bufferedreader = new BufferedReader(inputstreamreader);

      System.out.println("\n");
      System.out.println("Ready to receive data");
      System.out.println("---------------------");

      String string = null;

      while ((string = bufferedreader.readLine()) != null) {

        System.out.println(string);
        System.out.flush();

        if (string.equals("quit"))
          break;

      }

      welcomeSocket.close();

    }

    catch (Exception exception) {

      exception.printStackTrace();

    }
  }


}
