/**
 * 
 */
package goserver;

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
   
    
    /* (non-Javadoc)
     * @see goserver.GamePlayState#makeMove(goserver.Player, int, int)
     */
    @Override
    public void makeMove(Player p, int x, int y)
    {
       if (p == gamePlay.getBlack())
       {
          if (gamePlay.checkIfMovePossible(Color.BLACK, x, y))
          {
              gamePlay.putStone(Color.BLACK, x, y);
          };
       }
       else p.sendMessage("NOTYOURTURN");
    }
    
}
