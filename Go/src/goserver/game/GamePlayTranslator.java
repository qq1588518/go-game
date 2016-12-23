/**
 * 
 */
package goserver.game;

import java.awt.Point;
import java.util.HashMap;
import java.util.HashSet;
import goserver.game.board.Board;
import goserver.game.board.Field;
import goserver.game.board.FieldType;
import goserver.game.board.MoveState;

/**
 * @author mk
 *
 */
public class GamePlayTranslator
{
    Player black;
    Player white;
    private String lastSuggestion;
    private String lastDeadSuggestion;
    private String lastTerritorySuggestion;    
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
    
    public void sendOpponentsMove(Player p) 
    {
		p.sendMessage("OPPOPASS");	
	}
	
	public void sendChooseDead(Player p) 
	{
		p.sendMessage("CHOOSEDEAD");
	}

	public void sendGameStopped(Player p)
	{
		p.sendMessage("GAMESTOPPED");
	}
	
	public String getLastSuggestion()
	{
		return lastSuggestion;
	}
	
	public HashSet<Field> getLastDeadSuggestion(Board b)
	{
		if (lastDeadSuggestion != null)
		{
			HashSet<Field> points = new HashSet<>();
			String input = lastDeadSuggestion;
	        input = input.replaceFirst("DEADSUGGESTION ", "");
	        if (!input.trim().equals("NONE"))
	        {
	            String[] pairs = input.split(" ");
	            for (String string : pairs)
	            {
	                String[] pair = string.split(",");
	                Field p = new Field(Integer.valueOf(pair[0].trim()), Integer.valueOf(pair[1].trim()), FieldType.EMPTY, b);
	                points.add(p);
	            }         	
	        }
			return points;
		}
		return null;
	}
	
	public HashMap<Point, Color> getLastTerritorySuggestion()
	{
    	HashMap<Point, Color> fields = new HashMap<>();
		
		String input = lastTerritorySuggestion;
		String[] inputs = input.replaceFirst("TERRITORYSUGGESTION ", "").split(":");
		
		if (!inputs[0].replaceFirst("BLACK", "").trim().startsWith("NONE"))
		{
	    	String[] blackFields = inputs[0].replaceFirst("BLACK", "").trim().split(" ");
	    	for (String pair : blackFields) 
	    	{
	            String[] coords = pair.split(",");
	            Point p = new Point(Integer.valueOf(coords[0].trim()), Integer.valueOf(coords[1].trim()));
	            fields.put(p, Color.BLACK);     
			}			
		}

		if (!inputs[1].replaceFirst("WHITE", "").trim().startsWith("NONE"))
		{
	    	String[] whiteFields = inputs[1].replaceFirst("WHITE", "").trim().split(" ");
	    	for (String pair : whiteFields) 
	    	{
	            String[] coords = pair.split(",");
	            Point p = new Point(Integer.valueOf(coords[0].trim()), Integer.valueOf(coords[1].trim()));
	            fields.put(p, Color.WHITE);    
	    	}			
		}

    	return fields;
	}

	public void sendChooseTerritory(Player p) 
	{
		p.sendMessage("SETTERRITORY");
	}

	public void sendDeadOK(Player player) 
	{
		String message = lastDeadSuggestion.replaceFirst("DEADSUGGESTION", "DEADOK");
		player.sendMessage(message);
	}

	public void setLastDeadSuggestion(String message) 
	{
		lastDeadSuggestion = message;
		
	}

	public void setLastTerritorySuggestion(String message) 
	{
		lastTerritorySuggestion = message;
	}

	public void sendResults(double black, double white) 
	{
		notifyBoth("THEEND BLACK " + String.valueOf(black) + " : " + "WHITE " + String.valueOf(white));
	}

    
    
}