package goclient.program;

import java.awt.EventQueue;
import java.awt.Frame;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import goclient.gui.GUIMediator;

class Program
{   
    private final ProgramManager programManager;
    private final ProgramServerTranslator translator;
    private GUIMediator frame;
    private final SocketClient socket;
    
    private Program()
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

    public SocketClient getSocket()
    {
        return socket;
    }
    
    public GUIMediator getGUI()
    {
        return frame;
    }
    
    public void reset()
    {
    	frame.dispose();
    	frame = new GUIMediator(programManager);
    	frame.setVisible(true);
    }
    

    public static void main(String[] args) 
    {
        EventQueue.invokeLater(() -> {
            try
            {
                for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels())
                {
                    if ("Nimbus".equals(info.getName()))
                    {
                        UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (Exception ignored) {}

            new Program();
        });
    }
    
}