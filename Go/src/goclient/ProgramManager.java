/**
 * 
 */
package goclient;

import java.util.Vector;

/**
 * @author mk
 *
 */
public class ProgramManager
{
    private Program parent;
    private ProgramServerTranslator translator;
    //private Vector<PlayerList> playerLists;
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
       System.out.println(this.toString());
        
        translator.chooseName(name);
    }
    
    public void setTranslator(ProgramServerTranslator translator)
    {
        this.translator = translator;
    }

    /**
     * @param replaceFirst
     */
    public void chooseOpponent(String list)
    {
                
    }

	public void askForPlayerList() {
		// TODO Auto-generated method stub
		
	}
    
}
