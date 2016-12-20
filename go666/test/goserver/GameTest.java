/**
 * 
 */
package goserver;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.junit.Before;
import org.junit.Test;

import goclient.EmptyNameException;
import goclient.NameContainsSpaceException;

/**
 * @author mk
 *
 */
public class GameTest
{
    Game game;
    Socket socket;
    PrintWriter out;
    BufferedReader in;
    ClientHandler clientHandler;
    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception
    {
        game = new Game();
        socket = new Socket("localhost", 5556);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        clientHandler = new ClientHandler(socket, game);
    }
    
    @Test
    public void testChooseOpponent()
    {
        try {
			game.addPlayer("Ania", clientHandler);
		} catch (NameContainsSpaceException | EmptyNameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        assertTrue(game.chooseOpponent("Ania", clientHandler.getPlayer()));
    } 
    
    @Test
    public void MyNameInListTest(){
    	
    		try {
				clientHandler.getGame().addPlayer("Gosia", clientHandler);
			} catch (NameContainsSpaceException | EmptyNameException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    		//System.out.println(clientclientHandler2.getGame().chooseOpponent("Gosia", clientHandler.getPlayer()));
    		assertTrue(game.chooseOpponent("Gosia", clientHandler.getPlayer()));
    	
    }
}
