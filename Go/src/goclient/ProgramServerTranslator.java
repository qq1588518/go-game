/**
 * 
 */
package goclient;

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
            sendListRequest();
        }
        else if(input.startsWith("LIST"))
        {
            input = input.replaceFirst("LIST ", "");
            playerList = getPlayerList(input);
            
            parent.getGUI().displayPlayersDialog(playerList, "Please choose your opponent");   
        }
        else if(input.startsWith("GAMESTART")){
            
        }
        else if(input.startsWith("CHOOSEOPPONENTAGAIN")){
        	System.out.println("Slysze");
        	input = input.replaceFirst("CHOOSEOPPONENTAGAIN ", "");
        	playerList = getPlayerList(input);
           	parent.getGUI().displayPlayersDialog(playerList, "Chosen player is no longer avaliable. Please choose again.");
        }
        else
        {
            System.out.println("Uknown server response");
        }
    }

    private String getPlayerList(String input) {
		// TODO Auto-generated method stub
		return input.replaceAll(",", "").replaceAll("\\[", "").replaceAll("\\]", "");
	}

	/* (non-Javadoc)
     * @see goclient.ServerTranslator#processOutcommingMessage(java.lang.String)
     */
//    @Override
//    public void processOutcommingMessage(String output)
//    {
//    	if(output.startsWith("NAME")){
//    		//inaczej nie dziala :c
//    		String nowyout = output.replaceFirst("NAME", "");
//    		
//    		parent.getSocket().send("USERNAME " + nowyout);
//    	}
//    	else if(output.startsWith("CHOOSEOPPONENT")){
//    		
//    		String nowyout = output.replaceFirst("CHOOSEOPPONENT", "");
//    		parent.getSocket().send("ENEMY" + nowyout);
//    	}
//        
//    }

    /**
     * @param name
     */

    public void setManager(ProgramManager manager)
    {
        this.manager = manager;
    }

    /**
     * @param name
     */
    public void sendName(String name)
    {
        parent.getSocket().send("USERNAME " + name);
    }

    /**
     * @param oppname
     */
    public void sendOpponent(String oppname)
    {
        parent.getSocket().send("OPPONENT " + oppname);
    }

    /**
     * 
     */
    public void sendListRequest()
    {
       parent.getSocket().send("LIST");
    }
}
