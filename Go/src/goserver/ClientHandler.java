package goserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Observable;

import gogame.Game;
import gogame.Player;
import goserver.Server;



public class ClientHandler extends Observable implements Runnable
{
    private BufferedReader reader;
    private PrintWriter writer;
    
    private Socket socket;
    private boolean running;
    private Server parent;
    private Game game;
    private String clientName;
    private ClientMessagesTranslator clientMessagesTranslator;
    private BufferedReader stdIn;
    
    /**
     * Creates a new thread to handle single client with given socket
     * @param socket Socket given by the server
     * @param parent 
     */
    public ClientHandler(Socket socket, Server parent)
    {
        this.socket = socket;
        this.parent = parent;
       game = new Game(parent);
       
    }
    
    public Game getGame(){
    	return game;
    }
    
    public BufferedReader getReader(){
    	return reader;
    }
    
    public PrintWriter getWriter(){
    	return writer;
    }
    
    public void setName(String name){
    	clientName = name;
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
    
    public String getName(){
    	return clientName;
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
        	running = false;
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);
           // stdIn = new BufferedReader(new InputStreamReader(System.in));
            clientMessagesTranslator = new ClientMessagesTranslator(this);
            running = true; 	
            writer.println("WELCOME");
            reader.readLine();
            writer.println("SETNAME");
            
          
            while ((message = reader.readLine()) != null && running) 
            {
                /**
                 * TODO: server logic
                 */
            	System.out.println("Server:" + message);
            	writer.println(message);
            	clientMessagesTranslator.processIncommingMessage(reader.readLine());
            	
            	
            	
            	
            	
            	
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
            parent.deletePlayer(clientName);
            
            this.socket.close();
        } catch (IOException ioe) { System.out.println(ioe); }

        // Observer methods
        this.setChanged();              
        this.notifyObservers(this);     
    }

	
    
}
