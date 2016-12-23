package goserver.game;

/**
 * Thrown when move is incorrect.
 *
 */
public class WrongMoveException extends Exception{

	private static final long serialVersionUID = 1L;
	
	
	public WrongMoveException(String message){
		super(message);
	}

}