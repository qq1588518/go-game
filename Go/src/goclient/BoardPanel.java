/**
 * 
 */
package goclient;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * Panel representing game board. Handles drawing board and stones on screen
 */
@SuppressWarnings("serial")
public class BoardPanel extends JPanel
{
    private final GUIMediator parent;
    private int n;
    private int fieldSize;
    private int stoneRadius;
    private Point[][] fields;
    private Point[] hoshi;
    private Vector<Stone> stones;
    
    private BufferedImage blackStone;
    private BufferedImage whiteStone;
    
    
    /**
     * Initialises a new Board Panel and sets its parent (mediator) frame.
     */
    public BoardPanel(GUIMediator parent)
    {
        this.parent = parent;
        stones = new Vector<Stone>();
        initComponents();
    }

    /**
     * TODO: coś zrobić z wyjątkiem wyrzucanym jak brak obrazka 
     * 
     * Initialises components of the BoardPanel.
     */
    private void initComponents()
    {
        Dimension panelSize = new Dimension(500, 500);
        setMaximumSize(panelSize);
        setPreferredSize(panelSize);
        setMinimumSize(panelSize);
        setSize(panelSize);

        setBackground(new Color(220, 179, 92));
        createBoard();
        
         
        
        try
        {
            blackStone = ImageIO.read(this.getClass().getResource("black.png"));
            whiteStone = ImageIO.read(this.getClass().getResource("white.png"));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Paints board and stones
     */
    @Override
    public void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        drawBoard(g);
        drawStone(g);
    }
    
    /**
     * Calculates positions of fields and hoshi on the board.
     */
    private void createBoard()
    {
        n = 19;
        fields = new Point[n][n];
        
        int panelSize = getWidth();  
        this.fieldSize = panelSize / (n + 2);
        
        int xPosition = 7 * fieldSize / 4;
        for (int x = 0; x < n; x++)
        {
            int yPosition = 7 * fieldSize / 4;
            for (int y = 0; y < n; y++)
            {
                fields[x][y] = new Point(xPosition, yPosition);
                yPosition += fieldSize;                   
            }
            xPosition += fieldSize;
        }
        
        hoshi = new Point[9];
        hoshi[0] = new Point((int)fields[3][3].getX(), (int)fields[3][3].getY());
        hoshi[1] = new Point((int)fields[n-4][3].getX(), (int)fields[n-4][3].getY());
        hoshi[2] = new Point((int)fields[3][n-4].getX(), (int)fields[3][n-4].getY());
        hoshi[3] = new Point((int)fields[n-4][n-4].getX(), (int)fields[n-4][n-4].getY());
        hoshi[4] = new Point((int)fields[n/2][3].getX(), (int)fields[n/2][3].getY());
        hoshi[5] = new Point((int)fields[n-4][n/2].getX(), (int)fields[n-4][n/2].getY());
        hoshi[6] = new Point((int)fields[n/2][n-4].getX(), (int)fields[n/2][n-4].getY());
        hoshi[7] = new Point((int)fields[3][n/2].getX(), (int)fields[3][n/2].getY());
        hoshi[8] = new Point((int)fields[n/2][n/2].getX(), (int)fields[n/2][n/2].getY());
        
        stoneRadius = fieldSize / 2;
    }
    
    /**
     * Draws board on screen using given Graphics object.
     */
    private void drawBoard(Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g;
        
        g2d.setPaint(Color.black);
        char column = 'A';        
        for (int i = 0; i < n; i++)
        {
            g.drawLine((int)fields[i][0].getX(), 
                       (int)fields[i][0].getY() - fieldSize / 2, 
                       (int)fields[i][n-1].getX(),
                       (int)fields[i][n-1].getY() + fieldSize / 2);
            g2d.drawString(String.valueOf(column), 
                          (int)fields[i][0].getX() - fieldSize / 4, 
                          (int)fields[i][0].getY() - fieldSize );
            g2d.drawString(String.valueOf(column), 
                          (int)fields[i][n-1].getX() - fieldSize / 4, 
                          (int)fields[i][n-1].getY() + (5 * fieldSize) / 4);
            column++;
        }
        
        int radius = fieldSize / 5;
        
        int row = n;
        for (int i = 0; i < n; i++)
        {
            g.drawLine((int)fields[0][i].getX() - fieldSize / 2, 
                       (int)fields[0][i].getY(), 
                       (int)fields[n-1][i].getX() + fieldSize / 2,
                       (int)fields[n-1][i].getY());
            g2d.drawString(String.valueOf(row), 
                          (int)fields[0][i].getX() - fieldSize * 3/2, 
                          (int)fields[0][i].getY() + fieldSize / 4);
            g2d.drawString(String.valueOf(row), 
                          (int)fields[n-1][i].getX() + fieldSize, 
                          (int)fields[n-1][i].getY() + fieldSize / 4);
            row--;
        }
        
        for (Point point : hoshi)
        {
            g.fillOval(point.x - radius, point.y - radius, 2 * radius, 2 * radius);
        } 
    }

    /**
     * TODO: zmienić algorytm skalowania na jakiś lepszy 
     * 
     * Draw images representing Stones on board
     * @param g Graphics object to handle drawing
     */
    private void drawStone(Graphics g)
    {        
        for (Stone stone : stones)
        {
            BufferedImage image = (stone.type == StoneType.BLACK) ? blackStone : whiteStone;
            g.drawImage(image, 
                        fields[stone.getX()][stone.getY()].x - stoneRadius, 
                        fields[stone.getX()][stone.getY()].y - stoneRadius, 
                        stoneRadius * 2, stoneRadius * 2, null);
        }  
    }
    
    /**
     * TODO: to powinna chyba być publiczna funkcja, wołana z zewnątrz, 
     * żeby mieć pole, na którym chcemy postawić kamień - i dalej sprawdzać, 
     * czy możemy go tam postawić.
     * 
     * Calculates point on grid which is closest to given one. 
     * If given point is further than precision allows, returns null.
     * @param Point to pull to grid
     * @return Point on grid closest to given point or null if given point doesn't match any.
     */
    public Point pullToGrid(Point p)
    {
        double x = p.getX();
        double y = p.getY();

        int x0 = fields[0][0].x;
        int y0 = fields[0][0].y;
        
        int xn = fields[n-1][n-1].x;
        int yn = fields[n-1][n-1].y;
        int xsize = xn-x0 + fieldSize;
        int ysize = yn-y0 + fieldSize;
       
        double gridX= (x - x0) / xsize * n;
        double gridY= (y - y0) / ysize * n;
       
        int gridYrounded = (int)Math.round(gridY);
        int gridXrounded = (int)Math.round(gridX);
        
        double deltaX = gridX - gridXrounded;
        double deltaY = gridY - gridYrounded;
    
        double precision = 0.25;
       
        if (deltaX > precision || deltaY > precision) return null;
    
        return new Point(gridXrounded, gridYrounded);
    }
    
    
    /**
     * TODO: ostatecznie tu pewnie nie powinno być sprawdzania, czy jest nullem.
     * Ta fcja wołana powinna być tylko dla kamieni, o których wiemy, że są dobre 
     * i można je postawić. 
     * 
     * Adds a new Stone to Vector of Stones to draw on board.
     * @param stoneType color of Stone
     * @param p Point with coords on grid
     */
    public void addStone(StoneType stoneType, int x, int y) throws WrongCoordsException
    {
        if (x >= 0 && x < n && y >= 0 && y < n) stones.add(new Stone(x, y, stoneType));
        else throw new WrongCoordsException("Chosen coordinates do not exist on board.");
        repaint();
    }


}
