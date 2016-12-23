/**
 * 
 */
package goclient.game.states;

import java.awt.Point;

/**
 * @author mk
 *
 */
public interface GameState
{
    void makeMove(int x, int y);
    void reset();
    void nextTurn();
	void remove(int x, int y);
	void endMove(Point coords, boolean isAdding);
	void sendProposition();
}

