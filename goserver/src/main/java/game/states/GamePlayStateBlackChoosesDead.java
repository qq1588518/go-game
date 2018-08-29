package game.states;

import game.GamePlay;
import game.Player;
import game.board.Field;

import java.util.HashSet;

public class GamePlayStateBlackChoosesDead implements GamePlayState {

    private final GamePlay gamePlay;

    public GamePlayStateBlackChoosesDead(GamePlay gamePlay) {
        this.gamePlay = gamePlay;
    }

    @Override
    public void makeMove(Player p, int x, int y) {

    }

    @Override
    public boolean makeMove(Player player, boolean wasPassed) {
        return false;
    }

    @Override
    public void sendProposal(Player player, String message) {
        if (player == gamePlay.getBlack() && message.startsWith("DEADSUGGESTION")) {
            gamePlay.getTranslator().setLastDeadSuggestion(message);
            gamePlay.getWhite().sendMessage(message);
            gamePlay.setState(new GamePlayStateWhiteChoosesDead(gamePlay));
        }
    }

    @Override
    public void reachAgreement(Player player) {
        if (player == gamePlay.getBlack()) {
            HashSet<Field> toRemove = gamePlay.getTranslator().getLastDeadSuggestion(gamePlay.getBoard());
            if (toRemove != null) {
                gamePlay.getBoard().removeStones(toRemove);
                gamePlay.setState(new GamePlayStateWhiteSetsTeritory(gamePlay));
                gamePlay.getTranslator().sendChooseTerritory(gamePlay.getWhite());
                gamePlay.getTranslator().sendDeadOK(player);
                gamePlay.getTranslator().sendStats(gamePlay.getBoard().getBlackCaptured(), gamePlay.getBoard().getWhiteCaptured());
            }
        }
    }
}
