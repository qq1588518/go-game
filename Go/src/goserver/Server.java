package goserver;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observer;
import java.util.Vector;
import java.util.Observable;
import java.io.*;

/**
 * @author mk
 *
 */

public class Server implements Observer
{
    private Socket socket;
    private Vector<ClientHandler> clients;
    private ServerSocket ssocket;  
    private StartServerThread sst; 
    private ClientHandler clientHandler;

    private int port;
    private boolean listening; 

    public Server() 
    {
        this.clients = new Vector<ClientHandler>();
        this.port = 5556; 
        this.listening = false;
    }

    public void startServer() 
    {
        if (!listening) 
        {
          this.sst = new StartServerThread();
          this.sst.start();
          this.listening = true;
        }
    }

    public void stopServer() 
    {
        if (listening && sst != null)
        {
            java.util.Enumeration<ClientHandler> e = this.clients.elements();
            while(e.hasMoreElements())
            {
                ClientHandler clientHandler = (ClientHandler)e.nextElement();
                clientHandler.stopClient();
            }
            this.sst.stopServerThread();
            this.listening = false;
        }
    }

    public void update(Observable observable, Object object) 
    {
        this.clients.removeElement(observable);
    }

    public int getPort() { return port; }

    public void setPort(int port) { this.port = port; }
    
    boolean getState() { return listening; }
    
    Vector<ClientHandler> getClients()
    {
        return clients;
    }

  
  /** This inner class will keep listening to incoming connections,
   *  and initiating a ClientThread object for each connection. */
  
    private class StartServerThread extends Thread 
    {
        private boolean listen;

        public StartServerThread() { this.listen = true; }

        public void run() 
        {
            try
            {
                //this.listen = false;
                Server.this.ssocket =  new ServerSocket(Server.this.port);
            }
            catch (IOException e)
            {
                System.out.println("Could not listen on port 5556");
                e.printStackTrace();
                System.exit(-1);
            }
            try 
            { 
                System.out.println("GO Server is now running");
                while (this.listen) 
                {
                     Server.this.socket = Server.this.ssocket.accept();
                     System.out.println("Client connected");
                     Server.this.clientHandler = new ClientHandler(Server.this.ssocket.accept());
                    
                     Thread t = new Thread(Server.this.clientHandler);
                     Server.this.clientHandler.addObserver(Server.this);
                     Server.this.clients.addElement(Server.this.clientHandler);
                     t.start();
                }
            } catch (IOException ioe) 
            {  
                System.out.println(ioe); 
                this.stopServerThread(); 
            }
        }

        public void stopServerThread()
        {
            this.listen = false; 
            
            try 
            { 
                if (ssocket != null) Server.this.ssocket.close(); 
            }
            catch (IOException ioe) 
            {
                System.out.println(ioe);
            }
        }
        
        public void finalize() throws Throwable 
        {
            try 
            {
              socket.close();
              System.out.println("zamykam");
              
            } 
            catch (IOException e) 
            {
              System.out.println("Could not close."); System.exit(-1);
            } finally
            {
                super.finalize();
            }
        }
    }
    
    public static void main(String[] args)
    {
        Server server = new Server();
        server.startServer();
        
    }
}