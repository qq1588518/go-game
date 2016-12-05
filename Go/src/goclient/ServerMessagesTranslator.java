/**
 * 
 */
package goclient;

/**
 * @author mk
 *
 */
public class ServerMessagesTranslator
{
    GameManager manager;
    /**
     * 
     */
    public ServerMessagesTranslator(GameManager manager)
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
