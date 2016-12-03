/**
 * 
 */
package goclient;

import java.awt.Dimension;

import javax.swing.JPanel;

/**
 * TODO: Pewnie jakieś przyciski typu: pas, poddaj się, statystki rozgrywki
 * Side panel of the frame, showing game info
 *
 */
@SuppressWarnings("serial")
public class OptionsPanel extends JPanel
{
    private final GUIMediator parent;
    
    public OptionsPanel(GUIMediator parent)
    {
        this.parent = parent;
        
        initComponents();
    }

    /**
     * 
     */
    private void initComponents()
    {
        Dimension panelSize = new Dimension(300, 300);
        setMaximumSize(panelSize);
        setPreferredSize(panelSize);
        setMinimumSize(panelSize);
    }
}
