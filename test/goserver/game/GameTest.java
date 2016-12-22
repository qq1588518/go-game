/**
 * 
 */
package goserver.game;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import goclient.program.EmptyNameException;
import goclient.program.NameContainsSpaceException;
import goserver.game.Game;
import goserver.server.ClientHandler;
import goserver.server.Server;

/**
 * @author mk
 *
 */
public class GameTest
{	
	Server s;
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
    	s = new Server();
    	s.startServer();
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
    
    
    @After
    public void cleanUp(){
    	s.stopServer();
    }
}
