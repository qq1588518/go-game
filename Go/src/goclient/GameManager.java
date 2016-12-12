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
    
    /**
     * 
     */
    public GameManager(int boardSize, GUIMediator mediator, StoneType myColor)
    {
        this.boardSize = boardSize;
        this.mediator = mediator;
        this.state = new GameStateNotStartedYet();
        this.myColor = myColor;
        
        state = (myColor == StoneType.BLACK) ? new GameStateMyMove(this) : new GameStateOpponentsMove(this);
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
    
     
}
