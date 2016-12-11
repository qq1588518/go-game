/**
 * 
 */
package goclient;

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
    Socket socket = null;
    PrintWriter out = null;
    BufferedReader in = null;
    ServerTranslator translator = null;
    Program program = null;
    String messageToSend = null;
    
    public SocketClient(Program program)
    {
        this.program = program;
        this.translator = program.getTranslator();
    }
    
    /**
     * Listens to the socket, sends user queries and prints out server responses.
     */
    public void listenSocket()
    {
        try 
        {
            socket = new Socket("localhost", 5556);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            System.out.println(in.readLine());
            out.println("CONNECTION OK");
            String serverLine;
            while((serverLine = in.readLine()) != null)
            {
                System.out.println("Serwer: " + serverLine);
                translator.processIncommingMessage(serverLine);
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
        listenSocket(); 
    }

    /**
     * @param translator2
     */
    public void setTranslator(ServerTranslator translator)
    {
        this.translator = translator;
    }
}
