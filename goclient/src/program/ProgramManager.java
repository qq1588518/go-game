/**
 * 
 */
package goclient.program;

import goclient.game.GameManager;
import goclient.game.GameServerTranslator;
import goclient.game.StoneType;

/**
 * @author mk
 *
 */
public class ProgramManager
{
    private Program parent;
    private ProgramServerTranslator translator = null;
    private GameManager game = null;
    /**
     * 
     */
    public ProgramManager(Program program)
    {
        this.parent = program; 
    }

    public void askForName(String text)
    {
        parent.getGUI().displayChooseNameDialog(text);
    }
    
    public void showPlayers(String list, String text)
    {
        parent.getGUI().displayPlayersDialog(list, text);
    }
    
    /**
     * @param name
     * @throws EmptyNameException 
     * @throws NameContainsSpaceException 
     * 
     */
    public void sendChosenName(String name) throws ComponentException, NameContainsSpaceException, EmptyNameException
    {
       System.out.println("manager ok");
        if (translator == null) throw new ComponentException("Translator not set in ProgramManager");
        translator.sendName(name);
    }

    public void setTranslator(ProgramServerTranslator translator)
    {
        this.translator = translator;
    }

    /**
     * @param oppname
     */
    public void chooseOpponent(String oppname) throws ComponentException
    {	
    	if (translator == null) throw new ComponentException("Translator not set in ProgramManager");
        translator.sendOpponent(oppname);
    }
    
    public void askForList() throws ComponentException
    {
        if (translator == null) throw new ComponentException("Translator not set in ProgramManager");
        translator.sendListRequest();
    }
    
    public void startGame(StoneType myColor)
    {
        game = new GameManager(19, parent.getGUI(), myColor);
        parent.getGUI().setGameComponents(game);
        parent.getGUI().setOptionPanelButtonsListeners(game);
        GameServerTranslator gt = new GameServerTranslator(game, parent.getSocket());
        parent.getSocket().setTranslator(gt);
        game.setTranslator(gt);
    }
    
    public void endGame()
    {
        game = null;
        parent.getSocket().setTranslator(translator);
        parent.reset();
    }

    /**
     * @param name
     */
    public void invite(String name)
    {
        parent.getGUI().displayInvitation(name);
    }

    /**
     * @param name
     * @param accepted
     */
    public void respondInvitation(String name, boolean accepted) throws ComponentException
    {
        if (translator == null) throw new ComponentException("Translator not set in ProgramManager");
        if (accepted){
        	translator.sendAgreement(name);
        }
        else{
        	translator.sendDecline(name);
        }
    }
}