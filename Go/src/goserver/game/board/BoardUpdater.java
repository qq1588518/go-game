/**
 * 
 */
package goserver.game.board;

/**
 * Interface for composite pattern to handle updates of stones and group of stones.
 * @author mk
 */
public interface BoardUpdater
{
    /**
     * Checks how many liberties the group/field has.
     * @return number of liberties.
     */
    public int checkLiberties();
    
    /**
     * Sets FieldType of group/field to EMPTY.
     */
    public void setEmpty();
}
