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
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
   
            String userInput;
            System.out.println(in.readLine());
            while ((userInput = stdIn.readLine()) != null) 
            {
                out.println(userInput);
                System.out.println(in.readLine());
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
    
//    /**
//     * Reads the server response from stream.
//     * @return String with the response
//     * @throws IOException when the server is not available.
//     */
//    public String read() throws IOException
//    {
//        StringBuilder sb = new StringBuilder();
//        int c;
//        while ((c = in.read()) != '\0') sb.append((char) c);   
//        return sb.toString();
//    }
    
    /**
     * Gets the PrintWriter to write to the server.
     * @return PrintWriter.
     */
    public  PrintWriter getPrintWriter() { return out; }
    
    /**
     * Listens to the socket.
     */
    @Override
    public void run() { listenSocket(); }
    
    /**
     * Starts a new BSTclient.
     * @param args the command line arguments, not used.
     */
    public static void main(String[] args)
    {
        SocketClient sc = new SocketClient();
        sc.start();
    }
    
}
