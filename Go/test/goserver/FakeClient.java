package goserver;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import org.junit.Test;

public class FakeClient
{
    Socket socket = null;
    PrintWriter out = null;
    BufferedReader in = null;
    boolean running = true;
    
    public void listenSocket()
    {
        try {
            socket = new Socket("localhost", 5556);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println(in.readLine());
          }
          catch (UnknownHostException e) {
             System.out.println("Unknown host: localhost"); System.exit(1);
           }
           catch  (IOException e) {
             System.out.println("No I/O"); System.exit(1);
           }
            try
            {
                Thread.sleep(10);
            }
            catch (InterruptedException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }           
        
    }
    
    
    
//    public static void main(String args[])
//    {
//        FakeClient client = new FakeClient();
//                
//        client.listenSocket();
//    }
    
    @Test
    public void CommunicationTest(){
    	FakeClient client = new FakeClient();
    	client.listenSocket();
    	client.out.println("Message");
    	try {
			assertEquals(in.readLine(), "Message");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
