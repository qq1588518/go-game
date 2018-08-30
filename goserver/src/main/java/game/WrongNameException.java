package game;


/**
 * Thrown when name is incorrect.
 */
class WrongNameException extends Exception {

    public WrongNameException(String message) {
        super(message);
    }
}