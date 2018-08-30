package game.board;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a single group of stones on the board
 */
public class StoneGroup implements BoardUpdater {
    private final Set<Field> group;

    /**
     * Creates a new StoneGroup containing given Field
     *
     * @param field Field to add to the created group
     **/
    public StoneGroup(Field field) {
        group = new HashSet<>();
        group.add(field);
        FieldType type = field.getType();
    }

    /**
     * Checks if group contains a given Field
     *
     * @param field Field to check if it is in the group.
     * @return true if group contains a given Field, false otherwise.
     */
    boolean contains(Field field) {
        for (Field f : group) {
            if (f.equals(field)) return true;
        }
        return false;
    }

    /**
     * adds a Field do the group
     *
     * @param field Field to add
     */
    void add(Field field) {
        group.add(field);
    }

    /**
     * Get fields in the group
     *
     * @return HashSet of Fields in the group.
     */
    Set<Field> getFields() {
        return group;
    }


    /* (non-Javadoc)
     * @see goserver.BoardUpdater#checkLiberties()
     */
    @Override
    public int checkLiberties() {
        int liberties = 0;
        for (Field field : group) {
            liberties += field.checkLiberties();
        }
        return liberties;
    }


    /* (non-Javadoc)
     * @see goserver.BoardUpdater#setEmpty()
     */
    @Override
    public void setEmpty() {
        group.forEach(Field::setEmpty);
    }


}