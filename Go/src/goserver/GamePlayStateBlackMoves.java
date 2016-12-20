/**
 * 
 */
package goserver;

import java.util.HashSet;

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
    public void makeMove(Player p, int x, int y)
    {
       if (p == gamePlay.getBlack())
       {
          if (gamePlay.getBoard().checkIfMovePossible(Color.BLACK, x, y))
          {
              gamePlay.getBoard().putStone(Color.BLACK, x, y);
              gamePlay.getTranslator().confirmMove(p);
              gamePlay.getTranslator().sendOpponentsMove(gamePlay.getWhite(), x, y);
              HashSet<Field> removed = gamePlay.getBoard().update(new Field(x, y, FieldType.BLACK, gamePlay.getBoard()));    
              
              gamePlay.getTranslator().sendRemovedStones(removed);
              gamePlay.setState(new GamePlayStateWhiteMoves(gamePlay));
          }
          else gamePlay.getTranslator().rejectMove(p);
       }
       else gamePlay.getTranslator().rejectMoveAttempt(p);
    }
    
}
