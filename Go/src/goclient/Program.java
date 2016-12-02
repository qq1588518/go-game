/**
 * 
 */
package goclient;

import java.awt.EventQueue;

import javax.swing.JFrame;

/**
 * @author mk
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
                JFrame frame = new GUIMediator();
            }
        });     
    }
    
}
