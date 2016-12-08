/**
 * 
 */
package goclient;

import java.io.PrintWriter;

/**
 * @author mk
 *
 */
public class ProgramServerTranslator extends ServerTranslator
{
    private ProgramManager manager;
    private Program parent;
    
    public ProgramServerTranslator(Program program)
    {
        this.parent = program;
        manager = program.getProgramManager();
    }
    
    public void processIncommingMessage(String input) 
    {
        if(input.startsWith("SETNAME"))
        {
            manager.askForName("Please choose your nickname.");
        }
        else if(input.startsWith("NAMETAKEN"))
        {
            manager.askForName("This nickname is already taken. Please choose another one.");
        }
        else if(input.startsWith("NAMEOK"))
        {
        	String playerList = input.replaceFirst("NAMEOK", "").replaceAll(",", "").replaceAll("\\[", "").replaceAll("\\]", "");
        	
        	parent.getGUIMediator().displayPlayersDialog(playerList);;
        	
        }
        else if(input.startsWith("CHOOSEOPPONENT"))
        {
            manager.chooseOpponent(input.replaceFirst("CHOOSEOPPONENT", ""));
        }
        else
        {
            System.out.println("Uknown server response");
        }
    }

    /* (non-Javadoc)
     * @see goclient.ServerTranslator#processOutcommingMessage(java.lang.String)
     */
    @Override
    public void processOutcommingMessage(String output)
    {
        // TODO Auto-generated method stub
        
    }

    /**
     * @param name
     */
    public void chooseName(String name)
    {
        parent.getSocket().send("USERNAME: " + name);
        
    }
    
    public void setManager(ProgramManager manager)
    {
        this.manager = manager;
    }
}
