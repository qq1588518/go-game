/**
 * 
 */
package goserver;

import java.util.HashSet;

/**
 * Represents a single field on the go board and handles it state.
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
     * Creates a new Field of given coordinates and FieldType on given Board
     * @param x first coordinate of the field.
     * @param y second coordinate of the field.
     * @param type FieldType of the field.
     * @param board Board to which the field belongs.
     */
    public Field(int x, int y, FieldType type, Board board)
    {
        this.x = x;
        this.y = y;
        this.type = type;
        this.board = board;
    }
    
    /**
     * Changes the FieldType of the Field to the given one.
     * @param type FieldType to which the state should be changed.
     */
    void setType(FieldType type)
    {
        this.type = type;
    }
    
    /**
     * Gets the FieldType of the Field
     * @return FieldType of the Field
     */
    FieldType getType()
    {
        return type;
    }
    
    /**
     * Gets the Board to which the Field belongs.
     * @return
     */
    Board getBoard()
    {
        return board;
    }
    
    /* (non-Javadoc)
     * @see goserver.BoardUpdater#checkLiberties()
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
    
    /**
     * Gets four neighbours of the Field.
     * @return HashSet of the adjacent Fields.
     */
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
