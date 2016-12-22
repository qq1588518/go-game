package goclient.game.states;

import java.awt.Point;

import goclient.game.GameManager;

/**
 * Game state, when opponent is choosing which stones are dead. Part of the State design pattern.
 */
public class GameStateOpponentsChoosingDead implements GameState 
{
	private GameManager manager;

	public GameStateOpponentsChoosingDead(GameManager manager) 
	{
		this.manager = manager;
	}

	/**
	 * Empty method, not used in this state.
	 */
	@Override
	public void makeMove(int x, int y) { }

	/**
	 * Empty method, not used in this state.
	 */
	@Override
	public void reset() { }

	/**
	 * Changes state to GameStateIAmChoosingDead.
	 */
	@Override
	public void nextTurn() { manager.setState(new GameStateIAmChoosingDead(manager)); }

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
}
