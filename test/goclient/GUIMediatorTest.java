/**
 * 
 */
package goclient;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import goclient.game.GameManager;
import goclient.gui.GUIMediator;
import goclient.gui.GamePanel;
import goclient.gui.OptionsPanel;
import goclient.program.ComponentException;
import goclient.program.Program;
import goclient.program.ProgramManager;

/**
 * Test case for GUIMediator Class.
 * @author marcin
 *
 */
public class GUIMediatorTest
{
	
	ProgramManager pm;
    GUIMediator m;
    
    @Before
    public void setUp()
    {
    	pm = new ProgramManager(new Program());
        m = new GUIMediator(pm);
    }
    
    /**
     * Test method for {@link goclient.GUIMediator#GUIMediator(goclient.GameManager)}.
     */
    @Test
    public void testGUIMediator()
    {
        assertNotNull(m);
    }
    
    /**
     * Test method for {@link goclient.GUIMediator#getGamePanel()}.
     */
    @Test
    public void testGetGamePanel()
    {
        assertTrue(m.getGamePanel() instanceof GamePanel);
    }
    
    /**
     * Test method for {@link goclient.GUIMediator#getOptionsPanel()}.
     */
    @Test
    public void testGetOptionsPanel()
    {
        assertTrue(m.getOptionsPanel() instanceof OptionsPanel);
    }
    
    /**
     * Test method for {@link goclient.GUIMediator#getManager()}.
     */
    @Test
    public void testGetManager()
    {
        try {
			assertTrue(m.getGameManager() instanceof GameManager);
		} catch (ComponentException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
    }
    
    @Test
    public void PlayerListDialogTest(){
    	m.displayPlayersDialog("bla bla", "blabla");
    	assertTrue(m.getPlayerList().isVisible());
    }
    
    
}
