package game.board;

/**
 * Thrown when field is outside board
 */
class FieldOutOfBoardException extends Exception {

    public FieldOutOfBoardException(String message) {
        super(message);
    }
}
