package goserver.game;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import org.junit.Before;
import org.junit.Test;

import goserver.server.ClientHandler;

public class GamePlayTest {
	Game game;
	Socket socket;
	ClientHandler clientHandler;
	Player white, black;
	GamePlay gamePlay;
	
	
	
	@Before
	public void setUp(){
		game = new Game();
		socket = new Socket();
		clientHandler = new ClientHandler(socket, game);
		white = new Player("Mariusz", clientHandler);
		black = new Player("Stefan", clientHandler);
		gamePlay = new GamePlay(white, black);
		
	}
	
	
	
}