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
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);
            running = true; 	
            writer.println("WELCOME");
            reader.readLine();
            writer.println("SETNAME");
            
           
            while ((message = reader.readLine()) != null && running) 
            {
                /**
                 * TODO: server logic
                 */
            	//writer.println(message); //echo w/o communication
                //System.out.println("sasasaasas sasadsa " + message);
            	if(message.startsWith("USERNAME")){
            		        		
            			if(game.addPlayer(message.substring(10, message.length()))){
            				writer.println("NAMEOK" + parent.getPlayers());
            				clientName = message.substring(10, message.length());
            			}
            			
            			else{
            				
            				writer.println("NAMETAKEN");
            				System.out.println("nametaken");
            			}
            			
            			
            			//writer.println(game.writePlayers());
            			System.out.println(game.getPlayers());
            			
            		    		            		
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
            parent.deletePlayer(clientName);
            
            this.socket.close();
        } catch (IOException ioe) { System.out.println(ioe); }

        // Observer methods
        this.setChanged();              
        this.notifyObservers(this);     
    }
    
}
