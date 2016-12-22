package goclient.gui;

import java.awt.Point;
import java.util.HashSet;

import goclient.program.ComponentException;

public class DrawingManager 
{
	private GUIMediator mediator;
	private HashSet<Point> deadStones;
	private HashSet<Point> myTeritory;
	private HashSet<Point> opponentsTeritory;
	
	/**
	 * Constructs a new DrawingManager object 
	 * @param mediator GUIMediator which handles drawing objects on screen.
	 */
	public DrawingManager(GUIMediator mediator)
	{
		this.mediator = mediator;
		deadStones = new HashSet<>();
		myTeritory = new HashSet<>();
		opponentsTeritory = new HashSet<>();
	}
	
	/**
	 * Chooses appropriate set of Points according to given DrawingMode
	 * @param mode DrawingMode for choosing set.
	 * @return chosen HashSet of Points
	 */
	private HashSet<Point> chooseSet(DrawingMode mode)
	{
		if(mode.equals(DrawingMode.MYTERITORY)) return myTeritory;
		else if (mode.equals(DrawingMode.OPPONENTSTERITORY)) return opponentsTeritory;
		else return deadStones;
	}
	
	/**
	 * Add given coordinates to a set depending on current DrawingMode 
	 * @param x first coordinate of the point.
	 * @param y second coordinate of the point.
	 * @param mode DrawingMode used for choosing proper set.
	 */
	public void mark(int x, int y, DrawingMode mode)
	{
		HashSet<Point> set = chooseSet(mode);
		try 
		{
			if(mediator.getGameManager().isFieldTypeAppropriate(x, y, mode)) set.add(new Point(x, y));
		} 
		catch (ComponentException e) { System.out.println(e.getMessage());}

		mediator.getGamePanel().getBoardPanel().repaint();
	}
	
	/**
	 * Remove point of given coordinates from a set chosen depending on current DrawingMode 
	 * @param x first coordinate of the point.
	 * @param y second coordinate of the point.
	 * @param mode DrawingMode used for choosing proper set.
	 */
	public void unmark(int x, int y, DrawingMode mode)
	{
		HashSet<Point> set = chooseSet(mode);
		set.remove(new Point(x, y));
		mediator.getGamePanel().getBoardPanel().repaint();
	}
	
	/**
	 * Add group of points to a set chosen depending on current DrawingMode 
	 * @param x first coordinate of the point.
	 * @param y second coordinate of the point.
	 * @param mode DrawingMode used for choosing proper set.
	 */
	public void markGroup(Point first, Point last, DrawingMode mode) 
	{
		HashSet<Point> set = chooseSet(mode);
		
		int upperLeftX = (last.x > first.x) ? first.x : last.x;
		int upperLeftY = (last.y > first.y) ? first.y : last.y;
		int width = Math.abs(last.x - first.x);
		int height = Math.abs(last.y - first.y);
		HashSet<Point> fields;
		try 
		{
			fields = mediator.getGameManager().getAppropriateFieldsInArea(upperLeftX, upperLeftY, width, height, mode);
			if (fields != null) set.addAll(fields);
		} 
		catch (ComponentException e) { System.out.println(e.getMessage());}
		mediator.getGamePanel().getBoardPanel().repaint();
	}
	
	/**
	 * Remove set of given points from a set chosen depending on current DrawingMode 
	 * @param x first coordinate of the point.
	 * @param y second coordinate of the point.
	 * @param mode DrawingMode used for choosing proper set.
	 */
	public void unmarkGroup(Point first, Point last, DrawingMode mode) 
	{
		HashSet<Point> set;
		if(mode.equals(DrawingMode.MYTERITORY)) set = myTeritory;
		else if (mode.equals(DrawingMode.OPPONENTSTERITORY)) set = opponentsTeritory;
		else set = deadStones;
		
		int upperLeftX = (last.x > first.x) ? first.x : last.x;
		int upperLeftY = (last.y > first.y) ? first.y : last.y;
		int width = Math.abs(last.x - first.x);
		int height = Math.abs(last.y - first.y);
		HashSet<Point> fields;
		try 
		{
			fields = mediator.getGameManager().getAppropriateFieldsInArea(upperLeftX, upperLeftY, width, height, mode);
			if (fields != null) set.removeAll(fields);
		} 
		catch (ComponentException e) { System.out.println(e.getMessage());}
		mediator.getGamePanel().getBoardPanel().repaint();
	}
	
	public HashSet<Point> getDead()
	{
		return deadStones;
	}
	
	public void removeAllDeadSigns()
	{
		deadStones.removeAll(deadStones);
		mediator.getGamePanel().getBoardPanel().repaint();
	}
	
	public void removeAllTeritoriesSigns()
	{
		myTeritory.removeAll(myTeritory);
		opponentsTeritory.removeAll(opponentsTeritory);
		mediator.getGamePanel().getBoardPanel().repaint();
	}
	
	public void removeAllSigns()
	{
		removeAllTeritoriesSigns();
		removeAllDeadSigns();
	}

	public HashSet<Point> getMyTeritory() 
	{
		return myTeritory;
	}

	public HashSet<Point> getOpponentsTeritory() 
	{
		return opponentsTeritory;
	}
	
	public void setMyTeritory(HashSet<Point> points)
	{
		myTeritory = points;
	}
	
	public void setOpponentsTeritory(HashSet<Point> points)
	{
		opponentsTeritory = points;
	}
	
	public void setDeadStones(HashSet<Point> points)
	{
		deadStones = points;
	}
	

}
