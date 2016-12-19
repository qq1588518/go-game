/**
 * 
 */
package goserver;

import java.util.HashSet;
import java.util.Iterator;

/**
 * Stores all StoneGroups and handles updating them.
 * @author mk
 *
 */
public class StoneGroupSet
{
    private HashSet<StoneGroup> groups;
    
    /**
     * 
     */
    public StoneGroupSet()
    {
        groups = new HashSet<StoneGroup>();
    }
    
    
    private StoneGroup find(Field field)
    {
        for (StoneGroup group : groups)
        {
            if (group.contains(field)) return group;
        }
        return null;
    }

    
    /***
     * 
     * @param lastMove
     */
    public void updateGroupsAfterMove(Field lastMove)
    {
        
    }
    
    /***
     * Creates a new StoneGroup with given Field and updates set of groups.
     * @param field
     */
    private void createNewGroup(Field field)
    {
        StoneGroup g = new StoneGroup(field);
        groups.add(g);
    }
    
    /**
     * Copies all fields from source to destination and updates set of groups.
     * @param destination StoneGroup to which fields will be copied.
     * @param source StoneGroup from which fields will be copied.
     * @return StoneGroup which is given destination with new elements added from source.
     */
    private StoneGroup joinGroups(StoneGroup destination, StoneGroup source)
    {
        destination.getFields().addAll(source.getFields());
        groups.remove(source);
        source = null;
        return destination;
    }
    
    /***
     * Adds a field to adjacent StoneGroup. 
     * If given field should belong to more than one group, joins the group. 
     * If there are no adjacent groups, creates a new group for given field.
     * @param field Field to add to StoneGroup
     */
    public void addFieldToGroup(Field field)
    {
        HashSet<Field> neigbours = field.getNeighbours();
        HashSet<StoneGroup> ngroups = new HashSet<StoneGroup>();

        for (Field neighbour : neigbours)
        {
            if(neighbour.getType() == field.getType())
            {
                StoneGroup g = find(neighbour);
                if (g != null) ngroups.add(g);              
            }
        }
        
        if(ngroups.isEmpty()) createNewGroup(field);
        else
        {
            Iterator<StoneGroup> it = ngroups.iterator();
            StoneGroup g = it.next();
            g.add(field);
            while(it.hasNext())
            {
               StoneGroup h = it.next();
               g = joinGroups(g, h);
            }
        }
    }
}
