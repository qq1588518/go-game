/**
 * 
 */
package goclient;

import java.awt.EventQueue;

/**
 * Start of program and innitialize few methods
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
    
    public GUIMediator getGUIMediator(){
    	return frame;
    }
    
    public ProgramManager getProgramManager()
    {
        return programManager;
    }
    
    
    /**
     * 
     * @return ProgramServerTranslator
     */
    public ProgramServerTranslator getTranslator()
    {
        return translator;
    }
    /**
     * 
     * @return SocketClient
     */
    public SocketClient getSocket()
    {
        return socket;
    }
    
    /**
     * 
     * @return GUIMediator
     */
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
