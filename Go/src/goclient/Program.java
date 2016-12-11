/**
 * 
 */
package goclient;

import java.awt.EventQueue;

/**
 * TODO: Jak rozwiązać zmianę translatorów i managerów?
 *
 */
public class Program
{   
    ProgramManager programManager;
    ProgramServerTranslator translator;
    GUIMediator frame;
    SocketClient socket;
    
    Program()
    {
        programManager = new ProgramManager(this);
        translator = new ProgramServerTranslator(this);
        socket = new SocketClient(this);
        frame = new GUIMediator(this);

        init();
    }
    
    private void init()
    {
        programManager.setTranslator(translator);
        translator.setManager(programManager);
        socket.start();
    }
    
    
    public ProgramManager getProgramManager()
    {
        return programManager;
    }
    
    public ProgramServerTranslator getTranslator()
    {
        return translator;
    }
    
    public SocketClient getSocket()
    {
        return socket;
    }
    
    public GUIMediator getGUI()
    {
        return frame;
    }
    
    /**
     * @param args
     */
    public static void main(String[] args) 
    {
        EventQueue.invokeLater(new Runnable() 
        {
            @Override
            public void run() {
                    new Program(); 
            }
        });     
    }
    
}
