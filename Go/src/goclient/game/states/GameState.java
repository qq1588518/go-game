/**
 * 
 */
package goclient.game.states;

/**
 * @author mk
 *
 */
public interface GameState
{
    void makeMove(int x, int y);

    /**
     * 
     */
    void reset();
    void nextTurn();
    void setTeritory();
}
