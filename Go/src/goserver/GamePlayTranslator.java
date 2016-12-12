/**
 * 
 */
package goserver;

/**
 * @author mk
 *
 */
public class GamePlayTranslator
{
    Player black;
    Player white;
    
    /**
     * 
     */
    public GamePlayTranslator(Player black, Player white)
    {
       this.black = black;
       this.white = white;
    }
    
    public void notifyBoth(String message)
    {
        
    }
    
    public void notifyGameStart()
    {
        black.sendMessage("GAMESTART BLACK");
        white.sendMessage("GAMESTART WHITE");
    }


    public void confirmMove(Player p)
    {
        p.sendMessage("MOVEOK");
    }

    public void rejectMove(Player p)
    {
        p.sendMessage("WRONGMOVE");
    }

    public void rejectMoveAttempt(Player p)
    {
        p.sendMessage("CANNOTMOVENOW");
    }

    /**
     * @param white2
     * @param x
     * @param y
     */
    public void sendOpponentsMove(Player p, int x, int y)
    {
        p.sendMessage("OPPOMOVE " + String.valueOf(x) + " " + String.valueOf(y));
    } 
}