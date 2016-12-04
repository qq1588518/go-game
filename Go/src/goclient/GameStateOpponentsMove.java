/**
 * 
 */
package goclient;

/**
 * @author mk
 *
 */
public class GameStateOpponentsMove implements GameState
{
    
    /**
     * 
     */
    public GameStateOpponentsMove()
    {
        // TODO Auto-generated constructor stub
    }
    
    /* (non-Javadoc)
     * @see goclient.GameState#makeMove()
     */
    @Override
    public void makeMove()
    {
        System.out.println("Can't make move now. It's not your turn!");
    }
    
}
