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
    private ProgramServerTranslator translator;
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
    
    /**
     * @param string
     */
    public void sendChosenName(String name)
    {
        translator.sendName(name);
    }

    public void setTranslator(ProgramServerTranslator translator)
    {
        this.translator = translator;
    }

    /**
     * @param replaceFirst
     */
    public void chooseOpponent(String oppname)
    {	
    	translator.sendOpponent(oppname);
    }
    
    public void askForList()
    {
        translator.sendListRequest();
    }
    
    public void startGame()
    {
        game = new GameManager(19, parent.getGUI());
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
    public void respondInvitation(String name, boolean accepted)
    {
        if (accepted) translator.sendAgreement(name);
    }
}
