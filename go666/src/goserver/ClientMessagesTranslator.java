package goserver;

import goclient.EmptyNameException;
import goclient.NameContainsSpaceException;

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
	        try {
				if(game.addPlayer(message.replaceFirst("USERNAME ", ""), clientHandler)) response = "NAMEOK";  
				else response = "NAMETAKEN";
			} catch (NameContainsSpaceException | EmptyNameException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
		    if (game.chooseOpponent(message, clientHandler.getPlayer())) return;
		    else response = "CHOOSEOPPONENTAGAIN " + getList();
		}
		else if (message.startsWith("INVDECLINE")){
			message = message.replaceFirst("INVDECLINE ", "");
			response = "CHOOSEANOTHEROPPONENT " + getList();
		}
		else if (message.startsWith("MOVE"))
		{
		    String[] coords = message.replaceFirst("MOVE ", "").split(" ");
		    if (coords.length == 2)
		    {
		        clientHandler.getPlayer().makeMove(Integer.valueOf(coords[0]),Integer.valueOf(coords[1]));
		        return;
		    }                                                             
		    else if (coords[0].equals("PASS")){
		    	clientHandler.getPlayer().makeMove();
		    }
		}
		else if (message.startsWith("SURRENDER ")){
			message = message.replaceFirst("SURRENDER ", "");
			clientHandler.getPlayer().surrendered(message);
			return;
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
     * Sends invitation from given name, using players clientHandler method.
     * @param player String with name of inviting player.
     */
    public void sendInvitation(String player)
    {
        clientHandler.send("INVITATIONFROM " + player);
    }
    
    /**
     * Sends given message using players clientHandler method.
     * @param message String to send.
     */
    public void sendMessage(String message)
    {
        clientHandler.send(message);
    }

}
