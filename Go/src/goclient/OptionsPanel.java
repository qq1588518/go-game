/**
 * 
 */
package goclient;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.sun.xml.internal.ws.api.Component;

/**
 * TODO: Pewnie jakieś przyciski typu: pas, poddaj się, statystki rozgrywki
 * Side panel of the frame, showing game info
 *
 */
@SuppressWarnings("serial")
public class OptionsPanel extends JPanel
{
    private final GUIMediator parent;
    
    private JTextArea messageArea;
    private JTextArea statisticsArea;
    
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
        
        Dimension textAreaSize = new Dimension(250, 150);
        
        messageArea = new JTextArea();
        messageArea.setEditable(false);
        messageArea.setVisible(true);
        
        messageArea.setSize(textAreaSize);
        messageArea.setMinimumSize(textAreaSize);
        messageArea.setMaximumSize(textAreaSize);
        messageArea.setPreferredSize(textAreaSize);
        messageArea.setAlignmentX(CENTER_ALIGNMENT);
        
        statisticsArea = new JTextArea();
        statisticsArea.setEditable(false);
        statisticsArea.setVisible(true);
        statisticsArea.setSize(textAreaSize);
        statisticsArea.setMinimumSize(textAreaSize);
        statisticsArea.setMaximumSize(textAreaSize);
        statisticsArea.setPreferredSize(textAreaSize);
        statisticsArea.setAlignmentX(CENTER_ALIGNMENT);
        
        Dimension gap = new Dimension(300, 30);
        
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        //add(Box.createVerticalGlue());
        add(Box.createRigidArea(gap));
        add(messageArea);
        add(Box.createRigidArea(gap));
        add(statisticsArea);
        add(Box.createRigidArea(gap));
    }

    /**
     * @param input
     */
    public void displayMessage(String input)
    {
        messageArea.append(input);
    }
}
