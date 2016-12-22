package goclient.game.states;

import java.awt.Point;

import goclient.game.GameManager;

public class GameStateIAmChoosingDead implements GameState {

	private GameManager manager;
	private Point last = null;

	public GameStateIAmChoosingDead(GameManager manager)
    {
       this.manager = manager;
    }
	
	@Override
	public void makeMove(int x, int y) 
	{
		manager.getDrawingManager().markAsDead(x, y);
		last = new Point(x,y);
	}
	
	@Override
	public void remove(int x, int y) 
	{
		manager.getDrawingManager().unmarkDead(x, y);
		last = new Point(x,y);
	}

	
	@Override
	public void reset() { }
	
	@Override
	public void nextTurn() { }

	@Override
	public void endMove(Point coords, boolean isAdding)
	{
		if (last != null) 
		{
			if (isAdding) manager.getDrawingManager().markGroupAsDead(last, coords);
			else  manager.getDrawingManager().unmarkDeadGroup(last, coords);
		}
		last = null;
	}




}
