/**
 * 
 */
package goclient;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.sun.javafx.geom.Ellipse2D;

/**
 * @author mk
 *
 */
public class BoardPanel extends JPanel
{
    private final JFrame parent;
    private Point[][] fields;
    
    /**
     * 
     */
    public BoardPanel(JFrame parent)
    {
        this.parent = parent;
        
        initComponents();
    }

    /**
     * 
     */
    private void initComponents()
    {
        Dimension panelSize = new Dimension(500, 500);
        setMaximumSize(panelSize);
        setPreferredSize(panelSize);
        setMinimumSize(panelSize);
        
        setBackground(new Color(220, 179, 92));
        
    }

    @Override
    public void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        drawBoard(g);
    }
    /**
     * 
     */
    private void drawBoard(Graphics g)
    {
        int n = 19;
        fields = new Point[n][n];
        
        int panelSize = this.getWidth();        
        int fieldSize = panelSize / (n + 2);
        
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
        
        Point[] hoshi = new Point[9];
        hoshi[0] = new Point((int)fields[3][3].getX(), (int)fields[3][3].getY());
        hoshi[1] = new Point((int)fields[n-4][3].getX(), (int)fields[n-4][3].getY());
        hoshi[2] = new Point((int)fields[3][n-4].getX(), (int)fields[3][n-4].getY());
        hoshi[3] = new Point((int)fields[n-4][n-4].getX(), (int)fields[n-4][n-4].getY());
        hoshi[4] = new Point((int)fields[n/2][3].getX(), (int)fields[n/2][3].getY());
        hoshi[5] = new Point((int)fields[n-4][n/2].getX(), (int)fields[n-4][n/2].getY());
        hoshi[6] = new Point((int)fields[n/2][n-4].getX(), (int)fields[n/2][n-4].getY());
        hoshi[7] = new Point((int)fields[3][n/2].getX(), (int)fields[3][n/2].getY());
        hoshi[8] = new Point((int)fields[n/2][n/2].getX(), (int)fields[n/2][n/2].getY());
        
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
}
