package game;


import server.ClientHandler;
import server.Server;
import org.junit.Test;

import static org.junit.Assert.*;

public class LoginTest {

    private Server s;
    private Game game;
    private ClientHandler clientHandler;


    @Test
    public void AddNewPlayerTest() {
        s = new Server();
        game = new Game();
        clientHandler = new ClientHandler(s.getSocket(), game);

        try {
            game.addPlayer("Gosia", clientHandler);
        } catch (NameContainsSpaceException | EmptyNameException e) { }
        assertTrue(game.players.elementAt(0).getName().equals("Gosia"));
    }

    @Test
    public void TryAddTwoSamePlayersTest() {
        s = new Server();
        game = new Game();
        clientHandler = new ClientHandler(s.getSocket(), game);
        try {
            game.addPlayer("Gosia", clientHandler);
        } catch (NameContainsSpaceException | EmptyNameException e) {  }
        try {
            assertFalse(game.addPlayer("Gosia", clientHandler));
        } catch (NameContainsSpaceException | EmptyNameException e) {  }
    }


}