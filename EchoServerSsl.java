//keytool -genkey -keystore mySrvKeystore -keyalg RSA
//java -Djavax.net.ssl.keyStore=mySrvKeystore -Djavax.net.ssl.keyStorePassword=changeit EchoServerSsl

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class EchoServerSsl {

  public static void main(String[] arstring) {

    try {

      SSLServerSocketFactory sslserversocketfactory =
      (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();

      SSLServerSocket sslserversocket =
      (SSLServerSocket) sslserversocketfactory.createServerSocket(9999);

      SSLSocket sslsocket = (SSLSocket) sslserversocket.accept();

      InputStream inputstream = sslsocket.getInputStream();
      InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
      BufferedReader bufferedreader = new BufferedReader(inputstreamreader);

      String string = null;

      while ((string = bufferedreader.readLine()) != null) {

        System.out.println(string);
        System.out.flush();

        if (string.equals("quit"))
          break;

        }
    }

    catch (Exception exception) {

      exception.printStackTrace();

    }
  }
}
