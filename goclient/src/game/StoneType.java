/**
 * 
 */
package goclient.game;

/**
 *Stone types: BLACK and WHITE
 */
public enum StoneType
{
    BLACK, WHITE;
    public StoneType other()
    {
        return StoneType.values()[ 1 - this.ordinal() ];
    }
}