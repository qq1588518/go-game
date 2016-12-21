/**
 * 
 */
package goserver.game.states;

import goserver.game.Player;

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
}
