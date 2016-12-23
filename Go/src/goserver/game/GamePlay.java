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
import goserver.game.states.GamePlayStateWhiteMoves;

/**
 * Manages single game between two Players.
 * This class is a context for state design pattern specified in goserver.gamestate.states subpackage
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
	 * Creates a new GamePlay for given Players 
	 * and chooses randomly, which of them will be Black, and which will be White.
	 * @param first Player
	 * @param second Player
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
    
    /**
     * Starts a GamePlay thread.
     */
    @Override
    public void run()
    {
        translator.notifyGameStart();
        state = new GamePlayStateBlackMoves(this);
    }
    
    /**
     * Sets state of this game which specifies allowed actions.
     * 
     * @param gamePlayStateWhiteMoves
     */
    public void setState(GamePlayState state)
    {
       this.state = state;
    }
    
    /**
     * Tries to handle move from given Player to given coordinates.
     * @param p Player trying to make the move.
     * @param x the first coordinate of desired move
     * @param y the second coordinate of desired move
     */
    public void makeMove(Player p, int x, int y)
    {
    	state.makeMove(p, x, y);
    	wasPassed = false;
    }

    /**
     * Tries to handle move from given Player without coordinates, i.e. a PASS move.
     * @param p Player trying to make the move.
     */
	public void makeMove(Player player) 
	{
		if (state.makeMove(player, wasPassed)) wasPassed = true;
	}

	/**
	 * Send proposal to a Player. Used when players are choosing dead stones and territories.
	 * @param player Player to which the proposal is send
	 * @param message String with contents of the porposal.
	 */
	public void sendProposal(Player player, String message) 
	{
		state.sendProposal(player, message);
	}

	/**
	 * Accepts other player's last proposal. Used when players are choosing dead stones and territories.
	 * @param player 
	 */
	public void acceptProposal(Player player) 
	{
		state.reachAgreement(player);
	}
	
	/**
	 * Resume game after given Player's request. After call, it will be the other Player's turn.
	 * @param player Player requesting resuming game.
	 */
	public void resumeGame(Player player) 
	{
		translator.setLastDeadSuggestion(null);
		translator.setLastTerritorySuggestion(null);
		translator.setLastDeadSuggestion(null);
		if (player == black)
		{
			state = new GamePlayStateWhiteMoves(this);
			translator.sendResume(white);
		}
		else
		{
			state = new GamePlayStateBlackMoves(this);
			translator.sendResume(black);
		}
	}
	
	/**
	 * Calculate points won by Players.
	 * @return array of Points won by Players
	 */
	public double[] calculateResults()
	{
		double blackPoints = board.getBlackTerritory() - board.getBlackCaptured();
		double whitePoints = board.getWhiteTerritory() - board.getWhiteCaptured() + 6.5;
		
		double[] results = {blackPoints, whitePoints};
		
		return results;
	}

	/**
	 * Ends Game when one of the Players have surrendered and sets Player's states to not busy.
	 * @param p Player which have surrendered.
	 */
	public void endGame(Player p)
	{
		setState(new GamePlayStateGameEnd());
		translator.sendSurrender(p);
		black.setNotBusy();
		white.setNotBusy();
	}
	
	/**
	 * Ends Game and sets Player's states to not busy.
	 */
	public void endGame() 
	{
		setState(new GamePlayStateGameEnd());

		double[] results = calculateResults();
		getTranslator().sendResults(results[0], results[1]);		

		black.setNotBusy();
		white.setNotBusy();
	}
    
    /**
     * Gets a Player with black Stones playing this game
     * @return Player with black Stones playing this game
     */
    public Player getBlack()
    {
        return black;
    }

    /**
     * Gets a Player with black Stones playing this game
     * @return Player with black Stones playing this game
     */
    public Player getWhite()
    {
        return white;
    }
    
    /**
     * Gets GamePlayTranslator assigned to this game
     * @return GamePlayTranslator assigned to this game
     */
    public GamePlayTranslator getTranslator()
    {
        return translator;
    }
    
    /**
     * Gets Board assigned to this game
     * @return Board assigned to this game
     */
    public Board getBoard()
    {
        return board;
    }

}