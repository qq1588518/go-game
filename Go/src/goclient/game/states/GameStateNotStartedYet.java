/**
 * 
 */
package goclient.game.states;

import java.awt.Point;

/**
 * @author mk
 * GameState before game, you can not do anything in this state. Part of the State design pattern.
 */
public class GameStateNotStartedYet implements GameState
{
    


    /* 
     * Do nothing - cannot click on board when game is not started.
     *
     * 
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
	public void remove(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void endMove(Point coords, boolean isAdding) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendProposal() {
		// TODO Auto-generated method stub
		
	}
}