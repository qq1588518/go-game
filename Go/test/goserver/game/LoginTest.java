package goserver.game;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import goclient.program.EmptyNameException;
import goclient.program.NameContainsSpaceException;
import goclient.program.Program;
import goserver.game.Game;
import goserver.server.ClientHandler;
import goserver.server.Server;

public class LoginTest {

	Server s;
	Game game;
	ClientHandler clientHandler;
	Program p;
	
	
	
	@Test
	public void AddNewPlayerTest(){
		 s = new Server();
		 game = new Game();
		 clientHandler = new ClientHandler(s.getSocket(), game);
		
		 try {
			game.addPlayer("Gosia", clientHandler);
		} catch (NameContainsSpaceException | EmptyNameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 assertTrue(game.players.elementAt(0).getName().equals("Gosia"));
	}
	
	@Test
	public void TryAddTwoSamePlayersTest(){
		s = new Server();
		game = new Game();
		clientHandler = new ClientHandler(s.getSocket(), game);
		try {
			game.addPlayer("Gosia", clientHandler);
		} catch (NameContainsSpaceException | EmptyNameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			assertFalse(game.addPlayer("Gosia", clientHandler));
		} catch (NameContainsSpaceException | EmptyNameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
}