/**
 * 
 */
package goclient;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 * Panel to keep non-resizable BoardPanel in the center.
 *
 */
@SuppressWarnings("serial")
public class GamePanel extends JPanel
{
    private final GUIMediator parent;
    private final BoardPanel board;

    public GamePanel(GUIMediator parent) 
    {
        this.parent = parent;
        this.board = new BoardPanel(this.parent);
        initComponents();
    }

    /**
     * Initialises panel and sets BoardPanel in the right position.
     */
    private void initComponents()
    {
        setPreferredSize(new Dimension(500, 500));
        Box box = new Box(BoxLayout.Y_AXIS);

        box.add(Box.createVerticalGlue());
        box.add(this.board);     
        box.add(Box.createVerticalGlue());

        add(box);
    }
    

    /**
     * Returns board (for communication between GUI components)
     * @return BoardPanel situated on this panel
     */
    public BoardPanel getBoardPanel() { return this.board; }
}
