/**
 * 
 */
package goclient.game;

import java.awt.Point;
import java.util.HashSet;

import goclient.game.states.GameState;
import goclient.game.states.GameStateMyMove;
import goclient.game.states.GameStateNotStartedYet;
import goclient.game.states.GameStateOpponentsMove;
import goclient.gui.DrawingManager;
import goclient.gui.DrawingMode;
import goclient.gui.GUIMediator;
import goclient.program.ComponentException;

public class GameManager
{
    private GameState state;
    private GUIMediator mediator;
    private DrawingManager drawingManager;
    private int boardSize;
    public final StoneType myColor;
    private GameServerTranslator translator;
    public enum Field {BLACK, WHITE, EMPTY};
    private Field[][] board;
    private int waitingX;
    private int waitingY;
   
    /**
     * Creates board, sets fields EMPTY and gives player color.
     * Checks if move is possible from Client side.
     * Manages which stones should be painted or removed.
     * @param boardSize
     * @param mediator
     * @param myColor
     */
    public GameManager(int boardSize, GUIMediator mediator, StoneType myColor)
    {
        this.boardSize = boardSize;
        this.mediator = mediator;
        this.myColor = myColor;
        this.drawingManager = new DrawingManager(mediator);
        board = new Field[boardSize][boardSize];
        
        for (int i = 0; i < boardSize; i++)
        {
            for (int j = 0; j < boardSize; j++) board [i][j] = Field.EMPTY;
        }
        
        state = (myColor == StoneType.BLACK) ? new GameStateMyMove(this) : new GameStateOpponentsMove(this);
        displayMessage("Welcome to go. You play as " + myColor + ".\n");
    }
        
    public void makeMove(int x, int y)
    {
        state.makeMove(x, y);
    }

    /**
     * Allows to show message in GUI window
     * @param input
     */
    public void displayMessage(String input)
    {
        mediator.displayMessage(input);
    }

    /**
     * 
     * Checks if move is possible from client side
     * @param x - coordx
     * @param y - coordy
     * @return true if move is possible, false if not
     */
    public boolean checkIfMovePossible(int x, int y)
    {
    	if(x < 0 || y < 0 || x >= boardSize || boardSize <= y)
    	{
    		displayMessage("Clicked outside the board");
    		return false;
    	}
        if (board[x][y] != Field.EMPTY) 
        {
            displayMessage("This field is already ocuppied. ");
            return false;
        }
        return true;
}
    
    /**
     * If coords equals -1, tell translator to send pass move to server
     * @param x
     * @param y
     */
    public void sendMove(int x, int y)
    {
        if(x == -1 && y == -1) translator.sendPassMove();
        else translator.sendMove(x,y);
    }
    
    /**
     * @param gt
     */
    public void setTranslator(GameServerTranslator gt)
    {
        this.translator = gt;
    }
    
    /**
     * 
     * @param state
     */
    public void setState(GameState state)
    {
        this.state = state;
    }
    /**
     * 
     * @return
     */
    public GameState getState()
    {
        return state;
    }
    /**
     * 
     * @return
     */
    public GUIMediator getMediator()
    {
        return mediator;
    }
    /**
     * 
     * @return
     */
    public DrawingManager getDrawingManager()
    {
    	return drawingManager;
    }
    /**
     * 
     * @return
     */
    public int getBoardSize()
    {
    	return boardSize;
    }
    /**
     * 
     * @return
     */
    public GameServerTranslator getTranslator()
    {
    	return translator;
    }

