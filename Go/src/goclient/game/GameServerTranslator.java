/**
 * 
 */
package goclient.game;

import java.awt.Point;
import java.util.HashSet;
import java.util.Vector;

import goclient.game.states.GameStateIAmChoosingDead;
import goclient.game.states.GameStateOpponentsChoosingDead;
import goclient.program.ServerTranslator;
import goclient.program.SocketClient;

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
        else if (input.startsWith("YOULOOSE")){
        	manager.getMediator().displayWinSurrender();
        }
        else if(input.trim().startsWith("REMOVED"))
        {
        	handleRemoved(input.trim());
        }
        else if(input.startsWith("CHOOSEDEAD"))
        {
        	manager.displayMessage("Use left mouse button to mark stones as dead and right to unmark them.");
        	manager.getMediator().displayDialog("<HTML>You both passed. The game is stopped. <br> Now it is time to choose which stones are dead. Use left mouse button to mark stones as dead and right to unmark them. <b> When you are ready, press Send proposition button</HTML>");
        	manager.setState(new GameStateIAmChoosingDead(manager));
        }
        else if (input.startsWith("GAMESTOPPED"))
        {
        	manager.getMediator().displayDialog("<HTML>You both passed. The game is stopped. <br> Now your opponent is choosing which stones are dead. <br>  When they is ready, it will be your turn. Use left mouse button to mark stones as dead and right to unmark them. <b> When you are ready, press Send proposition button</HTML>");
        	manager.setState(new GameStateOpponentsChoosingDead(manager));
        }
        else if (input.startsWith("DEADSUGGESTION"))
        {
        	manager.displayMessage("Opponent suggested dead stones. Now it is your turn.");
        	HashSet<Point> dead = new HashSet<>();
        	String[] pairs = input.replaceFirst("DEADSUGGESTION ", "").split(" ");
        	
        	for (String pair : pairs) 
        	{
                String[] coords = pair.split(",");
                Point p = new Point(Integer.valueOf(coords[0].trim()), Integer.valueOf(coords[1].trim()));
                dead.add(p);        
			}
        	manager.addDeadStoneSuggestion(dead);
        }
        else if (input.startsWith("SETTERRITORY"))
        {
        	manager.displayMessage("You have reached an agreement on dead stones. Now please suggest territories.");
        }
        else if (input.startsWith("TERRITORYSUGGESTION"))
        {
        	String[] inputs = input.replaceFirst("TERRITORYSUGGESTION ", "").split(":");
        	int my = inputs[0].startsWith("YOURS") ? 0 : 1;
        	String[] myFields = inputs[my].replaceFirst("YOURS", "").trim().split(" ");
        	String[] theirFields = inputs[my].replaceFirst("THEIR", "").trim().split(" ");
        	
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

	public void sendPassMove() {
		// TODO Auto-generated method stub
		System.out.println("odebralem pass");
		
		socket.send("PASS " + manager.myColor);
	}
	
	private void handleRemoved(String input)
	{
        input = input.replaceFirst("REMOVED ", "");
        Vector<Point> points = new Vector<Point>();
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
}
