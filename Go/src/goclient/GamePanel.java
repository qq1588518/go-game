/**
 * 
 */
package goclient;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * @author mk
 *
 */
public class GamePanel extends JPanel
{
    private final JFrame parent;
    private final BoardPanel board;

    public GamePanel(JFrame parent) 
    {
        this.parent = parent;
        this.board = new BoardPanel(this.parent);
//        anotherPanel = new AnotherPanel(this.parent);
//
//        this.add(anotherPanel);
        initComponents();
        
    }

    /**
     * 
     */
    private void initComponents()
    {
        setPreferredSize(new Dimension(500, 500));
        drawBoard();
        Box box = new Box(BoxLayout.Y_AXIS);

        box.add(Box.createVerticalGlue());
        box.add(this.board);     
        box.add(Box.createVerticalGlue());

        add(box);
        
        
    }
    
    /**
     * 
     */
    private void drawBoard()
    {
        // TODO Auto-generated method stub
        
    }

    public BoardPanel getBoardPanel(){
        return board;
    }
  
}
