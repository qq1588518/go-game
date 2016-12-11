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
    }
    
    
    @Override
    public void run()
    {
        do{
            black.makeMove();
            
        }while(true);
    }
}
