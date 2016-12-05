/**
 * 
 */
package goclient;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;

/**
 * @author mk
 * Nie powinno byæ w gogame packed¿u? /MZ
 */
public class Mouse implements MouseListener
{
    private int x;
    private int y;
    
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
           
          
           parent.getGamePanel().getBoardPanel().addStone(StoneType.BLACK, coords);
           
           
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
