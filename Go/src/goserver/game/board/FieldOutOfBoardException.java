/**
 * 
 */
package goserver.game.board;

/**
 * @author mk
 *
 */
public class FieldOutOfBoardException extends Exception
{
       /**
     * 
     */
    public FieldOutOfBoardException(String message)
    {
        super(message);
    }


}