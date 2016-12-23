/**
 * 
 */
package goclient.game;

import java.awt.Point;
import java.util.HashSet;
import java.util.Vector;

import goclient.game.states.GameStateIAmChoosingDead;
import goclient.game.states.GameStateIAmSettingTerritories;
import goclient.game.states.GameStateMyMove;
import goclient.game.states.GameStateOpponentsChoosingDead;
import goclient.game.states.GameStateOpponentsSettingTerritories;
import goclient.program.ServerTranslator;
import goclient.program.SocketClient;
import goserver.game.board.Field;

/**
 * @author mk
 *
 */
public class GameServerTranslator extends ServerTranslator
{
    private GameManager manager;
    private SocketClient socket;

    /**
     * 
     */
    public GameServerTranslator(GameManager manager, SocketClient socket) 
    {
       this.manager = manager;
       this.socket = socket;
    }
    
    public void processIncommingMessage(String input)
    {
        if (input.startsWith("SERVERMESSAGE: "))
        {
            input = input.replaceFirst("SERVERMESSAGE: ", "[Server]: ");
            manager.displayMessage(input);
        }
        else if (input.startsWith("MOVEOK"))
        {
            manager.addMyMove();
        }
        else if (input.startsWith("WRONGMOVE"))
        {
        	String[] inputs = input.split(" ");
        	if (inputs.length > 1) manager.resetMyMove(inputs[1]);
        	else manager.resetMyMove(null);
        }
        else if (input.startsWith("OPPOMOVE"))
        {
        	String[] inputs = input.split(":");
        	inputs[0] = inputs[0].replaceFirst("OPPOMOVE ", "");
            String[] coords = inputs[0].split(",");
            manager.addOpponentsMove(Integer.valueOf(coords[0].trim()), Integer.valueOf(coords[1].trim()));
            if (inputs.length > 1) handleRemoved(inputs[1].trim());
            
        }
        else if (input.startsWith("OPPOPASS")){
        	manager.displayMessage("Your opponent has passed. Now it is your turn\n");
        	manager.setState(new GameStateMyMove(manager));
        	manager.getMediator().getOptionsPanel().activateButtons();
        }
        else if (input.startsWith("YOULOOSE")){
        	manager.getMediator().displayWinSurrender();
        }
        else if(input.trim().startsWith("REMOVED"))
        {
        	handleRemoved(input.trim());
        }
        else if(input.startsWith("CHOOSEDEAD"))
        {
        	manager.displayMessage("Use left mouse button to mark stones as dead and right to unmark them.\n");
        	manager.getMediator().displayDialog("<HTML>You both passed. The game is stopped.\n Now it is time to choose which stones are dead. \n Use left mouse button to mark stones as dead and right to unmark them. \n When you are ready, press Send proposition button</HTML>");
        	manager.setState(new GameStateIAmChoosingDead(manager));
        	manager.getMediator().getOptionsPanel().activateTeritoriesBox(false);
        }
        else if (input.startsWith("GAMESTOPPED"))
        {
        	manager.getMediator().displayDialog("<HTML>You both passed. The game is stopped.\n Now your opponent is choosing which stones are dead. \n  When they is ready, it will be your turn. \n Use left mouse button to mark stones as dead and right to unmark them. \n When you are ready, press Send proposition button</HTML>");
        	manager.setState(new GameStateOpponentsChoosingDead(manager));
        }
        else if (input.startsWith("DEADSUGGESTION"))
        {
        	manager.displayMessage("Opponent suggested dead stones. Now it is your turn.\n");
        	HashSet<Point> dead = new HashSet<>();
        	input = input.replaceFirst("DEADSUGGESTION ", "");
        	if (!input.trim().equals("NONE"))
        	{
        		String[] pairs = input.split(" ");
            	
            	for (String pair : pairs) 
            	{
                    String[] coords = pair.split(",");
                    Point p = new Point(Integer.valueOf(coords[0].trim()), Integer.valueOf(coords[1].trim()));
                    dead.add(p);        
    			}
        	}
        	
        	manager.addDeadStoneSuggestion(dead);
        	manager.setState(new GameStateIAmChoosingDead(manager));
        	manager.getMediator().getOptionsPanel().activateTeritoriesBox(false);
        }
        else if (input.startsWith("SETTERRITORY"))
        {
        	manager.displayMessage("You have reached an agreement on dead stones. Now please suggest territories.\n");
        	manager.getDrawingManager().removeAllSigns();
        	manager.removeStones(manager.getDrawingManager().getDead());
        	manager.setState(new GameStateIAmSettingTerritories(manager));
        	manager.getMediator().getOptionsPanel().activateTeritoriesBox(true);
        }
        else if (input.startsWith("DEADOK"))
        {
        	
        	manager.displayMessage("You have reached an agreement on dead stones. Now your opponent is suggesting territories.\n");
        	HashSet<Point> dead = new HashSet<>();
        	input = input.replaceFirst("DEADOK ", "");
        	if (!input.trim().equals("NONE"))
        	{
        		String[] pairs = input.split(" ");
            	
            	for (String pair : pairs) 
            	{
                    String[] coords = pair.split(",");
                    Point p = new Point(Integer.valueOf(coords[0].trim()), Integer.valueOf(coords[1].trim()));
                    dead.add(p);        
    			}
        	}
        	manager.getDrawingManager().removeAllSigns();
        	manager.removeStones(dead);
        	manager.setState(new GameStateOpponentsSettingTerritories(manager));
        	manager.getMediator().getOptionsPanel().disactivateTeritoriesBox(false);
        	
        }
        else if (input.startsWith("TERRITORYSUGGESTION"))
        {
        	String[] inputs = input.replaceFirst("TERRITORYSUGGESTION ", "").split(":");
        	String[] myFields;
        	String[] theirFields;
        	if(manager.myColor.equals(StoneType.BLACK))
        	{
        		myFields = inputs[0].replaceFirst("BLACK", "").trim().split(" ");
            	theirFields = inputs[1].replaceFirst("WHITE", "").trim().split(" ");	
        	}
        	else
        	{
        		theirFields = inputs[0].replaceFirst("BLACK", "").trim().split(" ");
            	myFields = inputs[1].replaceFirst("WHITE", "").trim().split(" ");	
        	}
        	
        	HashSet<Point> myF = new HashSet<>();
        	for (String pair : myFields) 
        	{
                String[] coords = pair.split(",");
                Point p = new Point(Integer.valueOf(coords[0].trim()), Integer.valueOf(coords[1].trim()));
                myF.add(p);     
			}
        	HashSet<Point> theirF = new HashSet<>();
        	for (String pair : theirFields) 
        	{
                String[] coords = pair.split(",");
                Point p = new Point(Integer.valueOf(coords[0].trim()), Integer.valueOf(coords[1].trim()));
                theirF.add(p);     
			}
        	manager.addTerritorySuggestion(myF, theirF);
        }
        else if (input.startsWith("RESUMEGAME"))
        {
        	manager.displayMessage("Game resumed. Your move.");
        	manager.getMediator().displayDialog("<HTML>Your opponent requested resumption of the game. Your move. </html>");
        	manager.resumeGame(manager.myColor);
        }
        else if (input.startsWith("THE END"))
        {
        	// TODO: zastanowić się, co tutaj, jak już będą statystyki.
        }
        else System.out.println("Unknown system command");
    }

