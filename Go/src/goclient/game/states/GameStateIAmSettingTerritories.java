package goclient.game.states;

import java.awt.Point;

import goclient.game.GameManager;
import goclient.gui.DrawingMode;

public class GameStateIAmSettingTerritories implements GameState 
{
	private GameManager manager;
	public DrawingMode  mode = DrawingMode.MYTERITORY;
	private Point last = null;

	public GameStateIAmSettingTerritories(GameManager manager) 
	{
		this.manager = manager;
	}
	
	@Override
	public void makeMove(int x, int y) 
	{
		manager.getDrawingManager().mark(x, y, DrawingMode.MYTERITORY);
		last = new Point(x,y);
	}
	
	@Override
	public void remove(int x, int y) 
	{
		manager.getDrawingManager().unmark(x, y, DrawingMode.MYTERITORY);
		last = new Point(x,y);
	}

	@Override
	public void endMove(Point coords, boolean isAdding)
	{
		if (last != null) 
		{
			if (isAdding) manager.getDrawingManager().markGroup(last, coords, DrawingMode.MYTERITORY);
			else  manager.getDrawingManager().unmarkGroup(last, coords, DrawingMode.MYTERITORY);
		}
		last = null;
	}

	@Override
	public void reset() { }
	
	@Override
	public void nextTurn() { }


}
