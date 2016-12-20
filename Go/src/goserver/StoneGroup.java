/**
 * 
 */
package goserver;

import java.util.HashSet;

/**
 * @author mk
 *
 */
public class StoneGroup implements BoardUpdater
{
    private HashSet<Field> group;
    private FieldType type;
    private Board board;

    
    public StoneGroup(Field field)
    {
        group = new HashSet<Field>();
        group.add(field);
        this.type = field.getType();
        this.board = field.getBoard();
    }
    
    boolean contains(Field field)
    {
        for (Field f : group)
        {
           if (f.equals(field)) return true;
        }
        return false;
    }
    
    void add(Field field)
    {
        group.add(field);
    }
    
    void remove(Field field)
    {
        group.remove(field);
    }
    
    HashSet<Field> getFields()
    {
        return group;
    }
    
    FieldType getType()
    {
        return type;
    }
    
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
