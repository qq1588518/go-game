package goserver;

import java.util.Vector;

import com.sun.media.jfxmedia.events.PlayerStateEvent.PlayerState;

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
        players = new Vector<Player>();
    }
    
    public synchronized Vector<String> getNotBusyPlayersNames()
    {
        Vector<String> notBusyPlayersNames = new Vector<String>();
        
        for (Player player : players)
        {
            if (!player.isBusy()) notBusyPlayersNames.add(player.getName());
        }
        
        return notBusyPlayersNames;
    }
    
    public Vector<Player> getPlayers()
    {
        return players;
    }

    /**
     * @param input
     */
    public boolean addPlayer(String name, ClientHandler handler)
    {
       if (!isNameTaken(name))
       {
           Player p = new Player(name, handler);
           players.add(p);
           handler.setPlayer(p);
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

    /**
     * @param message
     * @return
     */
    synchronized public boolean chooseOpponent(String name, Player player)
    {
        if (getNotBusyPlayersNames().contains(name))
        {
            
            //       GamePlay gp = new GamePlay(first, second);
            return true;
        }
        
        
        
        return false;
    }
    
    public synchronized void deletePlayer(Player player)
    {
        players.remove(player);
    }
    
}
