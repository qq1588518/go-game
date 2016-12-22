package goserver.game.board;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Stores all StoneGroups and handles updating them.
 * @author mk
 */
public class StoneGroupSet
{
    private HashSet<StoneGroup> groups;
    
    /**
     * Constructs a new empty StoneGroupSet.
     */
    public StoneGroupSet()
    {
        groups = new HashSet<StoneGroup>();
    }
    
    /**
     * Finds the StoneGroup to which given Field belongs.
     * @param field Field to find the group.
     * @return StoneGroup to which given Field belongs
     */
    private StoneGroup find(Field field)
    {
        for (StoneGroup group : groups)
        {
            if (group.contains(field)) return group;
        }
        return null;
    }
    
    /***
     * Updates StoneGroups after a move.
     * Adds a newly put stone to a group and handles consequences of a move, i.e. captured stones.
     * @param lastMove Field where a stone was recently put.
     * @return HashSet of Fields made empty after lastMove.
     */
    public HashSet<Field> updateGroupsAfterMove(Field lastMove)
    {
        addFieldToGroup(lastMove);
        return handleConsequences(lastMove);
    }
    
    /**
     * Handles consequences of a move, i.e. removes groups of stones with no liberties.
     * @param lastMove Field, which was lastly changed.
     * @return HashSet of Fields from which stones were removed.
     */
    private HashSet<Field> handleConsequences(Field lastMove)
    {
        HashSet<Field> neigbours = lastMove.getNeighbours();
        HashSet<StoneGroup> ngroups = new HashSet<StoneGroup>();
        HashSet<Field> removed = new HashSet<Field>();
        
        for (Field neighbour : neigbours)
        {
            if(!neighbour.getType().equals(lastMove.getType()) && !neighbour.getType().equals(FieldType.EMPTY))
            {
                StoneGroup g = find(neighbour);
                if (g != null) ngroups.add(g);              
            }
        }
        for (StoneGroup stoneGroup : ngroups)
        {
            if (stoneGroup.checkLiberties() == 0)
            {
                removed.addAll(stoneGroup.getFields());
                stoneGroup.setEmpty();
                groups.remove(stoneGroup);
            }
        }
        return removed;
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
            if(neighbour.getType().equals(field.getType()))
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

/**
 * Checks if the ko rule would be violated if a move to given Field was made.
 * @param move Field where a player wants to put a stone.
 * @return Field which would be captured after given move or null if none or more than one field would be captured.
 */
	public Field checkForKo(Field move) 
	{
        HashSet<Field> neighbours = move.getNeighbours();
        HashSet<StoneGroup> suspected = new HashSet<StoneGroup>();
        HashSet<Field> toRemove = new HashSet<Field>();
        
        for (Field neighbour : neighbours)
        {
            if(!neighbour.getType().equals(move.getType()) && !neighbour.getType().equals(FieldType.EMPTY))
            {
                StoneGroup g = find(neighbour);
                if (g != null && g.getFields().size() == 1) suspected.add(g);              
            }
        }
        for (StoneGroup stoneGroup : suspected)
        {
        	if (stoneGroup.checkLiberties() == 1)
            {
                toRemove.addAll(stoneGroup.getFields());
            }
        }

        if(toRemove != null && toRemove.size() == 1)
        {
        	Iterator<Field> it = toRemove.iterator();
        	Field f = it.next();
        	return f;
        }
        return null;
	}

	/**
	 * Checks if move to given Field would cause immediate capture of added stone.
	 * @param move Field on which player wants to put a stone.
	 * @return true if move is suicidal, false otherwise.
	 */
	public boolean checkIfSuicidal(Field move) 
	{
		if (move.checkLiberties() > 0) return false;
		
		HashSet<Field> neighbours = move.getNeighbours();
		HashSet<StoneGroup> myGroups = new HashSet<>();
		HashMap<StoneGroup, Integer> opponentsGroups = new HashMap<>();
		int myColorNeighbours = 0;
		int otherColorNeighbours = 0;
		
        for (Field neighbour : neighbours)
        {
            if(neighbour.getType().equals(move.getType()))
            {
                myColorNeighbours++;
            	StoneGroup g = find(neighbour);
                if (g != null) myGroups.add(g);              
            }
            else if (!neighbour.getType().equals(FieldType.EMPTY))
            {
            	StoneGroup g = find(neighbour);
            	if (opponentsGroups.containsKey(g)) opponentsGroups.put(g, opponentsGroups.get(g) + 1);
                if (g != null) opponentsGroups.put(g, 1); 
            }
        }
        int myGroupsLiberties = 0;
        for (StoneGroup g : myGroups) myGroupsLiberties += g.checkLiberties();
		if (myGroupsLiberties == myColorNeighbours)
		{
			//int oppoGroupsLiberties = 0;
			for (StoneGroup oppoGroup : opponentsGroups.keySet()) 
			{
				if (oppoGroup.checkLiberties() <= opponentsGroups.get(oppoGroup)) return false;
			}
			return true;
		}
		return false;
	}
}