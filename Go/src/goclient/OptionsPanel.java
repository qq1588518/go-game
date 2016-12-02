/**
 * 
 */
package goclient;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * @author mk
 *
 */
public class OptionsPanel extends JPanel
{
    private final JFrame parent;
    
    public OptionsPanel(JFrame parent)
    {
        this.parent = parent;
        
        initComponents();
    }

    /**
     * 
     */
    private void initComponents()
    {
        //setMinimumSize(new Dimension(500, 500));
        //setBackground(new Color(220, 179, 92));
        

        Dimension panelSize = new Dimension(300, 300);
        setMaximumSize(panelSize);
        setPreferredSize(panelSize);
        setMinimumSize(panelSize);
    }
}
