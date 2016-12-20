/**
 * 
 */
package goclient;

import java.awt.Point;
import java.util.Vector;

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
        else if (input.startsWith("WRONGMOVE")) manager.resetMyMove();
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