    /**
     * @param x
     * @param y
     */
    public void sendMove(int x, int y)
    {
        socket.send("MOVE " + String.valueOf(x) + " " + String.valueOf(y));
    }
    
    public void sendSurrender(){
    	
    	socket.send("SURRENDER " + manager.myColor);
    }


    public void sendPassMove() 
    {
		socket.send("MOVE PASS");
    }
	
	private void handleRemoved(String input)
	{
        input = input.replaceFirst("REMOVED ", "");
        HashSet<Point> points = new HashSet<Point>();
        System.out.println(input);
        if (!input.trim().equals("NONE"))
        {
            String[] pairs = input.split(" ");
            for (String string : pairs)
            {
                String[] pair = string.split(",");
                Point p = new Point(Integer.valueOf(pair[0].trim()), Integer.valueOf(pair[1].trim()));
                points.add(p);
            }         	
        }

        manager.removeStones(points);
	}

	public void sendTerritories(HashSet<Point> myTeritory, HashSet<Point> opponentsTeritory) 
	{
		StringBuilder message = new StringBuilder("TERRITORIES ");
		message.append(manager.myColor );
		message.append(createFieldsList(myTeritory));
		message.append(":" + manager.myColor.other());
		message.append(createFieldsList(opponentsTeritory));
		socket.send(message.toString());
	}
	
	public void sendDead(HashSet<Point> dead) 
	{
		StringBuilder message = new StringBuilder("DEADSUGGESTION ");
		message.append(createFieldsList(dead));
		socket.send(message.toString());
	} 
	
    private String createFieldsList(HashSet<Point> fields)
    {
    	StringBuilder message = new StringBuilder();
    	if(fields != null && !fields.isEmpty())
        {
            for (Point p : fields)
            {
                message.append(String.valueOf(p.x) + "," + String.valueOf(p.y) + " ");
            }       
        }
    	else message.append("NONE");
    	return message.toString();   
    }

	public void sendAcceptance() 
	{
		socket.send("ACCEPT");
	}



}
