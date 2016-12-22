/**
 * 
 */
package goclient.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import goclient.game.states.GameStateIAmChoosingDead;
import goclient.game.states.GameStateIAmSettingTerritories;
import goclient.program.ComponentException;

/**
 * Side panel of the frame, showing game info
 */
@SuppressWarnings("serial")
public class OptionsPanel extends JPanel implements ActionListener
{
    private final GUIMediator parent;
    
    private JScrollPane scroll;
    private JTextArea messageArea;
    private JTextArea statisticsArea;
    private JButton passButton;
    private JButton surrenderButton;
    private JButton sendProposisionButton;
    private JButton requestResumingButton;
    private JButton acceptProposisionButton;
    
    
    private JButton teritoriesButton; //for debug only
    
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
        Dimension gap = new Dimension(300, 15);
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
        //messageArea.setMaximumSize(textAreaSize);
        //messageArea.setPreferredSize(textAreaSize);
        messageArea.setAlignmentX(CENTER_ALIGNMENT);
        messageArea.setLineWrap(true);
        scroll = new JScrollPane(messageArea);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

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
        passButton.addActionListener(this);
        
        surrenderButton = new JButton("SURRENDER");
        surrenderButton.setSize(buttonSize);
        surrenderButton.setMinimumSize(buttonSize);
        surrenderButton.setMaximumSize(buttonSize);
        surrenderButton.setPreferredSize(buttonSize);
        surrenderButton.addActionListener(this);
        
        Box buttonBox = new Box(BoxLayout.LINE_AXIS);
        buttonBox.add(passButton);
        buttonBox.add(Box.createRigidArea(new Dimension(20, 15)));
        buttonBox.add(surrenderButton);

        //add(Box.createVerticalGlue());
        add(Box.createRigidArea(gap));
        add(scroll);
        add(Box.createRigidArea(gap));
        add(buttonBox);
        add(Box.createRigidArea(gap));
        add(statisticsArea);
        
        sendProposisionButton = new JButton("Send proposition");
        acceptProposisionButton = new JButton("Accept proposition");
        requestResumingButton = new JButton("Request resuming the game");
        
        Dimension tbuttonSize = new Dimension(250, 30);
        sendProposisionButton.setSize(tbuttonSize);
        sendProposisionButton.setMinimumSize(tbuttonSize);
        sendProposisionButton.setMaximumSize(tbuttonSize);
        sendProposisionButton.setPreferredSize(tbuttonSize);
        sendProposisionButton.addActionListener(this);
        sendProposisionButton.setEnabled(false);
        
        acceptProposisionButton.setSize(tbuttonSize);
        acceptProposisionButton.setMinimumSize(tbuttonSize);
        acceptProposisionButton.setMaximumSize(tbuttonSize);
        acceptProposisionButton.setPreferredSize(tbuttonSize);
        acceptProposisionButton.addActionListener(this);
        acceptProposisionButton.setEnabled(false);
        
        requestResumingButton.setSize(tbuttonSize);
        requestResumingButton.setMinimumSize(tbuttonSize);
        requestResumingButton.setMaximumSize(tbuttonSize);
        requestResumingButton.setPreferredSize(tbuttonSize);
        requestResumingButton.addActionListener(this);
        requestResumingButton.setEnabled(false);
        
        
        Box teritoriesBox = new Box(BoxLayout.PAGE_AXIS);
        teritoriesBox.add(sendProposisionButton);
        teritoriesBox.add(acceptProposisionButton);
        teritoriesBox.add(requestResumingButton);
        teritoriesBox.setAlignmentX(CENTER_ALIGNMENT);
        add(teritoriesBox);
        
      
        
        /**
         * TODO: usunąć, jak będzie gotowe
         */
        teritoriesButton = new JButton("teritories");
        teritoriesButton.setSize(buttonSize);
        teritoriesButton.setMinimumSize(buttonSize);
        teritoriesButton.setMaximumSize(buttonSize);
        teritoriesButton.setPreferredSize(buttonSize);
        teritoriesButton.addActionListener(this);
        add(teritoriesButton);
    }

    /**
     * @param input
     */
    public void displayMessage(String input)
    {
        messageArea.append(input);
    }
    
    public void displayStatistics(String input)
    {
    	statisticsArea.setText(input);
    }
    
    public void activateTeritoriesBox()
    {
    	sendProposisionButton.setEnabled(true);
    	acceptProposisionButton.setEnabled(true);
    	requestResumingButton.setEnabled(true);
    }
    
    public void activateButtons()
    {
    	surrenderButton.setEnabled(true);
    	passButton.setEnabled(true);
    }
    
    /**
     * TODO: włączyć przyciski dopiero po rozpoczęciu gry, bo sypie wyjątkami.
     */
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
		if(arg0.getSource().equals(teritoriesButton)){
			try {
				parent.getGameManager().setState(new GameStateIAmSettingTerritories(parent.getGameManager()));
				activateTeritoriesBox();
			} catch (ComponentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
