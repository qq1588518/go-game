/**
 * 
 */
package goserver.game.states;

import java.util.HashSet;

import goserver.game.Color;
import goserver.game.GamePlay;
import goserver.game.Player;
import goserver.game.board.Field;
import goserver.game.board.FieldType;
import goserver.game.board.MoveState;

/**
 * @author mk
 *
 */
public class GamePlayStateWhiteMoves implements GamePlayState
{
    private GamePlay gamePlay;
    /**
     * @param gamePlay
     */
    public GamePlayStateWhiteMoves(GamePlay gamePlay)
    {
        this.gamePlay = gamePlay;
    }

    /* 
     * (non-Javadoc)
     * @see goserver.GamePlayState#makeMove(goserver.Player, int, int)
     */
    @Override
  synchronized public void makeMove(Player p, int x, int y)
    {
       if (p == gamePlay.getWhite())
       {
    	  MoveState moveState = gamePlay.getBoard().checkIfMovePossible(Color.WHITE, x, y);
    	  if (moveState.equals(MoveState.ACCEPTED))
          {
              gamePlay.getBoard().putStone(Color.WHITE, x, y);
                          
              HashSet<Field> removed = gamePlay.getBoard().update(new Field(x, y, FieldType.WHITE, gamePlay.getBoard()));    
              gamePlay.getTranslator().confirmMove(p);
              gamePlay.getTranslator().sendOpponentsMove(gamePlay.getBlack(), x, y, removed);
              gamePlay.getTranslator().sendRemovedStones(gamePlay.getWhite(), removed);
              gamePlay.getBoard().removeStones(removed);
              gamePlay.setState(new GamePlayStateBlackMoves(gamePlay));
          }
          else gamePlay.getTranslator().rejectMove(p, moveState);
       }
       else gamePlay.getTranslator().rejectMoveAttempt(p);
    }
    
    public boolean makeMove(Player p, boolean lastWasPass) 
    {
		if (p == gamePlay.getWhite())
		{
			if (lastWasPass)
			{
				gamePlay.getTranslator().sendChooseDead(gamePlay.getBlack());
				gamePlay.getTranslator().sendGameStopped(p);
				gamePlay.setState(new GamePlayStateBlackChoosesDead(gamePlay));	
			}
			else
			{
				gamePlay.getTranslator().sendOpponentsMove(gamePlay.getBlack());
				gamePlay.setState(new GamePlayStateBlackMoves(gamePlay));
			}
			return true;
		}
		else
		{
			gamePlay.getTranslator().rejectMoveAttempt(p);
			return false;
		}
	
	}

	@Override
	public void sendSuggestion(Player player, String message) {	}

	@Override
	public void reachAgreement(Player player) { }
    
}