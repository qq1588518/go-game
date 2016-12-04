package goserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Observable;

public class ClientHandler extends Observable implements Runnable
{
    private BufferedReader reader;
    private PrintWriter writer;
    
    private Socket socket;
    private boolean running;
    
    /**
     * Creates a new thread to handle single client with given socket
     * @param socket Socket given by the server
     */
    public ClientHandler(Socket socket)
    {
        this.socket = socket;
    }
    
    /**
     * 
     */
    public void stopClient()
    {
        try 
        { 
            this.socket.close();
        }
        catch(IOException ioe)
        {
            System.out.println(ioe);
        };
    }
    
    /**
     * Runs a thread and handles communication between server and client programs
     */
    @Override
    public void run()
    {
        String message = null;
        System.out.println("asas");
        try 
        {
            running = false;
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);
            running = true; 
            writer.println("Welcome to go!");
        }
        catch(IOException ioe) 
        {
            System.out.println(ioe);
        }

        try 
        {	
        	System.out.println("blalbksld");
            message = reader.readLine();
            while (message != null && running) 
            {
                /**
                 * TODO: server logic
                 */
            	System.out.println(message); //echo w/o communication
            	
                writer.println(message); //echo
                
                reader.readLine();
            }
            running = false;
        }
        catch (IOException ioe) 
        {
            System.out.println(ioe);
            running = false;
        }
        try 
        {
            System.out.println("Closing connection");
            this.socket.close();
        } catch (IOException ioe) { System.out.println(ioe); }

        // Observer methods
        this.setChanged();              
        this.notifyObservers(this);     
    }
    
}
