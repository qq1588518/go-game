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
}
