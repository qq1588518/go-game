/**
 *
 */
package game.states;

import game.Color;
import game.GamePlay;
import game.Player;

import java.awt.*;
import java.util.HashMap;

/**
 * @author mk
 */
public class GamePlayStateWhiteSetsTeritory implements GamePlayState {

    private final GamePlay gamePlay;

    public GamePlayStateWhiteSetsTeritory(GamePlay gamePlay) {
        this.gamePlay = gamePlay;
    }

    @Override
    public void sendProposal(Player player, String message) {
        if (player == gamePlay.getWhite() && message.startsWith("TERRITORYSUGGESTION")) {
            gamePlay.getTranslator().setLastTerritorySuggestion(message);
            gamePlay.getBlack().sendMessage(message);
            gamePlay.setState(new GamePlayStateBlackSetsTeritory(gamePlay));
        }
    }

    @Override
    public void reachAgreement(Player player) {
        if (player == gamePlay.getWhite()) {
            HashMap<Point, Color> territories = gamePlay.getTranslator().getLastTerritorySuggestion();
            if (territories != null) {
                gamePlay.getBoard().setTerritories(territories);
                gamePlay.endGame();
            }
        }
    }

    @Override
    public void makeMove(Player p, int x, int y) {
    }

    public void makeMove(Player player) {
    }

    @Override
    public boolean makeMove(Player player, boolean wasPassed) {
        return false;
    }


}