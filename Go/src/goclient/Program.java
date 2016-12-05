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
            GameManager manager = new GameManager(19);
            @Override
            public void run() {
                JFrame frame = new GUIMediator(manager);
                PlayerList plist = new PlayerList();
            }
        });     
    }
    
}
