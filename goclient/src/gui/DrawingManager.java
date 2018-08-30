package goclient.gui;

import java.awt.Point;
import java.util.HashSet;
import java.util.Set;

import goclient.program.ComponentException;

public class DrawingManager 
{
	private final GUIMediator mediator;
	private Set<Point> deadStones;
	private Set<Point> myTerritory;
	private Set<Point> opponentsTerritory;
	public DrawingMode drawingMode = DrawingMode.MYTERRITORY;
	
	/**
	 * Constructs a new DrawingManager object 
	 * @param mediator GUIMediator which handles drawing objects on screen.
	 */
	public DrawingManager(GUIMediator mediator)
	{
		this.mediator = mediator;
		deadStones = new HashSet<>();
		myTerritory = new HashSet<>();
		opponentsTerritory = new HashSet<>();
	}
	
	/**
	 * Chooses appropriate set of Points according to given DrawingMode
	 * @param mode DrawingMode for choosing set.
	 * @return chosen HashSet of Points
	 */
	private Set<Point> chooseSet(DrawingMode mode)
	{
		if(mode.equals(DrawingMode.MYTERRITORY)) return myTerritory;
		else if (mode.equals(DrawingMode.OPPONENTSTERITORY)) return opponentsTerritory;
		else return deadStones;
	}
	
	private Set<Point> chooseExcludingSet(DrawingMode mode)
	{
		if(mode.equals(DrawingMode.OPPONENTSTERITORY)) return myTerritory;
		else if (mode.equals(DrawingMode.MYTERRITORY)) return opponentsTerritory;
		else return new HashSet<Point>();
	}
	
	/**
	 * Add given coordinates to a set depending on current DrawingMode 
	 * @param x first coordinate of the point.
	 * @param y second coordinate of the point.
	 * @param mode DrawingMode used for choosing proper set.
	 */
	public void mark(int x, int y, DrawingMode mode)
	{
		Set<Point> set = chooseSet(mode);
		Set<Point> excludingSet = chooseExcludingSet(mode);
		try 
		{
			if(mediator.getGameManager().isFieldTypeAppropriate(x, y, mode) &&
                    !excludingSet.contains(new Point(x, y))) {
                set.add(new Point(x, y));
            }
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
		Set<Point> set = chooseSet(mode);
		set.remove(new Point(x, y));
		mediator.getGamePanel().getBoardPanel().repaint();
	}
	
	/**
	 * Add group of points to a set chosen depending on current DrawingMode 
	 * @param first first coordinate of the point.
	 * @param last second coordinate of the point.
	 * @param mode DrawingMode used for choosing proper set.
	 */
	public void markGroup(Point first, Point last, DrawingMode mode) 
	{
		Set<Point> set = chooseSet(mode);
		Set<Point> excludingSet = chooseExcludingSet(mode);
		int upperLeftX = (last.x > first.x) ? first.x : last.x;
		int upperLeftY = (last.y > first.y) ? first.y : last.y;
		int width = Math.abs(last.x - first.x);
		int height = Math.abs(last.y - first.y);
		HashSet<Point> fields;
		try 
		{
			fields = mediator.getGameManager().getAppropriateFieldsInArea(upperLeftX, upperLeftY, width, height, mode);
			if (fields != null) 
			{
				for (Point point : fields) 
				{
					if(!excludingSet.contains(point)) set.add(point);
				}
			}
		} 
		catch (ComponentException e) { System.out.println(e.getMessage());}
		repaint();
	}
	
	/**
	 * Remove set of given points from a set chosen depending on current DrawingMode 
	 * @param first first coordinate of the point.
	 * @param last second coordinate of the point.
	 * @param mode DrawingMode used for choosing proper set.
	 */
	public void unmarkGroup(Point first, Point last, DrawingMode mode) 
	{
		Set<Point> set;
		if(mode.equals(DrawingMode.MYTERRITORY)) set = myTerritory;
		else if (mode.equals(DrawingMode.OPPONENTSTERITORY)) set = opponentsTerritory;
		else set = deadStones;
		
		int upperLeftX = (last.x > first.x) ? first.x : last.x;
		int upperLeftY = (last.y > first.y) ? first.y : last.y;
		int width = Math.abs(last.x - first.x);
		int height = Math.abs(last.y - first.y);
		Set<Point> fields;
		try 
		{
			fields = mediator.getGameManager().getAppropriateFieldsInArea(upperLeftX, upperLeftY, width, height, mode);
			if (fields != null) set.removeAll(fields);
		} 
		catch (ComponentException e) { System.out.println(e.getMessage());}
		repaint();
	}
	
	public Set<Point> getDead()
	{
		return deadStones;
	}
	
	private void removeAllDeadSigns()
	{
		deadStones.clear();
		repaint();
	}
	
	private void removeAllTerritoriesSigns()
	{
		myTerritory.clear();
		opponentsTerritory.clear();
		repaint();
	}
	
	/**
	 * removes all signs, made in setting territories and choosing dead stones states
	 */
	public void removeAllSigns()
	{
		removeAllTerritoriesSigns();
		removeAllDeadSigns();
	}

	public Set<Point> getMyTerritory()
	{
		return myTerritory;
	}

	public Set<Point> getOpponentsTerritory()
	{
		return opponentsTerritory;
	}
	
	public void setMyTerritory(Set<Point> points)
	{
		myTerritory = points;
		repaint();
	}
	
	public void setOpponentsTerritory(Set<Point> points)
	{
		opponentsTerritory = points;
		repaint();
	}
	
	public void setDeadStones(Set<Point> points)
	{
		deadStones = points;
		repaint();
	}
	
	private void repaint()
	{
		mediator.getGamePanel().getBoardPanel().repaint();
	}
	

}