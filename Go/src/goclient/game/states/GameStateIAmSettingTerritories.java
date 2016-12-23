package goclient.game.states;

import java.awt.Point;

import goclient.game.GameManager;
import goclient.gui.DrawingMode;

public class GameStateIAmSettingTerritories implements GameState 
{
	private GameManager manager;
	private Point last = null;
	private boolean alreadySent = false;

	/**
	 * Constructs a new State object.
	 * @param manager GameManager managing the game play.
	 */
	public GameStateIAmSettingTerritories(GameManager manager) 
	{
		this.manager = manager;
	}
	
	@Override
	public void makeMove(int x, int y) 
	{
		manager.getDrawingManager().mark(x, y, manager.getDrawingManager().drawingMode);
		last = new Point(x,y);
	}
	
	@Override
	public void remove(int x, int y) 
	{
		manager.getDrawingManager().unmark(x, y, manager.getDrawingManager().drawingMode);
		last = new Point(x,y);
	}

	@Override
	public void endMove(Point coords, boolean isAdding)
	{
		if (last != null) 
		{
			if (isAdding) manager.getDrawingManager().markGroup(last, coords, manager.getDrawingManager().drawingMode);
			else  manager.getDrawingManager().unmarkGroup(last, coords, manager.getDrawingManager().drawingMode);
		}
		last = null;
	}

	@Override
	public void reset() { }
	
	@Override
	public void nextTurn() 
	{ 
		manager.getMediator().getOptionsPanel().disactivateTeritoriesBox(false);
		manager.setState(new GameStateOpponentsSettingTerritories(manager)); 
	}

	@Override
	public void sendProposal() 
	{
		if (!alreadySent ) manager.getTranslator().sendTerritories(manager.getDrawingManager().getMyTeritory(), 
												manager.getDrawingManager().getOpponentsTeritory());
		alreadySent = true;
	}


}
