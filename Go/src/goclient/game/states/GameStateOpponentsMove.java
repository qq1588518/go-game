/**
 * 
 */
package goclient.game.states;

import java.awt.Point;

import goclient.game.GameManager;

/**
 * @author mk
 * Game state when your opponent makes move. Part of the State design pattern.
 */
public class GameStateOpponentsMove implements GameState
{
    
    private GameManager manager;

	/**
	 * Constructs a new State object.
	 * @param manager GameManager managing the game play.
	 */
    public GameStateOpponentsMove(GameManager manager)
    {
        this.manager = manager;
    }

    @Override
    public void makeMove(int x, int y) { }
    
    public void nextTurn() 
    { 
    	manager.setState(new GameStateMyMove(manager)); 
    	manager.getMediator().getOptionsPanel().activateButtons();
    }
    
    @Override
    public void reset(){ }


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