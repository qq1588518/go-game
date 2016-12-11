package goserver;

import java.io.PrintWriter;

public class ClientMessagesTranslator {
	
	ClientHandler clientHandler;
	private PrintWriter writer;
	private Game game;
	
	public ClientMessagesTranslator(ClientHandler clientHandler)
	{
		this.clientHandler = clientHandler;
		writer = clientHandler.getWriter();
		game = clientHandler.getGame();
	}
	
	public void processIncommingMessage(String message)
	{
		String response = "";
	    if(message.startsWith("USERNAME"))
	    {
			System.out.println("klient chce ustawic nazwe");
	        if(game.addPlayer(message.substring(9, message.length()), clientHandler))
			{
				response = "NAMEOK" + game.getPlayers();     				
			}
			else response = "NAMETAKEN";
	            		
		}	
		else if (message.startsWith("OPPONENT"))
		{
			message = message.replaceFirst("OPPONENT", "");		
			response = game.chooseOpponent(message, clientHandler.getPlayer()) ? "GAMESTART" : "CHOOSEOPPONENTAGAIN";
		}
		else response = "UNKNOWNCOMMAND";
	    writer.println(response);
	}
	
	public void processOutcommingMessage(String message){
		
	}

}
