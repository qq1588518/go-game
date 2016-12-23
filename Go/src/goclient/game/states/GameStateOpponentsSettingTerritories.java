/**
 * 
 */
package goclient.game.states;

import java.awt.Point;

import goclient.game.GameManager;

/**
 * Game state, when opponent is setting territories. Part of the State design pattern.
 */
public class GameStateOpponentsSettingTerritories implements GameState 
{

	private GameManager manager;

	/**
	 * Constructs a new State object.
	 * @param manager GameManager managing the game play.
	 */
	public GameStateOpponentsSettingTerritories(GameManager manager) 
	{
		this.manager = manager;
	}

	/**
	 * Changes state to GameStateIAmSettingTerritories.
	 */
	@Override
	public void nextTurn() 
	{ 
		manager.getMediator().getOptionsPanel().activateTeritoriesBox();
		manager.setState(new GameStateIAmSettingTerritories(manager)); 
	}

	/**
	 * Empty method, not used in this state.
	 */
	@Override
	public void makeMove(int x, int y) {}
	
	/**
	 * Empty method, not used in this state.
	 */
	@Override
	public void reset() {}
	
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
