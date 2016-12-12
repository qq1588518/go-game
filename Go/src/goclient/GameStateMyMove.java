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


    /* (non-Javadoc)
     * @see goclient.GameState#makeMove(int, int)
     */
    @Override
    public void makeMove(int x, int y)
    {
        try
        {
            manager.getMediator().getGamePanel().getBoardPanel().addStone(manager.myColor, x, y);
        }
        catch (WrongCoordsException e)
        {
            manager.getMediator().displayError(e.getMessage());
        }
    }
    
}
