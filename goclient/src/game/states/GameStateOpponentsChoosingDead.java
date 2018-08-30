package goclient.game.states;

import java.awt.Point;

import goclient.game.GameManager;

/**
 * Game state, when opponent is choosing which stones are dead. Part of the State design pattern.
 */
public class GameStateOpponentsChoosingDead implements GameState 
{
	private final GameManager manager;

	public GameStateOpponentsChoosingDead(GameManager manager) 
	{
		this.manager = manager;
	}
	
	/**
	 * Changes state to GameStateIAmChoosingDead.
	 */
	@Override
	public void nextTurn() 
	{
		manager.setState(new GameStateIAmChoosingDead(manager)); 
		manager.getMediator().getOptionsPanel().activateTeritoriesBox(false);
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
	 * An empty method, not used in this state.
	 */
	@Override
	public void sendProposal() {	}
}