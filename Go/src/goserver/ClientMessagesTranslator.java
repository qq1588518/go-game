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
		    System.out.println("klient połączył się");
		    clientHandler.send("SETNAME");
		    return;
		}
		else if(message.startsWith("USERNAME"))
	    {
			System.out.println("klient chce ustawic nazwe");
	        if(game.addPlayer(message.substring(9, message.length()), clientHandler))
			{
	            response = "NAMEOK";     				
			}
			else response = "NAMETAKEN";
		}	
		else if(message.startsWith("LIST"))
		{
		    response = "LIST " + getList();
		}
		else if (message.startsWith("OPPONENT"))
		{
		    System.out.println("klient chce wybrać przeciwnika");
		    message = message.replaceFirst("OPPONENT ", "");	
		    if (game.chooseOpponent(message, clientHandler.getPlayer())) response = "GAMESTART";
		    else response = "CHOOSEOPPONENTAGAIN " + getList(); 
		}
		else response = "UNKNOWNCOMMAND";
	    clientHandler.send(response);
	}
	
	public void processOutcommingMessage(String message){
		
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

}
