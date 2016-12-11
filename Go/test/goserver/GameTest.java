/**
 * 
 */
package goserver;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author mk
 *
 */
public class GameTest
{
    Game game;
    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception
    {
        game = new Game(null);
    }
    
    @Test
    public void testChooseOpponent()
    {
        game.addPlayer("Ania", new ClientHandler(null, game));
        assertTrue(game.chooseOpponent("Ania", null));
    }
    
}
