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
    
    
    public void makeMove(Player p) {
		if (p==gamePlay.getBlack()){
			//gamePlay.getTranslator().confirmMove(p);
			gamePlay.getTranslator().sendOpponentsMove(gamePlay.getWhite());
			gamePlay.setState(new GamePlayStateWhiteMoves(gamePlay));
		}
		
	}
}
