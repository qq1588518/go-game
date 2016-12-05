/**
 * 
 */
package goclient;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Test case for GUIMediator Class.
 * @author mk
 *
 */
public class GUIMediatorTest
{
    /**
     * Test method for {@link goclient.GUIMediator#GUIMediator(goclient.GameManager)}.
     */
    @Test
    public void testGUIMediator()
    {
        GUIMediator m = new GUIMediator(new GameManager(19));
        assertNotNull(m);
    }
    
    /**
     * Test method for {@link goclient.GUIMediator#getGamePanel()}.
     */
    @Test
    public void testGetGamePanel()
    {
        GUIMediator m = new GUIMediator(new GameManager(19));
        assertTrue(m.getGamePanel() instanceof GamePanel);
    }
    
    /**
     * Test method for {@link goclient.GUIMediator#getOptionsPanel()}.
     */
    @Test
    public void testGetOptionsPanel()
    {
        GUIMediator m = new GUIMediator(new GameManager(19));
        assertTrue(m.getOptionsPanel() instanceof OptionsPanel);
    }
    
    /**
     * Test method for {@link goclient.GUIMediator#getManager()}.
     */
    @Test
    public void testGetManager()
    {
        GameManager gm = new GameManager(19);
        GUIMediator m = new GUIMediator(gm);
        assertSame(m.getManager(), gm);
    }
    
}
