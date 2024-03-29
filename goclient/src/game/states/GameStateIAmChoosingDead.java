package goclient.game.states;

import java.awt.Point;

import goclient.game.GameManager;
import goclient.gui.DrawingMode;

public class GameStateIAmChoosingDead implements GameState {

	private final GameManager manager;
	private Point last = null;
	private boolean alreadySent = false;

	/**
	 * Constructs a new State object.
	 * @param manager GameManager managing the game play.
	 */
	public GameStateIAmChoosingDead(GameManager manager)
    {
       this.manager = manager;
    }
	
	@Override
	public void makeMove(int x, int y) 
	{
		manager.getDrawingManager().mark(x, y, DrawingMode.DEAD);
		last = new Point(x,y);
	}
	
	@Override
	public void remove(int x, int y) 
	{
		manager.getDrawingManager().unmark(x, y, DrawingMode.DEAD);
		last = new Point(x,y);
	}

	@Override
	public void endMove(Point coords, boolean isAdding)
	{
		if (last != null) 
		{
			if (isAdding) manager.getDrawingManager().markGroup(last, coords, DrawingMode.DEAD);
			else  manager.getDrawingManager().unmarkGroup(last, coords, DrawingMode.DEAD);
		}
		last = null;
	}

	@Override
	public void reset() { }
	
	@Override
	public void nextTurn() 
	{ 
		manager.getMediator().getOptionsPanel().disactivateTeritoriesBox(false);
		manager.setState(new GameStateOpponentsChoosingDead(manager)); 
	}

	@Override
	public void sendProposal() 
	{
		if (!alreadySent) manager.getTranslator().sendDead(manager.getDrawingManager().getDead());
		alreadySent = true;
	}

}