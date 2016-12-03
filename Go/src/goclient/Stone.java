/**
 * 
 */
package goclient;

import java.awt.Point;

/**
 * @author mk
 *
 */
class Stone
{
    private int x;
    private int y;
    StoneType type;
    
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
