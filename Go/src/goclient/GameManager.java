/**
 * 
 */
package goclient;

/**
 * To chyba ma być coś, co komunikuje GUI z resztą klienta. Ale nie jestem pewna. 
 * Albo może klienta z serwerem też trochę? 
 * Wzorowane na tym, ale nie mam koncepcji: 
 * http://blue-walrus.com/2011/10/swing-and-design-patterns-%e2%80%93-part-3-command-pattern/
 *
 */
public class GameManager
{
    private GameState state;
    private GUIMediator mediator;
    private int boardSize;
    final StoneType myColor;
    private GameServerTranslator translator;
    private enum Field {BLACK, WHITE, EMPTY};
    private Field[][] board;
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
        System.out.println(x);
        System.out.println(y);
        System.out.println(board[x][y]);
        if (board[x][y] != Field.EMPTY) 
        {
            displayMessage("This field is already ocuppied. ");
            return false;
        }
        if (checkIfSuicidal(x, y))
        {
            displayMessage("Suicidal moves are forbidden.");
            return false;
        }
        return true;
    }
    

    /**
     * TODO: zaimplementować sprawdzanie samobójstw
     * @param x
     * @param y
     * @return
     */
    private boolean checkIfSuicidal(int x, int y)
    {
        // TODO Auto-generated method stub
        return false;
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
}
