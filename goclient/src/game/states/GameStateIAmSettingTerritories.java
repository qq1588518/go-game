package goclient.game.states;

import java.awt.Point;

import goclient.game.GameManager;
/**
 * 
 * @author mk
 * 
 * State of game, when you set your teritory. Part of the State design pattern.
 *
 */
public class GameStateIAmSettingTerritories implements GameState 
{
	private final GameManager manager;
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

	/**
	 * Empty method, not used in this state.
	 */
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
		if (!alreadySent ) manager.getTranslator().sendTerritories(manager.getDrawingManager().getMyTerritory(),
												manager.getDrawingManager().getOpponentsTerritory());
		alreadySent = true;
	}
}