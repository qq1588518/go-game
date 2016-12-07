 /**
 * 
 */
package goclient;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Main frame of the program and also the Mediator between GUI components
 *
 */
@SuppressWarnings("serial")
public class GUIMediator extends JFrame
{
    private Program parent;
    private GamePanel gamePanel;
    private OptionsPanel optionsPanel;
    private GameManager gameManager;
    private ProgramManager programManager;
    
    private PlayerList playerList;
    private ChooseNameDialog chooseNameDialog;
    
    
    /**
     * Constructs a new GUIMediator frame.
     */
    public GUIMediator(Program program) 
    {
        this.gamePanel = new GamePanel(this);
        this.optionsPanel = new OptionsPanel(this);
        
        this.parent = program;
        
        this.chooseNameDialog = new ChooseNameDialog(this);
        chooseNameDialog.setAlwaysOnTop(true);
 
        
        
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
        
        Dimension size = new Dimension(870, 570);
        this.setMinimumSize(size );
        java.awt.Image im = Toolkit.getDefaultToolkit().getImage("/Go/images/Realistic_Go_Stone.svg.png");
        this.setIconImage(im);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Go");
     
        setLayout(new BorderLayout());
        add(this.gamePanel, BorderLayout.CENTER);
        add(this.optionsPanel, BorderLayout.WEST);
        
        this.setVisible(true); 
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
    public GameManager getGameManager()
    {
        return gameManager;
    }
    
    public ProgramManager getProgramManager()
    {
        return parent.getProgramManager();
    }

    /**
     * @param input
     */
    public void displayMessage(String input)
    {
        optionsPanel.displayMessage(input);
    }

    /**
     * @param list
     */
    public void displayPlayersDialog(String list)
    {
        PlayerList playersDialog = new PlayerList(list, this);
    }
    
    public void displayChooseNameDialog(String text)
    {
        chooseNameDialog.setText(text);
        chooseNameDialog.setVisible(true);
    }
    
    
}
