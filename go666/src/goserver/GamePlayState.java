/**
 * 
 */
package goserver;

/**
 * @author mk
 *
 */
public interface GamePlayState
{

    /**
     * @param p
     * @param x
     * @param y
     */
    void makeMove(Player p, int x, int y);

	void makeMove(Player player);
}
