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
    private Program parent;
    private ProgramServerTranslator translator;
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

    
}
