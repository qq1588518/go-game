package gogame;

import java.util.Vector;

import goserver.Server;

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
            this.players = server.getPlayers();
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
        	   Player player = new Player(name);
        	  
        	   server.addPlayer(player);
        	         
               return true;
           }
           else return false;
           
        }
       
        synchronized private boolean isNameTaken(String name)
        {
        	
        		for (Player player : server.getPlayers())
        		{
        			if (player.getName().equals(name)) return true;
        		}
           
        		return false;
        	
        	
        }
}

