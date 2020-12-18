import java.net.*;
class IPAddress
{
    public static void main(String args[])
    {
        try
        {
            InetAddress ipAddr = InetAddress.getLocalHost();
            System.out.println("\nIP address of the machine: " +ipAddr.getHostAddress());
        }
          catch (UnknownHostException ex)
          {
              ex.printStackTrace();
          }
    }
}