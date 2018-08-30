package game.board;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Stores all StoneGroups and handles updating them.
 */
class StoneGroupSet {
    private final Set<StoneGroup> groups;

    /**
     * Constructs a new empty StoneGroupSet.
     */
    public StoneGroupSet() {
        groups = new HashSet<>();
    }

    /**
     * Finds the StoneGroup to which given Field belongs.
     *
     * @param field Field to find the group.
     * @return StoneGroup to which given Field belongs
     */
    private StoneGroup find(Field field) {
        for (StoneGroup group : groups) {
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
    public Set<Field> updateGroupsAfterMove(Field lastMove) {
        addFieldToGroup(lastMove);
        return handleConsequences(lastMove);
    }

    /**
     * Handles consequences of a move, i.e. removes groups of stones with no liberties.
     *
     * @param lastMove Field, which was lastly changed.
     * @return HashSet of Fields from which stones were removed.
     */
    private Set<Field> handleConsequences(Field lastMove) {
        Set<Field> neighbours = lastMove.getNeighbours();
        Set<StoneGroup> nGroups = new HashSet<>();
        Set<Field> removed = new HashSet<>();

        for (Field neighbour : neighbours) {
            if (!neighbour.getType().equals(lastMove.getType()) && !neighbour.getType().equals(FieldType.EMPTY)) {
                StoneGroup g = find(neighbour);
                if (g != null) nGroups.add(g);
            }
        }
        for (StoneGroup stoneGroup : nGroups) {
            if (stoneGroup.checkLiberties() == 0) {
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
    private void createNewGroup(Field field) {
        StoneGroup g = new StoneGroup(field);
        groups.add(g);
    }

    /**
     * Copies all fields from source to destination and updates set of groups.
     *
     * @param destination StoneGroup to which fields will be copied.
     * @param source      StoneGroup from which fields will be copied.
     * @return StoneGroup which is given destination with new elements added from source.
     */
    private void joinGroups(StoneGroup destination, StoneGroup source) {
        destination.getFields().addAll(source.getFields());
        groups.remove(source);
    }

    /***
     * Adds a field to adjacent StoneGroup. 
     * If given field should belong to more than one group, joins the group. 
     * If there are no adjacent groups, creates a new group for given field.
     * @param field Field to add to StoneGroup
     */
    private void addFieldToGroup(Field field) {
        Set<Field> neigbours = field.getNeighbours();
        Set<StoneGroup> ngroups = new HashSet<>();

        for (Field neighbour : neigbours) {
            if (neighbour.getType().equals(field.getType())) {
                StoneGroup g = find(neighbour);
                if (g != null) ngroups.add(g);
            }
        }

        if (ngroups.isEmpty()) createNewGroup(field);
        else {
            Iterator<StoneGroup> it = ngroups.iterator();
            StoneGroup g = it.next();
            g.add(field);
            while (it.hasNext()) {
                StoneGroup h = it.next();
                joinGroups(g, h);
            }
        }
    }

    /**
     * Checks if the ko rule would be violated if a move to given Field was made.
     *
     * @param move Field where a player wants to put a stone.
     * @return Field which would be captured after given move or null if none or more than one field would be captured.
     */
    public Field checkForKo(Field move) {
        Set<Field> neighbours = move.getNeighbours();
        Set<StoneGroup> suspected = new HashSet<>();
        Set<Field> toRemove = new HashSet<>();

        for (Field neighbour : neighbours) {
            if (!neighbour.getType().equals(move.getType()) && !neighbour.getType().equals(FieldType.EMPTY)) {
                StoneGroup g = find(neighbour);
                if (g != null && g.getFields().size() == 1) suspected.add(g);
            }
        }
        for (StoneGroup stoneGroup : suspected) {
            if (stoneGroup.checkLiberties() == 1) {
                toRemove.addAll(stoneGroup.getFields());
            }
        }

        if (toRemove.size() == 1) {
            Iterator<Field> it = toRemove.iterator();
            return it.next();
        }
        return null;
    }

    /**
     * Checks if move to given Field would cause immediate capture of added stone.
     *
     * @param move Field on which player wants to put a stone.
     * @return true if move is suicidal, false otherwise.
     */
    public boolean checkIfSuicidal(Field move) {
        if (move.checkLiberties() > 0) return false;

        Set<Field> neighbours = move.getNeighbours();
        Set<StoneGroup> myGroups = new HashSet<>();
        HashMap<StoneGroup, Integer> opponentsGroups = new HashMap<>();
        int myColorNeighbours = 0;

        for (Field neighbour : neighbours) {
            if (neighbour.getType().equals(move.getType())) {
                myColorNeighbours++;
                StoneGroup g = find(neighbour);
                if (g != null) myGroups.add(g);
            } else if (!neighbour.getType().equals(FieldType.EMPTY)) {
                StoneGroup g = find(neighbour);
                if (opponentsGroups.containsKey(g)) opponentsGroups.put(g, opponentsGroups.get(g) + 1);
                if (g != null) opponentsGroups.put(g, 1);
            }
        }
        int myGroupsLiberties = 0;
        for (StoneGroup g : myGroups) myGroupsLiberties += g.checkLiberties();
        if (myGroupsLiberties == myColorNeighbours) {
            for (StoneGroup opponentGroup : opponentsGroups.keySet()) {
                if (opponentGroup.checkLiberties() <= opponentsGroups.get(opponentGroup)) return false;
            }
            return true;
        }
        return false;
    }
}