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
          if (gamePlay.getBoard().checkIfMovePossible(Color.WHITE, x, y))
          {
              gamePlay.getBoard().putStone(Color.WHITE, x, y);
                          
              HashSet<Field> removed = gamePlay.getBoard().update(new Field(x, y, FieldType.WHITE, gamePlay.getBoard()));    
              gamePlay.getTranslator().confirmMove(p);
              gamePlay.getTranslator().sendOpponentsMove(gamePlay.getBlack(), x, y, removed);
              gamePlay.getTranslator().sendRemovedStones(gamePlay.getWhite(), removed);
              gamePlay.getBoard().removeStones(removed);
              gamePlay.setState(new GamePlayStateBlackMoves(gamePlay));
          }
          else gamePlay.getTranslator().rejectMove(p);
       }
       else gamePlay.getTranslator().rejectMoveAttempt(p);
    }
    
}
