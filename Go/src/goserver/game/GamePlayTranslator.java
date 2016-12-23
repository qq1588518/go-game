/**
 * 
 */
package goserver.game;

import java.util.HashSet;

import goserver.game.board.Field;
import goserver.game.board.MoveState;

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
        black.sendMessage(message);
        white.sendMessage(message);
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

    public void rejectMove(Player p, MoveState ms)
    {
        String reason = "";
        if(ms.equals(MoveState.REJECTEDKO)) reason = "KO";
        else if(ms.equals(MoveState.REJECTEDSUICIDAL)) reason = "SUICIDAL";
        else if(ms.equals(MoveState.REJECTEDNOTEMPTY)) reason = "NOTEMPTY";
    	p.sendMessage("WRONGMOVE " + reason);
    }

    public void rejectMoveAttempt(Player p)
    {
        p.sendMessage("CANNOTMOVENOW");
    }
    
    public void sendOpponentsMove(Player p, int x, int y)
    {
        p.sendMessage("OPPOMOVE " + String.valueOf(x) + " " + String.valueOf(y));
    }

    /**
     * @param white2
     * @param x
     * @param y
     * @param removed 
     */
   public void sendOpponentsMove(Player p, int x, int y, HashSet<Field> removed)
    {
	   StringBuilder message = new StringBuilder("OPPOMOVE ");
	   message.append(String.valueOf(x) + "," + String.valueOf(y));
	   message.append(":");
	   message.append(createRemovedStonesMessage(removed));
	   p.sendMessage(message.toString());
    }

    /**
     * @param removed
     */
    private String createRemovedStonesMessage(HashSet<Field> removed)
    {
    	StringBuilder message = new StringBuilder("REMOVED ");
    	if(removed != null && !removed.isEmpty())
        {
           
            for (Field field : removed)
            {
                message.append(String.valueOf(field.getX()) + "," + String.valueOf(field.getY()) + " ");
            }       
        }
    	else message.append("NONE");
    	return message.toString();   
    } 
    
    /**
     * @param removed
     */
    public void sendRemovedStones(Player p, HashSet<Field> removed)
    {
    	p.sendMessage(createRemovedStonesMessage(removed));
    } 
    
    public void sendOpponentsMove(Player p) {
		p.sendMessage("OPPOPASS");
		
	}
    
    
}