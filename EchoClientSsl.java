//keytool -genkey -keystore mySrvKeystore -keyalg RSA
//java -Djavax.net.ssl.trustStore=mySrvKeystore -Djavax.net.ssl.trustStorePassword=changeit EchoClientSsl

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;

public class EchoClientSsl {

  public static void main(String[] arstring) {

    try {

      SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
      SSLSocket sslsocket = (SSLSocket) sslsocketfactory.createSocket("localhost", 9999);

      InputStream inputstream = System.in;
      InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
      BufferedReader bufferedreader = new BufferedReader(inputstreamreader);

      OutputStream outputstream = sslsocket.getOutputStream();
      OutputStreamWriter outputstreamwriter = new OutputStreamWriter(outputstream);
      BufferedWriter bufferedwriter = new BufferedWriter(outputstreamwriter);

      String string = null;

      while ((string = bufferedreader.readLine()) != null) {

        bufferedwriter.write(string + '\n');
        bufferedwriter.flush();

        if (string.equals("quit"))
          break;

      }
    }

    catch (Exception exception) {

      exception.printStackTrace();

    }
  }
}
