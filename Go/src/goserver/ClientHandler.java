package goserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Observable;

import goserver.Server;



public class ClientHandler extends Observable implements Runnable
{
    private BufferedReader reader;
    private PrintWriter writer;
    
    private Socket socket;
    private boolean running;
    private Server parent;
    
    /**
     * Creates a new thread to handle single client with given socket
     * @param socket Socket given by the server
     * @param parent 
     */
    public ClientHandler(Socket socket, Server parent)
    {
        this.socket = socket;
        this.parent = parent;
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
        
        try 
        {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);
            running = true; 	
            writer.println("WELCOME ");
            reader.readLine();
            writer.println("SETNAME ");
            while ((message = reader.readLine()) != null && running) 
            {
                /**
                 * TODO: server logic
                 */
            	System.out.println(message); //echo w/o communication
                
            	if(message.startsWith("USERNAME: ")){
            		message.substring(10, message.length());           		            		
            	}
            	
            	writer.println(message);
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
