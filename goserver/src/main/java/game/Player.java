package game;

import server.ClientHandler;

/**
 * Represents a Player.
 */
public class Player {
    private final ClientHandler handler;
    private final ClientMessagesTranslator translator;
    private GamePlay gamePlay = null;
    private final String name;
    private boolean busy = false;

    /**
     * Constructs a new Player with given name connected to given ClientHandler
     *
     * @param name    String with name of the Player
     * @param handler ClientHandler managing connection with the Player.
     */
    public Player(String name, ClientHandler handler) {
        this.name = name;
        this.handler = handler;
        this.translator = handler.getTranslator();
    }

    public String getName() {
        return name;
    }

    public boolean isBusy() {
        return busy;
    }

    public void setBusy() {
        busy = true;
    }

    public void setNotBusy() {
        busy = false;
    }

    /**
     * Makes a move to given coordinates.
     *
     * @param x first coordinate of the Field
     * @param y second coordinate of the Field
     */
    public void makeMove(int x, int y) {
        if (gamePlay != null) gamePlay.makeMove(this, x, y);
    }

    /**
     * Makes a move with no coordinates i.e. PASS move.
     */
    public void makeMove() {
        if (gamePlay != null) gamePlay.makeMove(this);
    }

    /**
     * Sends a message to the Player application.
     *
     * @param message String to send
     */
    public void sendMessage(String message) {
        handler.send(message);
    }

    /**
     * Sends an invitation from a given Player
     *
     * @param player String with inviting Player's name.
     */
    public void beInvited(String player) {
        translator.sendInvitation(player);
    }

    /**
     * Sends an refusal to invitation.
     */
    public void beRefused() {
        translator.sendRefusal();
    }

    /**
     * Sends proposal (of territories or dead stones)
     *
     * @param message String containing proposal.
     */
    public void sendProposal(String message) {
        gamePlay.sendProposal(this, message);
    }

    /**
     * Accepts suggestion from other Player.
     */
    public void acceptSuggestion() {
        gamePlay.acceptProposal(this);
    }

    /**
     * Sends agreement (on territories or dead stones)
     */
    public void sendAgreement() {
        translator.sendAgreement();
    }

    /**
     * Gets GamePlay in which the Player is playing
     *
     * @return GamePlay in which the Player is playing
     */
    public GamePlay getGamePlay() {
        return gamePlay;
    }

    public void setGamePlay(GamePlay gamePlay) {
        this.gamePlay = gamePlay;
    }
}