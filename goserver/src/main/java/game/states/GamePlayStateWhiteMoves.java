/**
 *
 */
package game.states;

import game.Color;
import game.GamePlay;
import game.Player;
import game.board.Field;
import game.board.FieldType;
import game.board.MoveState;

import java.util.HashSet;

/**
 * @author mk
 */
public class GamePlayStateWhiteMoves implements GamePlayState {
    private final GamePlay gamePlay;

    /**
     * @param gamePlay
     */
    public GamePlayStateWhiteMoves(GamePlay gamePlay) {
        this.gamePlay = gamePlay;
    }

    /*
     * (non-Javadoc)
     * @see goserver.GamePlayState#makeMove(goserver.Player, int, int)
     */
    @Override
    synchronized public void makeMove(Player p, int x, int y) {
        if (p == gamePlay.getWhite()) {
            MoveState moveState = gamePlay.getBoard().checkIfMovePossible(Color.WHITE, x, y);
            if (moveState.equals(MoveState.ACCEPTED)) {
                gamePlay.getBoard().putStone(Color.WHITE, x, y);

                HashSet<Field> removed = gamePlay.getBoard().update(new Field(x, y, FieldType.WHITE, gamePlay.getBoard()));
                gamePlay.getTranslator().confirmMove(p);
                gamePlay.getTranslator().sendOpponentsMove(gamePlay.getBlack(), x, y, removed);
                gamePlay.getTranslator().sendRemovedStones(gamePlay.getWhite(), removed);
                gamePlay.getBoard().removeStones(removed);
                gamePlay.getTranslator().sendStats(gamePlay.getBoard().getBlackCaptured(), gamePlay.getBoard().getWhiteCaptured());
                gamePlay.setState(new GamePlayStateBlackMoves(gamePlay));
            } else gamePlay.getTranslator().rejectMove(p, moveState);
        } else gamePlay.getTranslator().rejectMoveAttempt(p);
    }

    public boolean makeMove(Player p, boolean lastWasPass) {
        if (p == gamePlay.getWhite()) {
            if (lastWasPass) {
                gamePlay.getTranslator().sendChooseDead(gamePlay.getBlack());
                gamePlay.getTranslator().sendGameStopped(p);
                gamePlay.setState(new GamePlayStateBlackChoosesDead(gamePlay));
            } else {
                gamePlay.getTranslator().sendOpponentsMove(gamePlay.getBlack());
                gamePlay.setState(new GamePlayStateBlackMoves(gamePlay));
            }
            return true;
        } else {
            gamePlay.getTranslator().rejectMoveAttempt(p);
            return false;
        }

    }

    @Override
    public void sendProposal(Player player, String message) {
    }

    @Override
    public void reachAgreement(Player player) {
    }

}