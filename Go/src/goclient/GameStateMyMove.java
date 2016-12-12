/**
 * 
 */
package goclient;

/**
 * @author mk
 *
 */
public class GameStateMyMove implements GameState
{
    
    private GameManager manager;


    /**
     * 
     */
    public GameStateMyMove(GameManager manager)
    {
       this.manager = manager;
    }


    /* TODO: żeby nie wysyłał kilku różnych ruchów za jednym razem.
     * (non-Javadoc)
     * @see goclient.GameState#makeMove(int, int)
     */
    @Override
    public void makeMove(int x, int y)
    {
        if (manager.checkIfMovePossible(x,y)) manager.sendMove(x, y);
    }
    
}
