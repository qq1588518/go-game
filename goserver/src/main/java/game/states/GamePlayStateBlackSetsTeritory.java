package game.states;

import game.Color;
import game.GamePlay;
import game.Player;

import java.awt.*;
import java.util.HashMap;

public class GamePlayStateBlackSetsTeritory implements GamePlayState {

    private final GamePlay gamePlay;

    public GamePlayStateBlackSetsTeritory(GamePlay gamePlay) {
        this.gamePlay = gamePlay;
    }

    /* (non-Javadoc)
     * @see goserver.GamePlayState#makeMove(goserver.Player, int, int)
     */
    @Override
    public void makeMove(Player p, int x, int y) { }

    public void makeMove(Player player) { }

    @Override
    public boolean makeMove(Player player, boolean wasPassed) { return false; }

    @Override
    public void sendProposal(Player player, String message) {
        if (player == gamePlay.getBlack() && message.startsWith("TERRITORYSUGGESTION")) {
            gamePlay.getTranslator().setLastTerritorySuggestion(message);
            gamePlay.getWhite().sendMessage(message);
            gamePlay.setState(new GamePlayStateWhiteSetsTeritory(gamePlay));
        }
    }

    @Override
    public void reachAgreement(Player player) {
        if (player == gamePlay.getBlack()) {
            HashMap<Point, Color> territories = gamePlay.getTranslator().getLastTerritorySuggestion();
            if (territories != null) {
                gamePlay.getBoard().setTerritories(territories);
                gamePlay.endGame();
            }
        }
    }

}