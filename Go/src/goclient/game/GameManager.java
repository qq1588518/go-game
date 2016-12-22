/**
 * 
 */
package goclient.game;

import java.awt.List;
import java.awt.Point;
import java.util.Vector;

import goclient.game.states.GameState;
import goclient.game.states.GameStateMyMove;
import goclient.game.states.GameStateOpponentsMove;
import goclient.gui.GUIMediator;
import goclient.program.ComponentException;

public class GameManager
{
    private GameState state;
    private GUIMediator mediator;
    private int boardSize;
    final StoneType myColor;
    private GameServerTranslator translator;
    enum Field {BLACK, WHITE, EMPTY};
    private Field[][] board;
    private int waitingX;
    private int waitingY;
    /**
     * 
     */
    public GameManager(int boardSize, GUIMediator mediator, StoneType myColor)
    {
        this.boardSize = boardSize;
        System.out.println(boardSize);
        this.mediator = mediator;
        this.myColor = myColor;
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
     * @param input
     */
    public void displayMessage(String input)
    {
        mediator.displayMessage(input);
    }

    /**
     * @param x
     * @param y
     * @return
     */
    public boolean checkIfMovePossible(int x, int y)
    {
        if (board[x][y] != Field.EMPTY) 
        {
            displayMessage("This field is already ocuppied. ");
            return false;
        }
        return true;
    }
    

    /**
     * @param x
     * @param y
     */
    public void sendMove(int x, int y)
    {
        translator.sendMove(x,y);
    }
    
    
    
    /**
     * @param gt
     */
    public void setTranslator(GameServerTranslator gt)
    {
        this.translator = gt;
    }
    
    public void setState(GameState state)
    {
        this.state = state;
    }
    
    public GameState getState()
    {
        return state;
    }

    public GUIMediator getMediator()
    {
        return mediator;
    }

    /**
     * @param string
     * @param string2
     */
    public void addMyMove()
    {
        try
        {
            mediator.getGamePanel().getBoardPanel().addStone(myColor, waitingX, waitingY);
           // state = new GameStateOpponentsMove(this);
        }
        catch (WrongCoordsException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * @param x
     * @param y
     */
    public void saveWaitingMove(int x, int y)
    {
        waitingX = x;
        waitingY = y;
    }

    /**
     * 
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
     * @param valueOf
     * @param valueOf2
     */
    public void addOpponentsMove(Integer x, Integer y)
    {
        try
        {
            mediator.getGamePanel().getBoardPanel().addStone(myColor.other(), x, y);
         //   state = new GameStateMyMove(this);
        }
        catch (WrongCoordsException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }    
    
    public void sendWhiteFlag() throws ComponentException{
    	if (translator == null) throw new ComponentException("Translator not set in ProgramManager");
    	translator.sendSurrender();
    }

	public void missTurn() {
		 state = new GameStateOpponentsMove(this);
		if (translator == null)
			try {
				throw new ComponentException("Translator not set in ProgramManager");
			} catch (ComponentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		translator.sendPassMove();
		// TODO Auto-generated method stub
		
	}
    
    synchronized public void removeStones(Vector<Point> fields)
    {
        
    	for (Point point : fields)
        {
        	StoneType c = (board[point.x][point.y] == Field.BLACK) ? StoneType.BLACK : StoneType.WHITE;
            mediator.getGamePanel().getBoardPanel().removeStone(point.x, point.y, c);
            board[point.x][point.y] = Field.EMPTY;
        }
        state.nextTurn();
    }

}
