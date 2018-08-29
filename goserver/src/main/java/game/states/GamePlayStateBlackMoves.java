package game.states;

import game.Color;
import game.GamePlay;
import game.Player;
import game.board.Field;
import game.board.FieldType;
import game.board.MoveState;

import java.util.HashSet;

public class GamePlayStateBlackMoves implements GamePlayState {
    private final GamePlay gamePlay;


    public GamePlayStateBlackMoves(GamePlay gamePlay) {
        this.gamePlay = gamePlay;
    }

    /*
     * (non-Javadoc)
     * @see goserver.GamePlayState#makeMove(goserver.Player, int, int)
     */
    @Override
    synchronized public void makeMove(Player p, int x, int y) {
        if (p == gamePlay.getBlack()) {
            MoveState moveState = gamePlay.getBoard().checkIfMovePossible(Color.BLACK, x, y);
            if (moveState.equals(MoveState.ACCEPTED)) {
                gamePlay.getBoard().putStone(Color.BLACK, x, y);

                HashSet<Field> removed = gamePlay.getBoard().update(new Field(x, y, FieldType.BLACK, gamePlay.getBoard()));
                gamePlay.getTranslator().confirmMove(p);
                gamePlay.getTranslator().sendOpponentsMove(gamePlay.getWhite(), x, y, removed);
                gamePlay.getTranslator().sendRemovedStones(gamePlay.getBlack(), removed);
                gamePlay.getBoard().removeStones(removed);
                gamePlay.getTranslator().sendStats(gamePlay.getBoard().getBlackCaptured(), gamePlay.getBoard().getWhiteCaptured());
                gamePlay.setState(new GamePlayStateWhiteMoves(gamePlay));
            } else gamePlay.getTranslator().rejectMove(p, moveState);
        } else gamePlay.getTranslator().rejectMoveAttempt(p);
    }


    public boolean makeMove(Player p, boolean lastWasPass) {
        if (p == gamePlay.getBlack()) {
            if (lastWasPass) {
                gamePlay.getTranslator().sendChooseDead(gamePlay.getWhite());
                gamePlay.getTranslator().sendGameStopped(p);
                gamePlay.setState(new GamePlayStateWhiteChoosesDead(gamePlay));

            } else {
                gamePlay.getTranslator().sendOpponentsMove(gamePlay.getWhite());
                gamePlay.setState(new GamePlayStateWhiteMoves(gamePlay));
            }
            return true;
        } else {
            gamePlay.getTranslator().rejectMoveAttempt(p);
            return false;
        }
    }

    @Override
    public void sendProposal(Player player, String message) { }

    @Override
    public void reachAgreement(Player player) { }
}