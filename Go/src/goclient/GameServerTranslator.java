/**
 * 
 */
package goclient;

/**
 * @author mk
 *
 */
public class GameServerTranslator extends ServerTranslator
{
    GameManager manager;
    /**
     * 
     */
    public GameServerTranslator(GameManager manager)
    {
       this.manager = manager;
    }
    
    public void processIncommingMessage(String input)
    {
        if (input.startsWith("SERVERMESSAGE: "))
        {
            input = input.replaceFirst("SERVERMESSAGE: ", "[Server]: ");
            manager.displayMessage(input);
        }
    }
}
