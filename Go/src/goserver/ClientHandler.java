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
    private Game game;
    private Player player;
    private ClientMessagesTranslator clientMessagesTranslator;
    private BufferedReader stdIn;
    
    /**
     * Creates a new thread to handle single client with given socket
     * @param socket Socket given by the server
     * @param parent 
     */
    public ClientHandler(Socket socket, Game game)
    {
        this.socket = socket;
        this.game = game;       
    }
    
    public BufferedReader getReader(){
    	return reader;
    }
    
    public PrintWriter getWriter(){
    	return writer;
    }
    
    public void setPlayer(Player player){
    	this.player = player;
    }
    
    public Player getPlayer(){
        return player;
    }
    
    public Game getGame(){
        return game;
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
        	running = false;
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);
           // stdIn = new BufferedReader(new InputStreamReader(System.in));
            clientMessagesTranslator = new ClientMessagesTranslator(this);
            running = true;
            writer.println("WELCOME");
            message = reader.readLine();
            System.out.println("Client sent: " + message);
            writer.println("SETNAME");
            while ((message = reader.readLine()) != null && running) 
            {
                /**
                 * TODO: server logic
                 */
            	System.out.println("Client sent: " + message);
            //	writer.println(message);
            	clientMessagesTranslator.processIncommingMessage(message);
            	
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
            game.deletePlayer(player);
            
            this.socket.close();
        } catch (IOException ioe) { System.out.println(ioe); }

        // Observer methods
        this.setChanged();              
        this.notifyObservers(this);     
    }

	
    
}
