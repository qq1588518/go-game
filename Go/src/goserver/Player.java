package goserver;

public class Player
{
    private ClientHandler handler;
    private ClientMessagesTranslator translator;
    private String name;
    private boolean busy = false;
    
    Player(String name, ClientHandler handler)
    {
        this.name = name;
        this.handler = handler;
        this.translator = handler.getTranslator();
    }
    
    public String getName()  { return name;  }
    public boolean isBusy()  { return busy;  }
    public void setBusy()    { busy = true;  }
    public void setNotBusy() { busy = false; }

    /**
     * 
     */
    public void makeMove()
    {
//        handler.sendMoveRequest();
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
}
