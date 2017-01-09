/**
 * 
 */
package goclient.game.states;

import java.awt.Point;

/**
 * @author mk
 * GameState before game, you can not do anything in this state. Part of the State design pattern.
 */
public class GameStateNotStartedYet implements GameState
{
	/**
	 * Empty method, not used in this state.
	 */
	@Override
    public void makeMove(int x, int y) {}

	/**
	 * Empty method, not used in this state.
	 */
	@Override
    public void reset() { }
    
	/**
	 * Empty method, not used in this state.
	 */
	@Override
    public void nextTurn() { }

	/**
	 * Empty method, not used in this state.
	 */
	@Override
	public void remove(int x, int y) { }

	/**
	 * Empty method, not used in this state.
	 */
	@Override
	public void endMove(Point coords, boolean isAdding) { }

	/**
	 * Empty method, not used in this state.
	 */
	@Override
	public void sendProposal() { }
}