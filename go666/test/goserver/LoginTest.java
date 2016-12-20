package goserver;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import goclient.EmptyNameException;
import goclient.NameContainsSpaceException;
import goclient.Program;

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
