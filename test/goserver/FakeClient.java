package goserver;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import goserver.game.Game;
import goserver.game.Player;

public class FakeClient 
{
    


	Socket socket = null;
    PrintWriter out = null;
    BufferedReader in = null;
    boolean running = true;
    FakeClient client;
    Game game;
    Player player;
    
    
  
    
    public void listenSocket()
    {
        try {
            socket = new Socket("localhost", 5556);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println(in.readLine());
          }
          catch (UnknownHostException e) {
             System.out.println("Unknown host: localhost"); System.exit(1);
           }
           catch  (IOException e) {
             System.out.println("No I/O"); System.exit(1);
           }
            try
            {
                Thread.sleep(10);
            }
            catch (InterruptedException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }           
        
    }
    
    public void exitClient(){
    	try {
    		//game.deletePlayer(player);
			socket.close();
		
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
    
    
    
    @Before
    public void beforeTest(){
    	client = new FakeClient();
    	client.listenSocket();
    }
    
    @Test
    public void CommunicationTest(){
    	
    	
    	client.out.println("CONNECTION OK");
    	try {
			assertEquals(client.in.readLine(), "SETNAME");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    @Test
    public void AddPlayerTest(){
    	
    	client.out.println(("USERNAME baba"));
    	try {
    		
			assertEquals(client.in.readLine(), "NAMEOK");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    @Test
    public void NameTakenTest(){
    	client.out.println("USERNAME username");
    	try {
			assertEquals(client.in.readLine(), "NAMEOK");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	client.out.println("USERNAME username");
    	try {
			assertEquals(client.in.readLine(), "NAMETAKEN");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    @Test
    public void ReadListOfPlayersTest(){
    	client.out.println("USERNAME marcin");
    	try {
			assertEquals(client.in.readLine(), "NAMEOK");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	client.out.println("USERNAME gosia");
    	try {
			assertEquals(client.in.readLine(), "NAMEOK");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	client.out.println("LIST");
    	try {
    		System.out.println("tgtgtgtg");
			System.out.println(client.in.readLine());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	client.out.println("LIST");
    	try {
			System.out.println(client.in.readLine());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    
    @After
    public void afterTest()
    {
    	client.exitClient();
    }
    
}
