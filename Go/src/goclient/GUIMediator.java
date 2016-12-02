/**
 * 
 */
package goclient;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * @author mk
 *
 */
public class GUIMediator extends JFrame
{
    BoardPanel boardPanel;
    OptionsPanel optionsPanel;
    
    public GUIMediator() 
    {
        this.boardPanel = new BoardPanel(this);
        this.optionsPanel = new OptionsPanel(this);
 
        initComponents();
    }
    
    private void initComponents()
    {
        try { UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");}
        catch (UnsupportedLookAndFeelException ex) { ex.printStackTrace(); }
        catch (IllegalAccessException ex) { ex.printStackTrace(); } 
        catch (InstantiationException ex) { ex.printStackTrace(); }
        catch (ClassNotFoundException ex) { ex.printStackTrace(); }
        UIManager.put("swing.boldMetal", Boolean.FALSE); 
        

   //     this.getContentPane().add(this.boardPanel); 
   //     this.getContentPane().add(this.optionsPanel);
        
        this.setBounds(0, 0, 1024, 708); 
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     
        setLayout(new BorderLayout());
        add(this.boardPanel, BorderLayout.CENTER);
        add(this.optionsPanel, BorderLayout.WEST);
        
        this.setVisible(true); 
    }
    
    

    private static final long serialVersionUID = 1L;
    
}
