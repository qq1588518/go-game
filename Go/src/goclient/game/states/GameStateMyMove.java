/**
 * 
 */
package goclient.game.states;

import java.awt.Point;

import goclient.game.GameManager;

/**
 * @author mk
 *
 */
public class GameStateMyMove implements GameState
{
    
    private GameManager manager;
    private boolean moveSent = false;

	/**
	 * Constructs a new State object.
	 * @param manager GameManager managing the game play.
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
    
    public void nextTurn() { manager.setState(new GameStateOpponentsMove(manager)); }

	/**
	 * Empty method, not used in this state.
	 */
	@Override
	public void remove(int x, int y) { }

	/**
	 * Empty method, not used in this state.
	 */
	@Override
	public void endMove(Point coords, boolean isAdding) { }
    
}