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
    public void makeMove(int x, int y)
    {
        System.out.println("Can't make move now. It's not your turn!");
    }
    
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
	public void sendProposition() {
		// TODO Auto-generated method stub
		
	}
    
}
