/**
 * 
 */
package goclient.game;

/**
 * @author mk
 *Stone types: BLACK and WHITE
 */
public enum StoneType
{
    BLACK, WHITE;
    /**
     * allows us to get other stone type when we have each one
     */
    public StoneType other()
    {
        return StoneType.values()[ 1 - this.ordinal() ];
    }
}
