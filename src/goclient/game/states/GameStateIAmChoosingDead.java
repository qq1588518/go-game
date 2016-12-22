package goclient.game.states;

import goclient.game.GameManager;
import goclient.gui.Keyboard;

public class GameStateIAmChoosingDead implements GameState {

	private GameManager manager;
	private boolean prepared = false;

	public GameStateIAmChoosingDead(GameManager manager)
    {
       this.manager = manager;
    }
	
	@Override
	public void makeMove(int x, int y) 
	{
		manager.getDrawingManager().markAsDead(x, y);
	}

	@Override
	public void reset() { }
	
	@Override
	public void nextTurn() { }


}
