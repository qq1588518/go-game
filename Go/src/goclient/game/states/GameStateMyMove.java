/**
 * 
 */
package goclient.game.states;

import java.awt.Point;

import goclient.game.GameManager;

/**
 * @author mk
 * State when you can make move. Part of the State design pattern.
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
        if (!moveSent)
        {
        	if(x == -1 && y == -1)
        	{
        		manager.sendMove(x, y);
        		moveSent = true;
        		nextTurn();
        	}
        	else if( manager.checkIfMovePossible(x,y)) 
	        {
	            manager.sendMove(x, y); 
	            moveSent = true;
	            manager.saveWaitingMove(x,y);
	        }
        }
    }
    
    public void reset() { moveSent = false; }
    
    public void nextTurn() 
    { 
    	manager.setState(new GameStateOpponentsMove(manager)); 
    	manager.getMediator().getOptionsPanel().disactivateButtons(false);
    }

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
	/**
	 * Empty method, not used in this state.
	 */
	@Override
	public void sendProposal() {
		
		
	}
    
}