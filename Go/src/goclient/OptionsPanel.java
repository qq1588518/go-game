/**
 * 
 */
package goclient;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
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
    private JButton surrenderButton;
    private JButton passButton;
    private JPanel buttonPanel;
    
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
        Dimension buttonSize = new Dimension (90, 25);
        Dimension buttonPanelSize = new Dimension(200, 25);
        
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
        
        
        buttonPanel = new JPanel();
        buttonPanel.setVisible(true);
        buttonPanel.setSize(buttonPanelSize);
        buttonPanel.setAlignmentX(CENTER_ALIGNMENT);

        surrenderButton = new JButton("Poddaj sie");
        surrenderButton.setVisible(true);
        surrenderButton.setSize(buttonSize);
        surrenderButton.setMinimumSize(buttonSize);
        surrenderButton.setMaximumSize(buttonSize);
        surrenderButton.setAlignmentX(LEFT_ALIGNMENT);
        buttonPanel.add(surrenderButton);
       
        
        passButton = new JButton("Zpasuj");
        passButton.setVisible(true);
        passButton.setSize(buttonSize);
        passButton.setMinimumSize(buttonSize);
        passButton.setMaximumSize(buttonSize);
        passButton.setAlignmentX(RIGHT_ALIGNMENT);
        buttonPanel.add(passButton);
       
        Dimension gap = new Dimension(300, 30);
        
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        //add(Box.createVerticalGlue());
        add(Box.createRigidArea(gap));
        add(messageArea);
        add(Box.createRigidArea(gap));
        add(statisticsArea);
        add(Box.createRigidArea(gap));
       
        
        add(buttonPanel);
      
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
