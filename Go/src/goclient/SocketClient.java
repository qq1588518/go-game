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
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter stdOut = new PrintWriter(System.out);
           // std
         //   
            System.out.println(in.readLine());
            out.println("CONNECTION OK");
          //  String userInput = stdIn.readLine();
            String serverLine;
            //stdIn.
            while((serverLine = in.readLine()) != null)
            {
                System.out.println("Serwer: " + serverLine);
                translator.processIncommingMessage(serverLine);
            }
            
            
//            do{
//                serverLine = in.readLine();
//                System.out.println(serverLine);
//                translator.processIncommingMessage(serverLine);
//                
//            }while(serverLine != null);

//            while((serverLine = in.readLine()) != null);
//            {
//                System.out.println(serverLine);
//                translator.processIncommingMessage(serverLine);
//             //   System.out.println(serverLine);
//            }
//            do
//            {
//                String serverLine = in.readLine();
//                
//                out.println(userInput);
//                
//            }while((userInput = stdIn.readLine()) != null);
            
            System.out.println("koniec");
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
//        catch (InterruptedException e)
//        {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
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
    public void send(String text) 
    { 
        out.println(text);
    }
    
    /**
     * Listens to the socket.
     */
    @Override
    public void run() { listenSocket(); }

    /**
     * @param translator2
     */
    public void setTranslator(ServerTranslator translator)
    {
        this.translator = translator;
        
    }
  //
//    /**
//     * Starts a new BSTclient.
//     * @param args the command line arguments, not used.
//     */
//    public static void main(String[] args)
//    {
//        SocketClient sc = new SocketClient();
//        sc.start();
//    }
    
}
