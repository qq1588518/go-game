/**
 * 
 */
package goserver;

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
    public void makeMove(Player p, int x, int y)
    {
       if (p == gamePlay.getWhite())
       {
          if (gamePlay.checkIfMovePossible(Color.WHITE, x, y))
          {
              gamePlay.putStone(Color.WHITE, x, y);
              gamePlay.getTranslator().confirmMove(p);
              gamePlay.getTranslator().sendOpponentsMove(gamePlay.getBlack(), x, y);
              gamePlay.setState(new GamePlayStateBlackMoves(gamePlay));
          }
          else gamePlay.getTranslator().rejectMove(p);
       }
       else gamePlay.getTranslator().rejectMoveAttempt(p);
    }
    
}
