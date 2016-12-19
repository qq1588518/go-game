/**
 * 
 */
package goserver;

import java.awt.Composite;
import java.awt.CompositeContext;
import java.awt.RenderingHints;
import java.awt.image.ColorModel;
import java.util.HashSet;

/**
 * @author mk
 *
 */
public class Field implements BoardUpdater
{
    private final int x;
    private final int y;
    private FieldType type;
    private Board board;
    
    static final int[] xdirections = {1, 0, -1, 0};
    static final int[] ydirections = {0, 1, 0, -1};
    
    /**
     * 
     */
    public Field(int x, int y, FieldType type, Board board)
    {
        this.x = x;
        this.y = y;
        this.type = type;
        this.board = board;
    }
    
    void setType(FieldType type)
    {
        this.type = type;
    }
    
    FieldType getType()
    {
        return type;
    }
    
    Board getBoard()
    {
        return board;
    }
    
    /* (non-Javadoc)
     * @see goserver.LibertyChecker#checkLiberties()
     */
    @Override
    public int checkLiberties()
    {
        int liberties = 0;
        for (int i = 0; i < 4; i++)
        {
            FieldType t;
            try
            {
               t =  board.getFieldType(x + xdirections[i], y + ydirections[i]);
               if (t == FieldType.EMPTY) liberties++;
            }
            catch(FieldOutOfBoardException e)
            {
                continue;
            }
        }
        return liberties;
    }

    /* (non-Javadoc)
     * @see goserver.BoardUpdater#setEmpty()
     */
    @Override
    public void setEmpty()
    {
        type = FieldType.EMPTY;
    }
    
    public HashSet<Field> getNeighbours()
    {
        HashSet<Field> neigbours = new HashSet<Field>();
        
        for (int i = 0; i < 4; i++)
        {
            Field f =  board.getField(x + xdirections[i], y + ydirections[i]);
            if (f != null) neigbours.add(f);
        }
        return neigbours;
    }
}
