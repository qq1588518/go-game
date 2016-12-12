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
    
    private Player getPlayerNamed(String name)
    {
        for (Player player : players)
        {
            if (player.getName().equals(name)) return player;
        }
        
        return null;
    }

    /**
     * @param message
     * @return
     */
    synchronized public boolean chooseOpponent(String name, Player player)
    {
        if (getNotBusyPlayersNames().contains(name))
        {
            Player opponent = getPlayerNamed(name);            
            GamePlay gp = new GamePlay(player, opponent);
            gp.start();
            return true;
        }
        
        return false;
    }
    
    public synchronized boolean inviteOpponent(String name, String byWho)
    {
        if (getNotBusyPlayersNames().contains(name))
        {
            Player opponent = getPlayerNamed(name);
            if (opponent == null) return false;
            opponent.beInvited(byWho);
            return true;
        }
        
        return false;
    }
    
    public synchronized void deletePlayer(Player player)
    {
        players.remove(player);
    }
    
}
