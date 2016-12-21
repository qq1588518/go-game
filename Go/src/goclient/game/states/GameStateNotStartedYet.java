/**
 * 
 */
package goclient.game.states;

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

    /* (non-Javadoc)
     * @see goclient.GameState#reset()
     */
    @Override
    public void reset()
    {
        // TODO Auto-generated method stub
        
    }
    
    
    public void nextTurn() { }

	@Override
	public void setTeritory() {
		// TODO Auto-generated method stub
		
	}
}
