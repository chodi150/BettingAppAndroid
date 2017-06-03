package chodi.com.betclientapp;

import java.io.IOException;
import java.net.Socket;



 public class ServerConnector {

    public static Socket clientSocket;
    ServerConnector()
    {
        try
        {
            clientSocket = new Socket("54.245.3.249", 8096);
        }
        catch (IOException e)
        {
            throw new Error("Problems while establishing connection to the server");
        }
    }


}
