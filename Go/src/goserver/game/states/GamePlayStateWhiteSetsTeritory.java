/**
 * 
 */
package goserver.game.states;

import java.awt.Paint;
import java.awt.Point;
import java.util.HashMap;
import java.util.HashSet;

import goserver.game.Color;
import goserver.game.GamePlay;
import goserver.game.Player;
import goserver.game.board.Field;

/**
 * @author mk
 *
 */
public class GamePlayStateWhiteSetsTeritory implements GamePlayState
{

    private GamePlay gamePlay;

	public GamePlayStateWhiteSetsTeritory(GamePlay gamePlay) {
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
		if (player == gamePlay.getWhite() && message.startsWith("TERRITORYSUGGESTION"))
		{
			gamePlay.getTranslator().setLastTerritorySuggestion(message);
			gamePlay.getBlack().sendMessage(message);
			gamePlay.setState(new GamePlayStateBlackSetsTeritory(gamePlay));
		}
	}

	@Override
	public void reachAgreement(Player player) 
	{
		if (player == gamePlay.getWhite())
		{
			HashMap<Point,Color> territories = gamePlay.getTranslator().getLastTerritorySuggestion();
			if(territories != null)
			{
				gamePlay.getBoard().setTerritories(territories);
				gamePlay.endGame();
			}
		}
	}
    
    
}