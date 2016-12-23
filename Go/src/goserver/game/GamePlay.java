/**
 * 
 */
package goserver.game;

import java.util.Random;

import goserver.game.Player;
import goserver.game.board.Board;
import goserver.game.states.GamePlayState;
import goserver.game.states.GamePlayStateBlackMoves;
import goserver.game.states.GamePlayStateGameEnd;

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
    boolean wasPassed = false;
    
    /**
     * 
     */
    public GamePlay(Player first, Player second)
    {
        board = new Board(n);
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
    	wasPassed = false;
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
     * @param gamePlayStateWhiteMoves
     */
    public void setState(GamePlayState state)
    {
       this.state = state;
    }
    
    public Board getBoard()
    {
        return board;
    }
    public void surrender(String message){
    	
    	if(message.equals("BLACK")){
    		
    		white.sendMessage("YOULOOSE");
    	}
    	else if(message.equals("WHITE")){
    		
    		black.sendMessage("YOULOOSE");
    	}
    }

	//Send PASS 
	public void makeMove(Player player) 
	{
		if (state.makeMove(player, wasPassed)) wasPassed = true;
	}

	public void sendSuggestion(Player player, String message) 
	{
		state.sendProposal(player, message);
	}

	public void acceptSuggestion(Player player) 
	{
		state.reachAgreement(player);
	}
	
	public double[] calculateResults()
	{
		double blackPoints = board.getBlackTerritory() - board.getBlackCaptured();
		double whitePoints = board.getWhiteTerritory() - board.getWhiteCaptured() + 6.5;
		
		double[] results = {blackPoints, whitePoints};
		
		return results;
	}


	public void endGame() 
	{
		setState(new GamePlayStateGameEnd());
		double[] results = calculateResults();
		getTranslator().sendResults(results[0], results[1]);
		black.setNotBusy();
		white.setNotBusy();
		black.setGamePlay(null);
		white.setGamePlay(null);
	}
}