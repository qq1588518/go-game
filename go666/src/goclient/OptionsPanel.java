/**
 * 
 */
package goclient;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * TODO: Pewnie jakieś przyciski typu: pas, poddaj się, statystki rozgrywki
 * Side panel of the frame, showing game info
 */
@SuppressWarnings("serial")
public class OptionsPanel extends JPanel implements ActionListener
{
    private final GUIMediator parent;
    
    private JTextArea messageArea;
    private JTextArea statisticsArea;
    private JButton passButton;
    private JButton surrenderButton;
    
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
        Dimension gap = new Dimension(300, 30);
        Dimension textAreaSize = new Dimension(280, 150);
        Dimension buttonSize = new Dimension(120, 30);
        
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        
        setMaximumSize(panelSize);
        setPreferredSize(panelSize);
        setMinimumSize(panelSize);
        
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
        
        passButton = new JButton("PASS");
        passButton.setSize(buttonSize);
        passButton.setMinimumSize(buttonSize);
        passButton.setMaximumSize(buttonSize);
        passButton.setPreferredSize(buttonSize);
        
        
        surrenderButton = new JButton("SURRENDER");
        surrenderButton.setSize(buttonSize);
        surrenderButton.setMinimumSize(buttonSize);
        surrenderButton.setMaximumSize(buttonSize);
        surrenderButton.setPreferredSize(buttonSize);
       
        
        Box buttonBox = new Box(BoxLayout.LINE_AXIS);
        buttonBox.add(passButton);
        buttonBox.add(Box.createRigidArea(new Dimension(30, 30)));
        buttonBox.add(surrenderButton);

        //add(Box.createVerticalGlue());
        add(Box.createRigidArea(gap));
        add(messageArea);
        add(Box.createRigidArea(gap));
        add(buttonBox);
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
    
    public JButton getPassButton(){
    	return passButton;
    }
    
    public JButton getSurrenderButton(){
    	return surrenderButton;
    }
    
    @Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource().equals(surrenderButton)){
			try {
				//parent.getGameManager().getMediator().displayLooseSurrender();
				parent.getGameManager().sendWhiteFlag();
				parent.displayLooseSurrender();
			} catch (ComponentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(arg0.getSource().equals(passButton)){
			try {
				
				parent.getGameManager().missTurn();
			} catch (ComponentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