    /**
     * Adds move to board panel
     */
    public void addMyMove()
    {
        try
        {
            mediator.getGamePanel().getBoardPanel().addStone(myColor, waitingX, waitingY);
            board[waitingX][waitingY] = (myColor == StoneType.BLACK) ? Field.BLACK : Field.WHITE;
        }
        catch (WrongCoordsException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Saves move waiting for response from server
     * @param x
     * @param y
     */
    public void saveWaitingMove(int x, int y)
    {
        waitingX = x;
        waitingY = y;
    }

    /**
     * Displayes message of reason, why user could not put stone
     * @param reason
     */
    public void resetMyMove(String reason)
    {
    	String explanation = "";
    	StringBuilder message = new StringBuilder();
    	
    	message.append("Your move to [" + String.valueOf(waitingX) + ", " + String.valueOf(waitingY) + "] was incorrect because ");
    	if(reason.contains("SUICIDAL")) explanation = "it was suicidal. ";
    	else if(reason.contains("KO")) explanation = "of the KO rule. ";
    	else if(reason.contains("NOT EMPTY")) explanation = "the field was already occupied. ";
    	message.append(explanation);
    	message.append("Please try again.\n");
    	displayMessage(message.toString());
        waitingX = -1;
        waitingY = -1;
        state.reset();
    }

    /**
     * Adds opponent move to user board
     * @param valueOf
     * @param valueOf2
     */
    public void addOpponentsMove(Integer x, Integer y)
    {
        try
        {
            mediator.getGamePanel().getBoardPanel().addStone(myColor.other(), x, y);
            board[x][y] = (myColor == StoneType.BLACK) ? Field.WHITE : Field.BLACK;
        }
        catch (WrongCoordsException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }    
    
    /**
     * Tells translator to send information about user surrender
     * @throws ComponentException
     */
    public void sendWhiteFlag() throws ComponentException{
    	if (translator == null) throw new ComponentException("Translator not set in ProgramManager");
    	translator.sendSurrender();
    }

    /**
     * Makes pass move
     */
	public void missTurn() 
	{
		state.makeMove(-1, -1);
	}
    
	/**
	 * Removes stones with indicated hashset
	 * @param fields
	 */
    synchronized public void removeStones(HashSet<Point> fields)
    {
    	for (Point point : fields)
        {
        	StoneType c = (board[point.x][point.y] == Field.BLACK) ? StoneType.BLACK : StoneType.WHITE;
            mediator.getGamePanel().getBoardPanel().removeStone(point.x, point.y, c);
            board[point.x][point.y] = Field.EMPTY;
        }
        state.nextTurn();
    }

    private boolean isAppropriate(int x, int y, DrawingMode mode)
    {
    	if (mode.equals(DrawingMode.DEAD))  return !board[x][y].equals(Field.EMPTY);
    	else  return board[x][y].equals(Field.EMPTY);
    }
    
	public HashSet<Point> getAppropriateFieldsInArea(int upperLeftX, int upperLeftY, int width, int height, DrawingMode mode) 
	{
		HashSet<Point> fields = new HashSet<Point>(); 
		
		for (int i = 0; i < boardSize; i++)
        {
            for (int j = 0; j < boardSize; j++) 
            {
            	if (isAppropriate(i, j, mode) && 
            		i >= upperLeftX && i <= upperLeftX + width &&
            		j >= upperLeftY && j <= upperLeftY + height) fields.add(new Point(i, j));
            }
        }
		return fields;
	}
	
	public boolean isFieldTypeAppropriate(int x, int y, DrawingMode mode)
	{
		return isAppropriate(x, y, mode);
	}
	/**
	 * Removes pointed stones
	 * @param x
	 * @param y
	 */
	public void remove(int x, int y) 
	{
		state.remove(x, y);
	}

	public void addDeadStoneSuggestion(HashSet<Point> dead) 
	{
		drawingManager.setDeadStones(dead);
		state.nextTurn();
	}
	
	public void addTerritorySuggestion(HashSet<Point> my, HashSet<Point> oppo) 
	{
		drawingManager.setMyTeritory(my);
		drawingManager.setOpponentsTeritory(oppo);
		state.nextTurn();
	}

	public void resumeGame(StoneType color) 
	{
		drawingManager.removeAllSigns();
		mediator.getOptionsPanel().disactivateTeritoriesBox(true);
		if (color.equals(myColor)) state = new GameStateMyMove(this);
		else
		{
			state = new GameStateOpponentsMove(this);
			translator.sendResume();
		}
	}
	/**
	 * When player is content, he can send proposition to enemy player
	 */
	public void sendProposition() 
	{
		state.sendProposal();
	}
	/**
	 * Accept proposition
	 */
	public void acceptProposition()
	{
		translator.sendAcceptance();
	}
	/**
	 * Counts which player won game; send it to mediator
	 * @param black
	 * @param white
	 */
	public void manageResults(double black, double white) 
	{
		boolean blackWon = (black > white) ? true : false;
		boolean iAmTheWinner;
		if (myColor.equals(StoneType.BLACK) && blackWon) iAmTheWinner = true;
		else if (myColor.equals(StoneType.WHITE) && !blackWon) iAmTheWinner = true; 
		else iAmTheWinner = false;
		
		state = new GameStateNotStartedYet();
		mediator.manageGameEnd(black, white, iAmTheWinner, false);
	}
	

}
