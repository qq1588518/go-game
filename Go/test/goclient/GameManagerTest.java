/**
 * 
 */
package goclient;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import goclient.game.GameManager;
import goclient.game.StoneType;
import goclient.game.states.GameState;
import goclient.game.states.GameStateOpponentsMove;
import goclient.gui.GUIMediator;
import goclient.program.Program;
import goclient.program.ProgramManager;

/**
 * @author marcin
 *
 */
public class GameManagerTest
{
    GameManager gm;
    
    @Before
    public void setUp()
    {
        gm = new GameManager(19, new GUIMediator(new ProgramManager(new Program())), StoneType.BLACK);
    }
    
    /**
     * Test method for {@link goclient.GameManager#GameManager(goclient.GameState)}.
     */
    @Test
    public void testGameManager()
    {
        assertNotNull(gm);
    }
    
    /**
     * Test method for {@link goclient.GameManager#setState(goclient.GameState)}.
     */
    @Test
    public void testSetState()
    {
        GameState s = new GameStateOpponentsMove(gm);
        gm.setState(s);
        assertSame(s, gm.getState());
    }
    
    /**
     * Test method for {@link goclient.GameManager#getState()}.
     */
    @Test
    public void testGetState()
    {
    	GameState s = new GameStateOpponentsMove(gm);
    	gm.setState(s);
    	System.out.println(gm.getState().getClass());
        assertEquals(gm.getState().getClass(), GameStateOpponentsMove.class);
    }
    
    /**
     * TODO: 
     * Test method for {@link goclient.GameManager#makeMove()}.
     */
    @Test
    public void testMakeMove()
    {
        gm.makeMove(1, 1);
     //   gm.getMediator().getGamePanel().getBoardPanel().
    }
    
}