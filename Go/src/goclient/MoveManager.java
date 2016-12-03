/**
 * 
 */
package goclient;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Icon;

/**
 * To chyba ma być coś, co komunikuje GUI z resztą klienta. Ale nie jestem pewna. 
 * Albo może klienta z serwerem też trochę? 
 * Wzorowane na tym, ale nie mam koncepcji: 
 * http://blue-walrus.com/2011/10/swing-and-design-patterns-%e2%80%93-part-3-command-pattern/
 *
 */
public class MoveManager extends AbstractAction
{
    GUIMediator mediator;
    
    /**
     * 
     */
    public MoveManager(GUIMediator mediator)
    {
        super("Manage Moves");
        this.mediator = mediator;
        
    }
    
    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
       
    }
    
}
