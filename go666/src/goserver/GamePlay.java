/**
 * 
 */
package goserver;

import java.util.Random;

/**
 * @author mk
 *
 */
public class GamePlay extends Thread
{
    private Player black;
    private Player white;
    private Board board;
    private int n = 19;
    private GamePlayState state;
    private GamePlayTranslator translator; 
    
    /**
     * 
     */
    public GamePlay(Player first, Player second)
    {
        board = new Board(19);
        Random r = new Random(); 
        boolean firstBlack = r.nextBoolean();
        black = firstBlack ? first : second;
        white = firstBlack ? second : first;
        
        black.setGamePlay(this);
        white.setGamePlay(this);
       
        translator = new GamePlayTranslator(black, white);
    }
    
    
    @Override
    public void run()
    {
        translator.notifyGameStart();
        state = new GamePlayStateBlackMoves(this);
    }
    
    public void makeMove(Player p, int x, int y)
    {
        state.makeMove(p, x, y);
    }
    
    public Player getBlack()
    {
        return black;
    }

    public Player getWhite()
    {
        return white;
    }
    
    public GamePlayTranslator getTranslator()
    {
        return translator;
    }

    /**
     * @param string
     */
    private void send(String message)
    {
        black.sendMessage(message);    
        white.sendMessage(message);
    }

    /**
     * @param gamePlayStateWhiteMoves
     */
    public void setState(GamePlayState state)
    {
       this.state = state;
    }
    
    Board getBoard()
    {
        return board;
    }
    public void surrender(String message){
    	System.out.println("hhyyhyhy " + message);
    	if(message.equals("BLACK")){
    		
    		white.sendMessage("YOULOOSE");
    	}
    	else if(message.equals("WHITE")){
    		
    		black.sendMessage("YOULOOSE");
    	}
    }


	public void giveTurn(String message) {
		if(message.equals("BLACK")){
			white.sendMessage("ENEMYPASS");
		}
		else if(message.equals("WHITE")){
			black.sendMessage("ENEMYPASS");
		}
	}
}
