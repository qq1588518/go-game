/**
 * 
 */
package goclient;

/**
 * @author mk
 *
 */
enum StoneType
{
    BLACK, WHITE;
    
    StoneType other()
    {
        return StoneType.values()[ 1 - this.ordinal() ];
    }
}
