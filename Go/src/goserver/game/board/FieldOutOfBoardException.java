/**
 * 
 */
package goserver.game.board;

/**
 *Thrown when field is outside board
 */
public class FieldOutOfBoardException extends Exception
{

	private static final long serialVersionUID = -4601678451613349852L;

    public FieldOutOfBoardException(String message)
    {
        super(message);
    }


}
