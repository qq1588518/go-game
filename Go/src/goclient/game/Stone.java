/**
 * 
 */
package goclient.game;

import java.awt.Point;

/**
 * Ta klasa jest chyba tylko po to, żeby móc na planszy trzymać kamienie w jednej tablicy...
 *
 */
public class Stone
{
    private int x;
    private int y;
    public final StoneType type;
    
    /**
     * 
     */
    public Stone(int x, int y, StoneType type)
    {
        this.x = x;
        this.y = y;
        this.type = type;
    }
    
    public Stone(Point p, StoneType type)
    {
        this.x = p.x;
        this.y = p.y;
        this.type = type;
    }
    
    public int getX()
    {
        return x;
    }
    
    public int getY()
    {
        return y;
    }
    
    
    
}
