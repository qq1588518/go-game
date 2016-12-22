package goclient.game.states;

import java.awt.Point;

import goclient.game.GameManager;

public class GameStateIAmSettingTerritories implements GameState 
{

	private GameManager manager;

	public GameStateIAmSettingTerritories(GameManager manager) 
	{
		this.manager = manager;
	}
	
	@Override
	public void makeMove(int x, int y) 
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}

	@Override
	public void nextTurn() {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void endMove(Point coords, boolean isAdding) {
		// TODO Auto-generated method stub
		
	}


}
