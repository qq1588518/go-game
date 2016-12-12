/**
 * 
 */
package goclient;

/**
 * @author mk
 *
 */
public class ProgramManager
{
    private Program parent = null;
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
     * @param string
     * 
     */
    public void sendChosenName(String name) throws ComponentException
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
     * @param replaceFirst
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
        parent.getSocket().setTranslator(new GameServerTranslator(game));
    }
    
    public void endGame()
    {
        game = null;
        parent.getSocket().setTranslator(translator);
    }

    /**
     * @param replaceFirst
     */
    public void invite(String name)
    {
        parent.getGUI().displayInvitation(name);
    }

    /**
     * @param name
     * @param b
     */
    public void respondInvitation(String name, boolean accepted) throws ComponentException
    {
        if (translator == null) throw new ComponentException("Translator not set in ProgramManager");
        if (accepted) translator.sendAgreement(name);
    }
}
