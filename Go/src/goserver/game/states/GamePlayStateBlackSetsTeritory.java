/**
 * 
 */
package goserver.game.states;

import java.awt.Point;
import java.util.HashMap;

import goserver.game.Color;
import goserver.game.GamePlay;
import goserver.game.Player;

/**
 * @author mk
 *
 */
public class GamePlayStateBlackSetsTeritory implements GamePlayState
{

    private GamePlay gamePlay;

	public GamePlayStateBlackSetsTeritory(GamePlay gamePlay) {
		this.gamePlay = gamePlay;
	}

	/* (non-Javadoc)
     * @see goserver.GamePlayState#makeMove(goserver.Player, int, int)
     */
    @Override
    public void makeMove(Player p, int x, int y)
    {
        // TODO Auto-generated method stub
        
    }
    
    public void makeMove(Player player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean makeMove(Player player, boolean wasPassed) {
		return false;
		
	}

	@Override
	public void sendProposal(Player player, String message) 
	{
		if (player == gamePlay.getBlack() && message.startsWith("TERRITORYSUGGESTION"))
		{
			gamePlay.getTranslator().setLastTerritorySuggestion(message);
			gamePlay.getWhite().sendMessage(message);
			gamePlay.setState(new GamePlayStateWhiteSetsTeritory(gamePlay));
		}
	}

	@Override
	public void reachAgreement(Player player) 
	{
		if (player == gamePlay.getBlack())
		{
			HashMap<Point,Color> territories = gamePlay.getTranslator().getLastTerritorySuggestion();
			if(territories != null)
			{
				gamePlay.getBoard().setTerritories(territories);
				gamePlay.setState(new GamePlayStateGameEnd());
				double[] results = gamePlay.calculateResults();
				gamePlay.getTranslator().sendResults(results[0], results[1]);
			}
		}
	}
    
}