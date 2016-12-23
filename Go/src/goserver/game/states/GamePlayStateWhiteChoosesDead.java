package goserver.game.states;

import java.util.HashSet;

import goserver.game.GamePlay;
import goserver.game.Player;
import goserver.game.board.Field;

public class GamePlayStateWhiteChoosesDead implements GamePlayState 
{
	private GamePlay gamePlay;

	public GamePlayStateWhiteChoosesDead(GamePlay gamePlay) { this.gamePlay = gamePlay; }

	@Override
	public void makeMove(Player p, int x, int y) {}

	@Override
	public boolean makeMove(Player player, boolean wasPassed) { return false; }

	@Override
	public void sendSuggestion(Player player, String message) 
	{
		if (player == gamePlay.getWhite() && message.startsWith("DEADSUGGESTION"))
		{
			gamePlay.getTranslator().setLastDeadSuggestion(message);
			gamePlay.getBlack().sendMessage(message);
			gamePlay.setState(new GamePlayStateBlackChoosesDead(gamePlay));
		}
	}

	@Override
	public void reachAgreement(Player player) 
	{	
		if (player == gamePlay.getWhite())
		{
			HashSet<Field> toRemove = gamePlay.getTranslator().getLastDeadSuggestion(gamePlay.getBoard());
			if (toRemove != null)
			{
				gamePlay.getBoard().removeStones(toRemove);
				gamePlay.setState(new GamePlayStateBlackSetsTeritory(gamePlay));
				gamePlay.getTranslator().sendChooseTerritory(gamePlay.getBlack());
				gamePlay.getTranslator().sendDeadOK(player);				
			}
		}
	}

}
