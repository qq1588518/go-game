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
    
    private GameManager manager;

    /**
     * 
     */
    public GameStateOpponentsMove(GameManager manager)
    {
        this.manager = manager;
    }

    @Override
    public void makeMove(int x, int y)
    {
        System.out.println("Can't make move now. It's not your turn!");
    }

    /* (non-Javadoc)
     * @see goclient.GameState#reset()
     */
    @Override
    public void reset()
    {
        // TODO Auto-generated method stub
        
    }
    
}