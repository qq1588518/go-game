/**
 * 
 */
package goserver;

import java.util.HashSet;
import java.util.Iterator;

/**
 * Stores an array for go board, handles move possibility and consequences 
 * i.e. updates the board after move.
 * @author mk
 *
 */
public class Board
{
    private Field[][] board;
    private final int size;
    private StoneGroupSet groups;
    
    /**
     * Constructs a new Board of the given size with all the fields empty. 
     * @param n size of the board.
     */
    public Board(int n)
    {
        board = new Field[n][n];
        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < n; j++) board[i][j] = new Field(i, j, FieldType.EMPTY, this);
        }
        this.size = n;
        groups = new StoneGroupSet();
    }
    
    /**
     * TODO: sprawdzanie samobójczych ruchów.
     * głupi pomysł: skopiować planszę, zrobić ruch, sprawdzić konsekwencje, tj. dodany kamień zniknął.
     * @param c
     * @param x
     * @param y
     */
    public boolean checkIfMovePossible(Color c, int x, int y)
    {
    	System.out.println(board[x][y].getType());
    	if (board[x][y].getType().equals(FieldType.EMPTY)) return true;
        return false;
    }
    
    /**
     * Puts a stone of given Colour to given coordinates.
     * @param c Colour of the Stone to put on board.
     * @param x first coordinate of the stone.
     * @param y second coordinate of the stone.
     */
    public void putStone(Color c, int x, int y)
    {
        board[x][y].setType((c == Color.BLACK) ? FieldType.BLACK : FieldType.WHITE);
    }
    
    /**
     * Gets FieldType of the Field of given coordinates.
     * @param x first coordinate of the field.
     * @param y second coordinate of the field.
     * @return FieldType of the Field of given coordinates.
     * @throws FieldOutOfBoardException when given coordinates are out of board.
     */
    FieldType getFieldType(int x, int y) throws FieldOutOfBoardException
    {
        if (!isOnBoard(x, y)) throw new FieldOutOfBoardException("Field would be outside board");
        return board[x][y].getType();
    }
    
    /**
     * Gets the Field of given coordinates.
     * @param x first coordinate of the field.
     * @param y second coordinate of the field.
     * @return Field of given coordinates.
     */
    Field getField(int x, int y)
    {
        if (!isOnBoard(x, y)) return null;
        return board[x][y];
    }
    
    /**
     * Checks if given coordinates are on board.
     * @param x first coordinate to check.
     * @param y second coordinate to check.
     * @return true if (x,y) is on board, false otherwise.
     */
    private boolean isOnBoard(int x, int y)
    {
        if (x < 0 || x >= size || y < 0 || y >= size) return false;
        return true;
    }
    
    /**
     * Updates Board state after adding a new stone.
     * @param lastMove Field changed after a move
     */
    public HashSet<Field> update(Field lastMove)
    {
       return groups.updateGroupsAfterMove(lastMove);
    }
    
    synchronized public void removeStones(HashSet<Field> fields)
    {
    	Iterator<Field> it = fields.iterator();
    	while (it.hasNext())
    	{
    		Field f = it.next();
    		board[f.getX()][f.getY()].setEmpty();
    	}
    }
}
