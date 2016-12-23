package goserver.server;

import static org.junit.Assert.*;

import java.net.Socket;
import java.util.Vector;

import org.junit.After;
import org.junit.Test;

import goserver.server.ClientHandler;
import goserver.server.Server;

public class ServerTest
{    
    Server s;
    
    @Test
    public void testServer()
    {
        Server s = new Server();
        assertNotNull(s);
    }
    
    @Test
    public void testStartServer()
    {
        s = new Server();
        s.startServer();
        assertTrue(s.getState());
    }
    
    @Test
    public void testStopServer()
    {
        s = new Server();
        s.startServer();
        s.stopServer();
        assertFalse(s.getState());
    }
    
    @Test
    public void testUpdate()
    {
        s = new Server();
        ClientHandler c = new ClientHandler(new Socket(), null);
        s.getClients().add(c);
        @SuppressWarnings("unchecked")
        Vector<ClientHandler> ch = ((Vector<ClientHandler>)s.getClients().clone());
        s.update(c, c);
        assertNotEquals(s.getClients(), ch);
    }
    
    @Test
    public void testGetPort()
    {
        s = new Server();
        assertEquals(5556, s.getPort());
    }
    
    @Test
    public void testSetPort()
    {
        s = new Server();
        s.setPort(6666);
        assertEquals(6666, s.getPort());
    }
    
    
    @After
    public void afterTest()
    {
        if(s != null) s.stopServer();
    }
    
    
}