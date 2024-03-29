/**
 * 
 */
package goclient.gui;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import goclient.program.ComponentException;

/**
 * @author mk
 * Manages mouse buttons. Allows mouse to put stones, mark teritories and uncheck stones.
 */
class Mouse implements MouseListener
{
    private final GUIMediator parent;
    
    public Mouse(GUIMediator parent)
    {
        this.parent = parent;
    }
    
    /* (non-Javadoc)
     * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseClicked(MouseEvent e)
    {
       Point coords = e.getPoint();

        handleMouseEvent(e, coords);
    }

    private void handleMouseEvent(MouseEvent e, Point coords) {
        if (e.getButton() == MouseEvent.BUTTON1)
        {
            coords = parent.getGamePanel().getBoardPanel().pullToGrid(coords);
            if (coords != null) try
            {
                parent.getGameManager().makeMove(coords.x, coords.y);
            }
            catch (ComponentException e1)
            {
                System.out.println(e1.getMessage());
            }
        }
        else if (e.getButton() == MouseEvent.BUTTON3)
        {
            coords = parent.getGamePanel().getBoardPanel().pullToGrid(coords);
            if (coords != null) try
            {
                 parent.getGameManager().remove(coords.x, coords.y);
            }
            catch (ComponentException e1)
            {
                System.out.println(e1.getMessage());
            }
        }
    }


    @Override
    public void mousePressed(MouseEvent e) { 
    	Point coords = e.getPoint();
        handleMouseEvent(e, coords);
    }
    
    @Override
    public void mouseReleased(MouseEvent e) 
    {
    	Point coords = e.getPoint();
    	coords = parent.getGamePanel().getBoardPanel().pullToGrid(coords);
    	if (coords != null)
    	{
    		try 
    		{
    			if (e.getButton() == MouseEvent.BUTTON1) parent.getGameManager().getState().endMove(coords, true);
    			else if (e.getButton() == MouseEvent.BUTTON3) parent.getGameManager().getState().endMove(coords, false);
			} catch (ComponentException e1) { System.out.println(e1.getMessage());	}
    	}
	}
    
    @Override
    public void mouseEntered(MouseEvent e) { }
    @Override
    public void mouseExited(MouseEvent e) { }

}