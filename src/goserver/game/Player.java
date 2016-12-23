package goserver.game;

import goserver.server.ClientHandler;

public class Player
{
    private ClientHandler handler;
    private ClientMessagesTranslator translator;
    private GamePlay gamePlay = null;
    private String name;
    private boolean busy = false;
    
    public Player(String name, ClientHandler handler)
    {
    	
        this.name = name;
        this.handler = handler;
        this.translator = handler.getTranslator();
    }
    
    public String getName()   { return name;  }
    public boolean isBusy()   { return busy;  }
    public void setBusy()     { busy = true;  }
    public void setNotBusy()  { busy = false; }
    
    public void setGamePlay(GamePlay gamePlay)
    {
        this.gamePlay = gamePlay;
    }

    /**
     * @param y 
     * @param x 
     * 
     */
    public void makeMove(int x, int y)
    {
        if (gamePlay != null) gamePlay.makeMove(this, x, y);
    }

    public void sendMessage(String message)
    {
        handler.send(message);
    }
    /**
     * @param player
     */
    public void beInvited(String player)
    {
        translator.sendInvitation(player);
    }
    
 public void surrendered(String message){
    	
    	gamePlay.surrender(message);
    	
    }

	
	public void makeMove() {
		if (gamePlay != null) gamePlay.makeMove(this);
		
	}

	public void beDeclined() {
		// TODO Auto-generated method stub
		translator.sendDeclination();
	}
}
