/**
 * 
 */
package goserver;

import java.util.Random;

/**
 * @author mk
 *
 */
public class GamePlay extends Thread
{
    Player black;
    Player white;
    Field[][] board;
    int n = 19;
    GamePlayState state;
    GamePlayTranslator translator; 
    
    /**
     * 
     */
    public GamePlay(Player first, Player second)
    {
        board = new Field[n][n];
        for (Field[] fields : board)
        {
            for (Field field : fields) field = Field.EMPTY;
        }
        Random r = new Random(); 
        boolean firstBlack = r.nextBoolean();
        black = firstBlack ? first : second;
        white = firstBlack ? second : first;
        
        translator = new GamePlayTranslator(black, white);
    }
    
    
    @Override
    public void run()
    {
        translator.notifyGameStart();
        state = new GamePlayStateBlackMoves(this);
    }
    
    public void makeMove(Player p, int x, int y)
    {
        state.makeMove(p, x, y);
    }
    
    public Player getBlack()
    {
        return black;
    }

    public Player getWhite()
    {
        return white;
    }

    /**
     * @param string
     */
    private void send(String message)
    {
        black.sendMessage(message);    
        white.sendMessage(message);
    }


    /**
     * TODO: sprawdzanie możliwości ruchu
     * @param black2
     * @param x
     * @param y
     */
    public boolean checkIfMovePossible(Color c, int x, int y)
    {
        if (board[x][y] == Field.EMPTY) return true;
        return false;
    }
    
    public void putStone(Color c, int x, int y)
    {
        board[x][y] = (c == Color.BLACK) ? Field.BLACK : Field.WHITE;
    }
    
}
