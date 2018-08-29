/**
 *
 */
package game.states;

import game.Player;

/**
 * @author mk
 */
public interface GamePlayState {

    /**
     * @param p
     * @param x
     * @param y
     */
    void makeMove(Player p, int x, int y);

    boolean makeMove(Player player, boolean wasPassed);

    void sendProposal(Player player, String message);

    void reachAgreement(Player player);
}
