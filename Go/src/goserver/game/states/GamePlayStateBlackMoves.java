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
public class GamePlayStateBlackMoves implements GamePlayState
{
    private GamePlay gamePlay;


    /**
     * 
     */
    public GamePlayStateBlackMoves(GamePlay gamePlay)
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
       if (p == gamePlay.getBlack())
       {
    	   MoveState moveState = gamePlay.getBoard().checkIfMovePossible(Color.BLACK, x, y);
    	   if (moveState.equals(MoveState.ACCEPTED))
          {
              gamePlay.getBoard().putStone(Color.BLACK, x, y);
              
              HashSet<Field> removed = gamePlay.getBoard().update(new Field(x, y, FieldType.BLACK, gamePlay.getBoard()));    
              gamePlay.getTranslator().confirmMove(p);
              gamePlay.getTranslator().sendOpponentsMove(gamePlay.getWhite(), x, y, removed);
              gamePlay.getTranslator().sendRemovedStones(gamePlay.getBlack(), removed);
              gamePlay.getBoard().removeStones(removed);
              gamePlay.setState(new GamePlayStateWhiteMoves(gamePlay));
          }
    	   else gamePlay.getTranslator().rejectMove(p, moveState);
       }
       else gamePlay.getTranslator().rejectMoveAttempt(p);
    }
    
    
    public boolean makeMove(Player p, boolean lastWasPass) 
    {
		if (p == gamePlay.getBlack())
		{
			if (lastWasPass)
			{
				gamePlay.getTranslator().sendChooseDead(gamePlay.getWhite());
				gamePlay.getTranslator().sendGameStopped(p);
				gamePlay.setState(new GamePlayStateWhiteChoosesDead(gamePlay));
				
			}
			else
			{
				gamePlay.getTranslator().sendOpponentsMove(gamePlay.getWhite());
				gamePlay.setState(new GamePlayStateWhiteMoves(gamePlay));
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
	public void sendProposal(Player player, String message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reachAgreement(Player player) {
		// TODO Auto-generated method stub
		
	}
}