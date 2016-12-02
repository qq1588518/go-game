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

import javafx.scene.image.Image;

/**
 * @author mk
 *
 */
public class GUIMediator extends JFrame
{
    GamePanel gamePanel;
    OptionsPanel optionsPanel;
    
    public GUIMediator() 
    {
        this.gamePanel = new GamePanel(this);
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
        
       // this.setBounds(0, 0, 1024, 708); 

        Dimension size = new Dimension(870, 570);
        this.setMinimumSize(size );
        //this.setSize(800, 500);
        java.awt.Image im = Toolkit.getDefaultToolkit().getImage("/Go/images/Realistic_Go_Stone.svg.png");
        this.setIconImage(im);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     
        setLayout(new BorderLayout());
        add(this.gamePanel, BorderLayout.CENTER);
        add(this.optionsPanel, BorderLayout.WEST);
       // setResizable(false);
        
        this.setVisible(true); 
    }
    
    

    private static final long serialVersionUID = 1L;
    
}
