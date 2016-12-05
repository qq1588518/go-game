/**
 * 
 */
package goclient;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Test case for GUIMediator Class.
 * @author mk
 *
 */
public class GUIMediatorTest
{
    GUIMediator m;
    
    @Before
    public void setUp()
    {
        m = new GUIMediator();
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
        assertTrue(m.getManager() instanceof GameManager);
    }
    
}
