public class TcpParms {

  //constants
  private static final String SSL_OPT = "-ssl";
  private static final String PORT_OPT = "-port";
  private static final String HOST_OPT = "-host";
  private static final String VALUE_CHARACTER = "=";

  private static final boolean DEFAULT_SSL_OPT = false;
  private static final int DEFAULT_PORT_OPT = 1234;
  private static final String DEFAULT_HOST_OPT = "localhost";

  private static final int LOW_PORT = 1;
  private static final int HI_PORT = 65535;

  //variables
  private boolean useSsl;
  private int port;
  private String host;

  private boolean client;


  //constructor
  public TcpParms(String args[]) {

    //set default values
    setDefaults();

    //log message
    System.out.println("Parsing common options");

    //iterate through command arguments
    for (String s: args) {

      parseCommonOption(s);

    }
  }


  //constructor
  public TcpParms(String args[], boolean isClient) {

    //set default values
    setDefaults();


    if (isClient) {

      //indicate this is a client
      client = true;

      //log message
      System.out.println("Parsing client options");

      //iterate through command arguments
      for (String s: args) {

        parseClientOptions(s);

      }

    }

    else {

      //log message
      System.out.println("Parsing common options");

      //iterate through command arguments
      for (String s: args) {

        parseCommonOption(s);

      }
    }
  }


  private void setDefaults() {

    //assume not a client
    client = false;

    //set defaults
    useSsl = DEFAULT_SSL_OPT;
    port = DEFAULT_PORT_OPT;
    host = DEFAULT_HOST_OPT;

  }


  //check individual options
  private boolean parseCommonOption(String option) {

    //assume we wont procees
    boolean processed = false;

    //user specified ssl
    if (SSL_OPT.equals(option))  {

      //note we proccessed a parm
      processed = true;

      //set indicator
      useSsl = true;

    }

    //determine if option is long enough to be the option
    else if (option.length() >= PORT_OPT.length()) {

      //used specified port
      if (PORT_OPT.equals(option.substring(0, PORT_OPT.length()))) {

        //note we proccessed a parm
        processed = true;

        //parse out the numeric value of the option
        String parts[] = option.split(VALUE_CHARACTER);

        //determine if the numberic value is indeed numeric
        try {

          //attempt parse
          int newPort = Integer.parseInt(parts[1].trim());

          //validate port range
          if (newPort >= LOW_PORT && newPort < HI_PORT) {

            //set requested port
            port = newPort;

          }

          //invalid input given
          else {

            //log message
            System.out.println("Provide a port in range of " + LOW_PORT + "-" + HI_PORT);

          }

        }

        //invaldd input given
        catch (NumberFormatException nfe) {

          //log message
          System.out.println("Provide a numeric value for, " + PORT_OPT + ": " + nfe.getMessage());

        }

      }

    }

    //unknown option specified
    else {

      //log message
      System.out.println("Unknown option entered: " + option);

    }

    //return indicator if we processed a parm
    return processed;

  }


  private boolean parseClientOptions(String option) {

    //assume we wont procees
    boolean processed = false;

    //attempt to parse commpn options first
    processed = parseCommonOption(option);

    //if not already processed
    if (!processed) {

      //parse for extra client only options
      parseExtraClientOption(option);

    }

    //return indicator if we processed a parm
    return processed;

  }


  private boolean parseExtraClientOption(String option) {

    //assume we wont procees
    boolean processed = false;

    //determine if option is long enough to be the option
    if (option.length() >= HOST_OPT.length()) {

      //used specified host
      if (HOST_OPT.equals(option.substring(0, HOST_OPT.length()))) {

        //note we proccessed a parm
        processed = true;

        //parse out the numeric value of the option
        String parts[] = option.split(VALUE_CHARACTER);

        //set requested host
        host = parts[1];

      }

    }


    //return indicator if we processed a parm
    return processed;

  }


  //log the options we're using
  public void logOptions() {

    //log options being used
    System.out.println("Options requested:");
    System.out.println("  Use SSL set to - " + useSsl);
    System.out.println("  Use port       - " + port);

    if (client)
      System.out.println("  Use host       - " + host);

    System.out.println("\n");

  }


  //return whether or not SSL was specified
  public boolean isSsl() {

    return useSsl;

  }


  //return the port to use
  public int getPort() {

    return port;

  }


  //return the host to use
  public String getHost() {

    return host;

  }


}
