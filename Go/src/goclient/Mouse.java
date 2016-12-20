/**
 * 
 */
package goclient;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * @author mk
 *
 */
public class Mouse implements MouseListener
{
    private GUIMediator parent;
    
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
    }
    
    @Override
    public void mousePressed(MouseEvent e) { }
    @Override
    public void mouseReleased(MouseEvent e) { }
    @Override
    public void mouseEntered(MouseEvent e) { }
    @Override
    public void mouseExited(MouseEvent e) { } 
}