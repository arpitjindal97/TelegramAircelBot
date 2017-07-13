package org.telegram;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class WebController extends Thread
{
    static Logger log = LoggerFactory.getLogger(WebController.class.getName());
    private ServerSocket serverSocket;

    public WebController(int port) throws IOException
    {
        serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(10000);
    }

    public void run()
    {
        while (true)
        {
            try
            {
                System.out.println("Waiting for client on port " +
                        serverSocket.getLocalPort() + "...");
                Socket server = serverSocket.accept();

                log.info("Just connected to " + server.getRemoteSocketAddress());

                PrintWriter out = new PrintWriter(server.getOutputStream());
                out.println("HTTP/1.1 200 OK");
                out.println("Content-Type: text/html");
                out.println("\r\n");
                out.println("Thank you for connecting to " + server.getLocalSocketAddress()
                        + "\nGoodbye!");
                out.flush();

                out.close();
                server.close();

            } catch (SocketTimeoutException s)
            {
                log.info("Socket timed out!");
                break;
            } catch (IOException e)
            {
                e.printStackTrace();
                break;
            }
        }
    }

    public static void startServer()
    {
        int port = 5000;
        try
        {
            Thread t = new WebController(port);
            log.info("Starting Server at Port "+port);
            t.start();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
