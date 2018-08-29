package game;

/**
 * Thrown when move is incorrect.
 */
class WrongMoveException extends Exception {

    private static final long serialVersionUID = 1L;


    public WrongMoveException(String message) {
        super(message);
    }

}