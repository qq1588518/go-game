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
		}
		else
		{
			
		}
		mediator.getGamePanel().getBoardPanel().repaint();
	}
	
	public void unmarkDead(int x, int y)
	{
		deadStones.remove(new Point(x, y));
		mediator.getGamePanel().getBoardPanel().repaint();
	}
	
	public HashSet<Point> getDead()
	{
		return deadStones;
	}
	public void markGroupAsDead(Point first, Point last) 
	{
		int upperLeftX = (last.x > first.x) ? first.x : last.x;
		int upperLeftY = (last.y > first.y) ? first.y : last.y;
		int width = Math.abs(last.x - first.x);
		int height = Math.abs(last.y - first.y);
		HashSet<Point> fields;
		try {
			fields = mediator.getGameManager().getSameColorFieldsInArea(upperLeftX, upperLeftY, width, height);
			if (fields != null)
			{
				deadStones.addAll(fields);
			}
		} catch (ComponentException e) { return; }
		mediator.getGamePanel().getBoardPanel().repaint();
	}
	
	public void unmarkDeadGroup(Point first, Point last) 
	{
		int upperLeftX = (last.x > first.x) ? first.x : last.x;
		int upperLeftY = (last.y > first.y) ? first.y : last.y;
		int width = Math.abs(last.x - first.x);
		int height = Math.abs(last.y - first.y);
		HashSet<Point> fields;
		try {
			fields = mediator.getGameManager().getSameColorFieldsInArea(upperLeftX, upperLeftY, width, height);
			if (fields != null)
			{
				deadStones.removeAll(fields);
			}
		} catch (ComponentException e) { return; }
		mediator.getGamePanel().getBoardPanel().repaint();
	}
	
	

}
