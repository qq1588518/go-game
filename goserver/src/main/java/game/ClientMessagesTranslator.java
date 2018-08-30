package game;

import server.ClientHandler;

import java.util.Objects;

/**
 * Handles incoming messages from client and prepares and sends outgoing messages.
 */
public class ClientMessagesTranslator {
    private final ClientHandler clientHandler;
    private final Game game;

    /**
     * Constructs a new ClientMessagesTranslator for given ClientHandler.
     *
     * @param clientHandler ClientHandler for which the messages are translated.
     */
    public ClientMessagesTranslator(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
        game = clientHandler.getGame();
    }

    /**
     * Received String message and calls appropriate methods depending on its contents.
     *
     * @param message String to translate to methods.
     */
    public void processIncomingMessage(String message) {
        String response = "";
        if (message.startsWith("CONNECTION OK")) {
            clientHandler.send("SETNAME");
            return;
        } else if (message.startsWith("USERNAME")) {
            try {
                if (game.addPlayer(message.replaceFirst("USERNAME ", ""), clientHandler)) response = "NAMEOK";
                else response = "NAMETAKEN";
            } catch (NameContainsSpaceException | EmptyNameException e) {
                return;
            }
        } else if (message.startsWith("LIST")) {
            response = "LIST " + getList();
        } else if (message.startsWith("OPPONENT")) {
            message = message.replaceFirst("OPPONENT ", "");
            if (game.inviteOpponent(message, clientHandler.getPlayer().getName())) return;
            else response = "CHOOSEOPPONENTAGAIN " + getList();
        } else if (message.startsWith("INVAGREE")) {
            message = message.replaceFirst("INVAGREE ", "");
            if (game.chooseOpponent(message, clientHandler.getPlayer())) return;
            else response = "CHOOSEOPPONENTAGAIN " + getList();
        } else if (message.startsWith("INVDECLINE")) {
            message = message.replaceFirst("INVDECLINE ", "");
            if (game.refuseInvitation(message)) return;
            else response = "CHOOSEOPPONENTAGAIN " + getList();
        } else if (message.startsWith("MOVE")) {
            String[] coords = message.replaceFirst("MOVE ", "").split(" ");
            if (coords.length == 2) {
                clientHandler.getPlayer().makeMove(Integer.valueOf(coords[0]), Integer.valueOf(coords[1]));
                return;
            } else if (coords[0].equals("PASS")) {
                clientHandler.getPlayer().makeMove();
                return;
            }
        } else if (message.startsWith("SURRENDER ")) {
            clientHandler.getPlayer().getGamePlay().endGame(clientHandler.getPlayer());
            return;
        } else if (message.startsWith("DEADSUGGESTION")) {
            clientHandler.getPlayer().sendProposal(message);
            return;
        } else if (message.startsWith("TERRITORYSUGGESTION")) {
            clientHandler.getPlayer().sendProposal(message);
            return;
        } else if (message.startsWith("ACCEPT")) {
            clientHandler.getPlayer().acceptSuggestion();
            return;
        } else if (message.startsWith("DELETE ")) {
            message = message.replaceFirst("DELETE ", "");
            game.deletePlayer(game.getPlayerNamed(message));
            return;
        } else if (message.startsWith("RESUME")) {
            clientHandler.getPlayer().getGamePlay().resumeGame(clientHandler.getPlayer());
        } else response = "UNKNOWNCOMMAND";
        clientHandler.send(response);
    }

    /**
     * Builds a String from list of non-busy Players on server.
     *
     * @return String with Players list.
     */
    private String getList() {
        StringBuilder b = new StringBuilder();
        String myname = clientHandler.getPlayer().getName();
        for (String name : game.getNotBusyPlayersNames()) {
            if (!Objects.equals(name, myname)) b.append(name).append(" ");
        }

        return b.toString();
    }

    /**
     * Sends invitation from given name, using players clientHandler method.
     *
     * @param player String with name of inviting player.
     */
    public void sendInvitation(String player) {
        clientHandler.send("INVITATIONFROM " + player);
    }

    /**
     * Sends a message which refusal of invitation.
     */
    public void sendRefusal() {
        clientHandler.send("DECLINED");
    }


}