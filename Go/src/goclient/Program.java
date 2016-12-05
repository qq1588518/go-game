/**
 * 
 */
package goclient;

import java.awt.EventQueue;

import javax.swing.JFrame;

/**
 * TODO: tu póki co nic nie ma, ale pewnie powinny być podpięte różne elementy klienta
 * tj. GUI, komunikacja z serwerem, jakaś logika (sprawdzanie ruchu?) może też...
 *
 */
public class Program
{
    
    /**
     * @param args
     */
    public static void main(String[] args) 
    {
        
        
        EventQueue.invokeLater(new Runnable() 
        {
            @Override
            public void run() {
                
                GUIMediator frame = new GUIMediator();
                GameManager manager = new GameManager(19, frame);
                ServerMessagesTranslator translator = new ServerMessagesTranslator(manager);
                SocketClient socket = new SocketClient(translator);
                socket.start();
                //PlayerList plist = new PlayerList();
            }
        });     
    }
    
}
