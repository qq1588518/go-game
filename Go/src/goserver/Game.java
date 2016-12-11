package goserver;

import java.util.Vector;

public class Game extends Thread
{
    Vector<Player> players;
    private Server server;
    
    /**
     * 
     */
    public Game(Server server)
    {
        this.server = server;
    }
    
    public synchronized Vector<Player> getNotBusyPlayers()
    {
        Vector<Player> notBusyPlayers = new Vector<Player>();
        
        for (Player player : players)
        {
            if (!player.isBusy()) notBusyPlayers.add(player);
        }
        
        return notBusyPlayers;
    }
    
    public Vector<Player> getPlayers()
    {
        return players;
    }

    /**
     * @param input
     */
    public boolean addPlayer(String name)
    {
       if (!isNameTaken(name))
       {
           players.add(new Player(name));
           
           return true;
       }
       else return false;
        
    }
    
    synchronized private boolean isNameTaken(String name)
    {
        for (Player player : players)
        {
            if (player.getName().equals(name)) return true;
        }
        
        return false;
    }
    
    
    
}
