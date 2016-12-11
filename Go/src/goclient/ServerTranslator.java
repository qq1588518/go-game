/**
 * 
 */
package goclient;

/**
 * @author mk
 *
 */
public abstract class ServerTranslator
{
   abstract public void processIncommingMessage(String input);
   abstract public void processOutcommingMessage(String output);

}