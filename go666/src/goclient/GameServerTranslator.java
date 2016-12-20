/**
 * 
 */
package goclient;

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
            String[] coords = input.replaceFirst("OPPOMOVE ", "").split(" ");
            manager.addOpponentsMove(Integer.valueOf(coords[0]), Integer.valueOf(coords[1]));
        }
        else if (input.startsWith("YOULOOSE")){
        	manager.getMediator().displayWinSurrender();
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
}
