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
    private ProgramManager programManager;
    private ProgramServerTranslator translator;
    private GUIMediator frame;
    private SocketClient socket;
    
    Program()
    {
        programManager = new ProgramManager(this);

        translator = new ProgramServerTranslator(programManager);
        socket = new SocketClient(translator);
        frame = new GUIMediator(programManager);

        init();
    }
    
    private synchronized void init()
    {
        programManager.setTranslator(translator);
        translator.setSocket(socket);
        frame.setVisible(true);
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
