/**
 * 
 */
package goclient;

/**
 * @author mk
 *
 */
public class GameStateNotStartedYet implements GameState
{
    


    /* Do nothing - cannot click on board when game is not started.
     * (non-Javadoc)
     * @see goclient.GameState#makeMove(int, int)
     */
    @Override
    public void makeMove(int x, int y) {}
    
}
