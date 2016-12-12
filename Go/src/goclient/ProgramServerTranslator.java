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
            parent.getGUI().displayPlayersDialog(input, "Please choose your opponent");   
        }
        else if(input.startsWith("INVITATIONFROM"))
        {
            manager.invite(input.replaceFirst("INVITATIONFROM ", ""));
        }
        else if(input.startsWith("GAMESTART"))
        {
            input = input.replaceFirst("GAMESTART ", "").trim();
            if (input.equals("BLACK")) manager.startGame(StoneType.BLACK);
            else if (input.equals("WHITE")) manager.startGame(StoneType.WHITE);          
        }
        else if(input.startsWith("CHOOSEOPPONENTAGAIN")){
        	input = input.replaceFirst("CHOOSEOPPONENTAGAIN ", "");
           	parent.getGUI().displayPlayersDialog(input, "Chosen player is no longer avaliable. Please choose again.");
        }
        else
        {
            System.out.println("Uknown server response");
        }
    }
    
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

    /**
     * @param name
     */
    public void sendAgreement(String name)
    {
        parent.getSocket().send("INVAGREE " + name);
    }


}
