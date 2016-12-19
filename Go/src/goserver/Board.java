/**
 * 
 */
package goserver;

/**
 * @author mk
 *
 */
public class Board
{
    private Field[][] board;
    private final int size;
    private StoneGroupSet groups;
    
    /**
     * 
     */
    public Board(int n)
    {
        board = new Field[n][n];
        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < n; j++) board[i][j] = new Field(i, j, FieldType.EMPTY, this);
        }
        this.size = n;
    }
    
    /**
     * TODO: sprawdzanie możliwości ruchu
     * @param black2
     * @param x
     * @param y
     */
    public boolean checkIfMovePossible(Color c, int x, int y)
    {
        if (board[x][y].getType() == FieldType.EMPTY) return true;
        return false;
    }
    
    public void putStone(Color c, int x, int y)
    {
        board[x][y].setType((c == Color.BLACK) ? FieldType.BLACK : FieldType.WHITE);
    }
    
    FieldType getFieldType(int x, int y) throws FieldOutOfBoardException
    {
        if (!isOnBoard(x, y)) throw new FieldOutOfBoardException("Field would be outside board");
        return board[x][y].getType();
    }
    
    Field getField(int x, int y)
    {
        if (!isOnBoard(x, y)) return null;
        return board[x][y];
    }
    
    private boolean isOnBoard(int x, int y)
    {
        if (x < 0 || x >= size || y < 0 || y >= size) return false;
        return true;
    }
    
    public void update(Field lastMove)
    {
        groups.updateGroupsAfterMove(lastMove);
    }
}
