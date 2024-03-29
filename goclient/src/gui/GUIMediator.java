 /**
 * 
 */
package goclient.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import goclient.game.GameManager;
import goclient.program.ComponentException;
import goclient.program.ProgramManager;

/**
 * Main frame of the program and also the Mediator between GUI components.
 * Holds all methods to show our GUI components.
 *
 */
@SuppressWarnings("serial")
public class GUIMediator extends JFrame
{
    private final ProgramManager programManager;
    private GamePanel gamePanel;
    private OptionsPanel optionsPanel;
    private GameManager gameManager = null;
    private PlayerList playerList;
    private final ChooseNameDialog chooseNameDialog;
    
    
    /**
     * Constructs a new GUIMediator frame.
     * @param programManager parent of GUI Mediator
     */
    public GUIMediator(ProgramManager programManager) 
    {
        this.gamePanel = new GamePanel(this);
        this.optionsPanel = new OptionsPanel(this);
        
        this.programManager = programManager;
        
        this.chooseNameDialog = new ChooseNameDialog(this);

        initComponents();
    }

    /**
     * Initializes frame components and sets its properties.
     */
    private void initComponents()
    {
        try { UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");}
        catch (UnsupportedLookAndFeelException ex) { ex.printStackTrace(); }
        catch (IllegalAccessException ex) { ex.printStackTrace(); } 
        catch (InstantiationException ex) { ex.printStackTrace(); }
        catch (ClassNotFoundException ex) { ex.printStackTrace(); }
        UIManager.put("swing.boldMetal", Boolean.FALSE); 
        
        chooseNameDialog.setAlwaysOnTop(true);
        Dimension size = new Dimension(870, 570);
        this.setMinimumSize(size );
        java.awt.Image im = Toolkit.getDefaultToolkit().getImage("/Go/images/Realistic_Go_Stone.svg.png");
        this.setIconImage(im);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Go");
     
        setLayout(new BorderLayout());
        add(this.gamePanel, BorderLayout.CENTER);
        add(this.optionsPanel, BorderLayout.WEST);
    }
    
    /**
     *  Returns GamePanel for communication between components.
     *  @return GamePanel 
     */
    public GamePanel getGamePanel()
    {
        return gamePanel;
    }
    
    /**
     *  Returns OptionsPanel for communication between components.
     *  @return OptionsPanel 
     */
    public OptionsPanel getOptionsPanel()
    {
        return optionsPanel;
    }
    
    /**
     *  Returns GameManager for handling game activities
     *  @return GameManager 
     */
    public GameManager getGameManager() throws ComponentException
    {
        if (gameManager == null) throw new ComponentException("GameManager not set");
        return gameManager;
    }
    /**
     * 
     * @return ProgramManager
     */
    public ProgramManager getProgramManager()
    {
        return programManager;
    }

    /**
     * @param input
     */
    public void displayMessage(String input)
    {
        optionsPanel.displayMessage(input);
    }

    /**
     * Show list of players
     * @param list - names of players
     * @param tytul - title of frame, information to user
     */
    public void displayPlayersDialog(String list, String tytul)
    {
        playerList = new PlayerList(list, this, tytul);
    }
    
    /**
     * call method building chooseNameDialog
     * @param text
     */
    
    public void displayChooseNameDialog(String text)
    {
        chooseNameDialog.setText(text);
        chooseNameDialog.setVisible(true);
    }

    /**
     * Displayes invitation from opponent to user, with possibility to agree or decline
     * @param name of enemy who invited you
     */
    public void displayInvitation(String name)
    {
        try
        {
            String text = "<html>You have been invited to play with " + name + ". Do you agree?</html>";
            int choice = JOptionPane.showConfirmDialog(this, text, "Invitation", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION){
            	programManager.respondInvitation(name, true);
            	playerList.setVisible(false);
            }
            else programManager.respondInvitation(name, false);       
        }
        catch (ComponentException e)
        {
            System.out.println(e.getMessage());
        }
    }

    /**
     * @param game
     */
    public void setGameComponents(GameManager game)
    {
        gameManager = game;
        Mouse m = new Mouse(this);
        gamePanel.getBoardPanel().addMouseListener(m);
    }

    /**
     * adds ActionListeners to buttons after game start
     * @param game
     */
    public void setOptionPanelButtonsListeners(GameManager game)
    {
    	gameManager = game;
    	this.getOptionsPanel().getSurrenderButton().addActionListener(this.getOptionsPanel());
    	this.getOptionsPanel().getPassButton().addActionListener(this.getOptionsPanel());
    }

    /**
     * Shows message dialog
     * @param string
     */
    public void displayDialog(String string) 
	{
		JOptionPane.showMessageDialog(this, string);
	}
    
    /**
     * Shows dialog ending the game
     * @param black - colour of player1
     * @param white - colour of player2
     * @param iAmTheWinner - bool holds information who wins
     * @param wasSurrender - if game was surrendered
     */
	public void manageGameEnd(double black, double white, boolean iAmTheWinner, boolean wasSurrender) 
	{
		StringBuilder message = new StringBuilder();
		if(!wasSurrender)
		{
			message.append("<html> Game finished. ");
			if (iAmTheWinner) message.append("Congratulations. You won.\n");
			else  message.append("You lost.\n");
			message.append("Results:\n BLACK: ").append(String.valueOf(black)).append(" WHITE: ").append(String.valueOf(white)).append("\n");
		}
		else
		{
			if (iAmTheWinner) message.append("<html> Your opponent has surrendered.\n Congratulations. You won.\n");
			else message.append("<html> You have surrendered. You lost.\n");
		}
		message.append("What do you want to do next?");
		
		String title = iAmTheWinner ? "You are the winner!" : "You are the looser!";
		
		String[] options = new String[2];
    	options[0] = "New game";
    	options[1] = "Quit";
    	int choice = JOptionPane.showOptionDialog(this, message.toString(), title, 0, JOptionPane.INFORMATION_MESSAGE, null, options, null);
    	System.out.println(choice);
    	if (choice == 1) System.exit(1);
    	else if (choice == 0)
    	{
    		programManager.endGame();
    		try {
    			playerList.setVisible(true);
				programManager.askForList();
			} catch (ComponentException e) { e.printStackTrace(); }
    	}
    	else if(choice == JOptionPane.CLOSED_OPTION) System.exit(0);
	}

	/**
	 * creates new GamePanel and OptionsPanel if player choose "New Game" option
	 */
	public void reset()
	{
		gamePanel = new GamePanel(this);
		optionsPanel = new OptionsPanel(this);
		repaint();
	}

}