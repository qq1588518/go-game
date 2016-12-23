/**
 * 
 */
package goclient.game;

/**
 * @author mk
 *
 */
@SuppressWarnings("serial")
public class WrongCoordsException extends Exception
{
    /**
     * @param message
     */
    public WrongCoordsException(String message)
    {
        super(message);
    }
}
