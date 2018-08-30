/**
 * 
 */
package goclient.program;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @author mk
 *
 */
public class SocketClient extends Thread
{
    private Socket socket = null;
    private PrintWriter out = null;
    private ServerTranslator translator;

    public SocketClient(ServerTranslator translator)
    {
        this.translator = translator;
    }
    
    /**
     * Listens to the socket, sends user queries and prints out server responses.
     */
    private void listenSocket()
    {
        try 
        {
            socket = new Socket("localhost", 5556);
            out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            System.out.println(in.readLine());
            out.println("CONNECTION OK");
            String serverLine;
            while((serverLine = in.readLine()) != null)
            {
                System.out.println("Server: " + serverLine);
                translator.processIncomingMessage(serverLine);
            }
        }
        catch (UnknownHostException e) 
        {
           System.out.println("Unknown host: localhost"); 
           System.exit(1);
        }
        catch  (IOException e)
        {
            System.out.println("No I/O"); 
            System.exit(1);
        }
    }
    
    /**
     * Gets the PrintWriter to write to the server.
     * @return PrintWriter.
     */
    public void send(String text) 
    { 
        out.println(text);
    }
    
    /**
     * Listens to the socket.
     */
    @Override
    public void run() 
    { 
        try
        {
            sleep(100);
        }
        catch (InterruptedException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        listenSocket(); 
    }

    /**
     * @param translator
     */
    public void setTranslator(ServerTranslator translator)
    {
        this.translator = translator;
    }
}