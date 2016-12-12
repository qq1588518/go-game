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
    private boolean moveSent = false;


    /**
     * 
     */
    public GameStateMyMove(GameManager manager)
    {
       this.manager = manager;
    }

    /* 
     * (non-Javadoc)
     * @see goclient.GameState#makeMove(int, int)
     */
    @Override
    public void makeMove(int x, int y)
    {
        if (!moveSent && manager.checkIfMovePossible(x,y)) 
        {
            manager.sendMove(x, y); 
            moveSent = true;
            manager.saveWaitingMove(x,y);
        }
    }
    
    public void reset() { moveSent = false; }
    
}
