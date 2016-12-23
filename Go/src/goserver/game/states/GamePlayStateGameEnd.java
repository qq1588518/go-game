/**
 * 
 */
package goserver.game.states;

import goserver.game.Player;

/**
 * @author mk
 *
 */
public class GamePlayStateGameEnd implements GamePlayState
{

    /* (non-Javadoc)
     * @see goserver.GamePlayState#makeMove(goserver.Player, int, int)
     */
    @Override
    public void makeMove(Player p, int x, int y)
    {
        // TODO Auto-generated method stub
        
    }
    public void makeMove(Player player) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean makeMove(Player player, boolean wasPassed) {
		return false;
	}
	@Override
	public void sendSuggestion(Player player, String message) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void reachAgreement(Player player) {
		// TODO Auto-generated method stub
		
	}
}