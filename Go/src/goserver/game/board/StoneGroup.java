/**
 * 
 */
package goserver.game.board;

import java.util.HashSet;

/**
 * Represents a single group of stones on the board
 */
public class StoneGroup implements BoardUpdater
{
    private HashSet<Field> group;
    private FieldType type;
    private Board board;

    /**
     * Creates a new StoneGroup containing given Field
     * @param field Field to add to the created group
    **/
    public StoneGroup(Field field)
    {
        group = new HashSet<Field>();
        group.add(field);
        this.type = field.getType();
        this.board = field.getBoard();
    }
    
    /**
     * Checks if group contains a given Field
     * @param field Field to check if it is in the group.
     * @return true if group contains a given Field, false otherwise.
     */
    boolean contains(Field field)
    {
        for (Field f : group)
        {
           if (f.equals(field)) return true;
        }
        return false;
    }
    
    /**
     * adds a Field do the group
     * @param field Field to add
     */
    void add(Field field)
    {
        group.add(field);
    }
    
    /**
     * Removes given field from the group
     * @param field Field to remove
     */
    void remove(Field field)
    {
        group.remove(field);
    }
    
    /**
     * Get fields in the group
     * @return HashSet of Fields in the group.
     */
    HashSet<Field> getFields()
    {
        return group;
    }
    
    /**
     * Get type of fields in this group.
     * @return FieldType of fields in this group.
     */
    FieldType getType()
    {
        return type;
    }
    
    /**
     * Change type of fields in this group
     * @param type new type.
     */
    void changeType(FieldType type)
    {
        this.type = type;
    }


    /* (non-Javadoc)
     * @see goserver.BoardUpdater#checkLiberties()
     */
    @Override
    public int checkLiberties()
    {
        int liberties = 0;
        for (Field field : group)
        {
           liberties += field.checkLiberties();
        }
        return liberties;
    }


    /* (non-Javadoc)
     * @see goserver.BoardUpdater#setEmpty()
     */
    @Override
    public void setEmpty()
    {
        for (Field field : group)
        {
            field.setEmpty();
        }
    }
    

}