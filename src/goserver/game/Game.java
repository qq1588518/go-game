package goserver.game;

import java.util.Vector;

import goclient.program.EmptyNameException;
import goclient.program.NameContainsSpaceException;
import goserver.server.ClientHandler;

/**
 * Class handling Players connecting to the Server, pairing them and arranging new GamePlays.
 * @author mk
 *
 */
public class Game extends Thread
{
    Vector<Player> players;
    
    /**
     * Constructs a new Game object.
     */
    public Game()
    {
        players = new Vector<Player>();
    }
    
    /**
     * Checks which Players are available to play with.
     * @return Vector of names of not busy Players.
     */
    public synchronized Vector<String> getNotBusyPlayersNames()
    {
        Vector<String> notBusyPlayersNames = new Vector<String>();
        
        for (Player player : players)
        {
            if (!player.isBusy()) notBusyPlayersNames.add(player.getName());
        }
        return notBusyPlayersNames;
    }
    

    /**
     * Creates a new Player and adds it to Vector of Players.
     * Checks if given name is not in use. 
     * @param name String with Player's name
     * @param handler ClientHandler handling the Player
     * @return true if given name is not used and the Player was created, false otherwise.
     * @throws EmptyNameException 
     * @throws NameContainsSpaceException 
     */
    public boolean addPlayer(String name, ClientHandler handler) throws NameContainsSpaceException, EmptyNameException
    {
    	
    	checkIfNameIsGood(name);
    	
       if (!isNameTaken(name))
       {
           Player p = new Player(name, handler);
           players.add(p);
           handler.setPlayer(p);
           return true;
       }
       return false;
    }
    
    /**
     * Checks if there exists a Player with given name. 
     * @param name String to check for existence in Vector of Players
     * @return true if name found, false otherwise.
     */
    synchronized private boolean isNameTaken(String name)
    {
        for (Player player : players)
        {
            if (player.getName().equals(name)) return true;
        }
        return false;
    }
   
    /**
     * 
     * @param name
     * @return
     */
    public Player getPlayerNamed(String name)
    {
        for (Player player : players)
        {
            if (player.getName().equals(name)) return player;
        }
        
        return null;
    }

    /**
     * Starts new GamePlay.
     * Checks if opponent with given name exists and is not busy, creates new GamePlay thread with
     * two given players and sets their states to busy.      * 
     * @param name Opponent's name
     * @param player Player requesting new GamePlay
     * @return true if opponent is not busy and new GamePlay is created, false otherwise.
     */
    synchronized public boolean chooseOpponent(String name, Player player)
    {
        if (getNotBusyPlayersNames().contains(name))
        {
            Player opponent = getPlayerNamed(name);            
            GamePlay gp = new GamePlay(player, opponent);
            gp.start();
            player.setBusy();
            opponent.setBusy();
            return true;
        }
        return false;
    }
    
    /**
     * Manages sending invitation to Player with given name.
     * @param name Player to invite
     * @param byWho Player sending invitation
     * @return true if Player with given name exists and is not busy, false otherwise.
     */
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
    
    
    /**
     * Removes given Player from vector of Players
     * @param player Player to remove
     */
    public synchronized void deletePlayer(Player player)
    {
        players.remove(player);
    }
    
    private void checkIfNameIsGood(String name) throws NameContainsSpaceException, EmptyNameException {
  		if(name.contains(" ")){
  				throw new NameContainsSpaceException(name);
  			}
  		if(name.equals("")){
  				throw new EmptyNameException();
  		}
      
  	}

	public boolean declineOpponent(String message) {
		// TODO Auto-generated method stub
		if (getNotBusyPlayersNames().contains(message))
        {
            Player opponent = getPlayerNamed(message);
            if (opponent == null) return false;
            opponent.beDeclined();
            return true;
        }
        return false;
	}
    
}
