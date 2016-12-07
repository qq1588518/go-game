///**
// * 
// */
//package goclient;
//
//import static org.junit.Assert.*;
//
//import org.junit.Before;
//import org.junit.Test;
//
///**
// * @author mk
// *
// */
//public class GameManagerTest
//{
//    GameManager gm;
//    
//    @Before
//    public void setUp()
//    {
//        gm = new GameManager(19, new GUIMediator());
//    }
//    
//    /**
//     * Test method for {@link goclient.GameManager#GameManager(goclient.GameState)}.
//     */
//    @Test
//    public void testGameManager()
//    {
//        assertNotNull(gm);
//    }
//    
//    /**
//     * Test method for {@link goclient.GameManager#setState(goclient.GameState)}.
//     */
//    @Test
//    public void testSetState()
//    {
//        GameState s = new GameStateOpponentsMove();
//        gm.setState(s);
//        assertSame(s, gm.getState());
//    }
//    
//    /**
//     * Test method for {@link goclient.GameManager#getState()}.
//     */
//    @Test
//    public void testGetState()
//    {
//        assertEquals(gm.getState().getClass(), GameStateNotStartedYet.class);
//    }
//    
//    /**
//     * TODO: 
//     * Test method for {@link goclient.GameManager#makeMove()}.
//     */
////    @Test
////    public void testMakeMove()
////    {
////        fail("Not yet implemented");
////    }
//    
//}
