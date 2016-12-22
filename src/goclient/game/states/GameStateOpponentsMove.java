/**
 * 
 */
package goclient.game.states;

import goclient.game.GameManager;

/**
 * @author mk
 *
 */
public class GameStateOpponentsMove implements GameState
{
    
    private GameManager manager;

    /**
     * 
     */
    public GameStateOpponentsMove(GameManager manager)
    {
        this.manager = manager;
    }

    @Override
    public void makeMove(int x, int y)
    {
        System.out.println("Can't make move now. It's not your turn!");
    }
    @Override
    public void reset(){ }
    public void nextTurn() { manager.setState(new GameStateMyMove(manager)); }
    
}
