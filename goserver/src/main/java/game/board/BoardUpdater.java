package game.board;

/**
 * Interface for composite pattern to handle updates of stones and group of stones.
 *
 * @author mk
 */
interface BoardUpdater {
    /**
     * Checks how many liberties the group/field has.
     *
     * @return number of liberties.
     */
    int checkLiberties();

    /**
     * Sets FieldType of group/field to EMPTY.
     */
    void setEmpty();
}
