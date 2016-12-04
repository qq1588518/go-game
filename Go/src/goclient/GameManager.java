/**
 * 
 */
package goclient;

/**
 * To chyba ma być coś, co komunikuje GUI z resztą klienta. Ale nie jestem pewna. 
 * Albo może klienta z serwerem też trochę? 
 * Wzorowane na tym, ale nie mam koncepcji: 
 * http://blue-walrus.com/2011/10/swing-and-design-patterns-%e2%80%93-part-3-command-pattern/
 *
 */
public class GameManager
{
    GameState state;
    
    /**
     * 
     */
    public GameManager(GameState state)
    {
        this.state = state;
    }
    
    public void setState(GameState state)
    {
        this.state = state;
    }
    
    
    public GameState getState()
    {
        return state;
    }
    

    public void makeMove()
    {
       state.makeMove();
    }
    
    
    
}
