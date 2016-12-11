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
    private String playerList;
    
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
        	playerList = getPlayerList(input);
        	
        	parent.getGUIMediator().displayPlayersDialog(playerList, "Wybierz przeciwnika");        	
        }
        else if(input.startsWith("ENEMYOK")){
        	System.out.println("TE¯ S£YSZE!");
        }
        else if(input.startsWith("ENEMYBAD")){
        	System.out.println("Slysze");
        	playerList = getPlayerList(input);
           	parent.getGUIMediator().displayPlayersDialog(playerList, "Gracz juz nie istnieje");
        }
        else
        {
            System.out.println("Uknown server response");
        }
    }

    private String getPlayerList(String input) {
		// TODO Auto-generated method stub
		return input.replaceFirst("NAMEOK", "").replaceAll(",", "").replaceAll("\\[", "").replaceAll("\\]", "");
	}

	/* (non-Javadoc)
     * @see goclient.ServerTranslator#processOutcommingMessage(java.lang.String)
     */
    @Override
    public void processOutcommingMessage(String output)
    {
    	if(output.startsWith("NAME")){
    		//inaczej nie dziala :c
    		String nowyout = output.replaceFirst("NAME", "");
    		
    		parent.getSocket().send("USERNAME " + nowyout);
    	}
    	else if(output.startsWith("CHOOSEOPPONENT")){
    		
    		String nowyout = output.replaceFirst("CHOOSEOPPONENT", "");
    		parent.getSocket().send("ENEMY" + nowyout);
    	}
        
    }

    /**
     * @param name
     */
    
    
    public void setManager(ProgramManager manager)
    {
        this.manager = manager;
    }
}
