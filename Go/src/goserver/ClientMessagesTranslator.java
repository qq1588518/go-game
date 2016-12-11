package goserver;

import java.io.PrintWriter;

import gogame.Game;

public class ClientMessagesTranslator {
	
	ClientHandler clientHandler;
	private PrintWriter writer;
	private Game game;
	
	public ClientMessagesTranslator(ClientHandler clientHandler){
		this.clientHandler = clientHandler;
		writer = clientHandler.getWriter();
		game = clientHandler.getGame();
	}
	
	
	
	public void processIncommingMessage(String message){
		if(message.startsWith("USERNAME")){
    		
			if(clientHandler.getGame().addPlayer(message.substring(9, message.length()))){
				writer.println("NAMEOK" + game.getPlayers());     				
				clientHandler.setName(message.substring(9, message.length()));
			}
			else{
				writer.println("NAMETAKEN");
			}
	            		
		}	

		if (message.startsWith("ENEMY")){
			message = message.replaceFirst("ENEMY", "");
				
			if(game.isNameInArray(message)){
				writer.println("ENEMYOK");
			} 
			else{
				writer.println("ENEMYBAD");	
			}
		}
	}
	
	public void processOutcommingMessage(String message){
		
	}

}
