package game.board;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a single field on the go board and handles its state.
 *
 * @author mk
 */
public class Field implements BoardUpdater {
    private static final int[] xDirections = {1, 0, -1, 0};
    private static final int[] yDirections = {0, 1, 0, -1};
    private final int x;
    private final int y;
    private FieldType type;
    private final Board board;

    /**
     * Creates a new Field of given coordinates and FieldType on the given Board
     *
     * @param x     first coordinate of the field.
     * @param y     second coordinate of the field.
     * @param type  FieldType of the field.
     * @param board Board to which the field belongs.
     */
    public Field(int x, int y, FieldType type, Board board) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.board = board;
    }

    /**
     * Gets the FieldType of the Field
     *
     * @return FieldType of the Field
     */
    FieldType getType() {
        return type;
    }

    /**
     * Changes the FieldType of the Field to the given one.
     *
     * @param type FieldType to which the state should be changed.
     */
    void setType(FieldType type) {
        this.type = type;
    }

    /* (non-Javadoc)
     * @see goserver.BoardUpdater#checkLiberties()
     */
    @Override
    public int checkLiberties() {
        int liberties = 0;
        for (int i = 0; i < 4; i++) {
            FieldType t;
            try {
                t = board.getFieldType(x + xDirections[i], y + yDirections[i]);
                if (t == FieldType.EMPTY) liberties++;
            } catch (FieldOutOfBoardException ignored) { }
        }
        return liberties;
    }

    /* (non-Javadoc)
     * @see goserver.BoardUpdater#setEmpty()
     */
    @Override
    public void setEmpty() {
        type = FieldType.EMPTY;
    }

    /**
     * Gets four neighbours of the Field.
     *
     * @return Set of the adjacent Fields.
     */
    public Set<Field> getNeighbours() {
        Set<Field> neighbours = new HashSet<>();

        for (int i = 0; i < 4; i++) {
            Field f = board.getField(x + xDirections[i], y + yDirections[i]);
            if (f != null) neighbours.add(f);
        }
        return neighbours;
    }

    /**
     * Return the x coordinate of the Field.
     *
     * @return x coordinate of the Field.
     */
    public int getX() {
        return x;
    }

    /**
     * Return the y coordinate of the Field.
     *
     * @return y coordinate of the Field.
     */
    public int getY() {
        return y;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + 20 * x;
        result = prime * result + y;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Field other = (Field) obj;
        if (x != other.x)
            return false;
        if (y != other.y)
            return false;
        return type.equals(other.type);
    }
}