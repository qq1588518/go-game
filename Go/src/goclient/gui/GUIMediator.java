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
import goclient.program.PlayerList;
import goclient.program.ProgramManager;

/**
 * Main frame of the program and also the Mediator between GUI components
 *
 */
@SuppressWarnings("serial")
public class GUIMediator extends JFrame
{
    private ProgramManager programManager;
    private GamePanel gamePanel;
    private OptionsPanel optionsPanel;
    private GameManager gameManager = null;
    private PlayerList playerList;
    private ChooseNameDialog chooseNameDialog;
    
    
    /**
     * Constructs a new GUIMediator frame.
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
     * Initialises frame components and sets its properties.
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
        
   //     this.setVisible(true); 
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
     * @return
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
     * shows list of players
     * @param list
     * @param tytul 
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
     * @param name
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
        gamePanel.getBoardPanel().addMouseListener(new Mouse(this));
    }

    /**
     * @param message
     */
    public void displayError(String message)
    {
        JOptionPane.showMessageDialog(this, message, "Error", ERROR);
    }

    public void displayLooseSurrender() {
		String text = "<html> You surrendered! You lost :c\nWhat to do next?";
    	String[] options = new String[2];
    	options[0] = new String("Nowa gra");
    	options[1] = new String("Zako�cz");
    	int choice = JOptionPane.showOptionDialog(this, text, "Looser", 0, JOptionPane.INFORMATION_MESSAGE, null, options, null);
    	System.out.println(choice);
    	if (choice == 1){
    		System.exit(1);
    	}
    	else if (choice == 0){
    		//TODO
    		//CREATE NEW GAME
    	}
		
	}
    public void displayWinSurrender(){
    	String text = "<html> Enemy surrendered! You win!\nWhat to do next?";
    	String[] options = new String[2];
    	options[0] = new String("Nowa gra");
    	options[1] = new String("Zako�cz");
    	int choice = JOptionPane.showOptionDialog(this, text, "Winner", 0, JOptionPane.INFORMATION_MESSAGE, null, options, null);
    	System.out.println(choice);
    	if (choice == 1){
    		System.exit(1);
    	}
    	else if (choice == 0){
    		//TODO
    		//CREATE NEW GAME
    	}
    }


}
