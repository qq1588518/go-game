package goserver;

public class ClientMessagesTranslator {
	
	ClientHandler clientHandler;
	private Game game;
	
	public ClientMessagesTranslator(ClientHandler clientHandler)
	{
		this.clientHandler = clientHandler;
		game = clientHandler.getGame();
	}
	
	public void processIncommingMessage(String message)
	{
		String response = "";
		if(message.startsWith("CONNECTION OK"))
		{
		    clientHandler.send("SETNAME");
		    return;
		}
		else if(message.startsWith("USERNAME"))
	    {
	        if(game.addPlayer(message.replaceFirst("USERNAME ", ""), clientHandler)) response = "NAMEOK";  
			else response = "NAMETAKEN";
		}	
		else if(message.startsWith("LIST"))
		{
		    response = "LIST " + getList();
		}
		else if (message.startsWith("OPPONENT"))
		{
		    message = message.replaceFirst("OPPONENT ", "");	
		    if (game.inviteOpponent(message, clientHandler.getPlayer().getName())) return;
		    else response = "CHOOSEOPPONENTAGAIN " + getList(); 
		}
		else if (message.startsWith("INVAGREE"))
		{
		    message = message.replaceFirst("INVAGREE ", "");
		    if (game.chooseOpponent(message, clientHandler.getPlayer())) response = "GAMESTART";
		    else response = "CHOOSEOPPONENTAGAIN " + getList();
		}
		else response = "UNKNOWNCOMMAND";
	    clientHandler.send(response);
	}
	
	private String getList()
	{
	    StringBuilder b = new StringBuilder();
	    String myname = clientHandler.getPlayer().getName();
	    for(String name : game.getNotBusyPlayersNames())
	    {
	        if(name != myname) b.append(name + " ");
	    }
	    
	    return b.toString();
	}

    /**
     * @param player
     */
    public void sendInvitation(String player)
    {
        clientHandler.send("INVITATIONFROM " + player);
                        
        //"You have been invited to play with " + player + ". Do you agree?");
    }
    
    public void sendMessage(String message)
    {
        clientHandler.send(message);
    }

}
