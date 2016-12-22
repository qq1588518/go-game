package goclient.gui;

import java.awt.Point;
import java.util.HashSet;

import goclient.program.ComponentException;

public class DrawingManager 
{
	DrawingMode mode = DrawingMode.SINGLEMODE;
	private GUIMediator mediator;
	private HashSet<Point> deadStones;
	private Point lastClick;
	
	public DrawingManager(GUIMediator mediator)
	{
		this.mediator = mediator;
		deadStones = new HashSet<>();
	}
	public void changeMode(DrawingMode mode) { this.mode = mode; }
	
	public void markAsDead(int x, int y)
	{
		if (mode.equals(DrawingMode.SINGLEMODE)) 
		{
			try {
				if(!mediator.getGameManager().isFieldEmpty(x, y)) deadStones.add(new Point(x, y));
			} catch (ComponentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//lastClick = null;
		}
		else
		{
			if (lastClick == null) lastClick = new Point(x, y);
			else
			{
				int upperLeftX = (lastClick.x > x) ? x : lastClick.x;
				int upperLeftY = (lastClick.y > y) ? y : lastClick.y;
				int width = Math.abs(lastClick.x - x);
				int height = Math.abs(lastClick.y - y);
				HashSet<Point> fields;
				try {
					fields = mediator.getGameManager().getSameColorFieldsInArea(upperLeftX, upperLeftY, width, height);
					if (fields != null)
					{
						deadStones.addAll(fields);
					}
				} catch (ComponentException e) { return; }
				lastClick = null;				
			}
		}
		mediator.getGamePanel().getBoardPanel().repaint();
	}
	
	public void unmarkDead(int x, int y)
	{
		deadStones.remove(new Point(x, y));
	}
	
	public HashSet<Point> getDead()
	{
		return deadStones;
	}
	
	

}
