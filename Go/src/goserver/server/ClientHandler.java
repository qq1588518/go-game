package goserver.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Observable;

import goserver.game.ClientMessagesTranslator;
import goserver.game.Game;
import goserver.game.Player;


public class ClientHandler extends Observable implements Runnable
{
    private BufferedReader reader;
    private PrintWriter writer;
    
    private Socket socket;
    private Game game;
    private Player player;
    private ClientMessagesTranslator clientMessagesTranslator;
    
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
    
    public ClientMessagesTranslator getTranslator(){
    	return clientMessagesTranslator;
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
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);
            clientMessagesTranslator = new ClientMessagesTranslator(this);
            writer.println("WELCOME");
            while ((message = reader.readLine()) != null) 
            {
            	System.out.println("Client sent: " + message);
            	clientMessagesTranslator.processIncommingMessage(message);
            }          
        }
        catch (IOException ioe) 
        {
            System.out.println(ioe);
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

    public void send(String message)
    {
        writer.println(message);
        System.out.println("Sending: " + message);
    }

}