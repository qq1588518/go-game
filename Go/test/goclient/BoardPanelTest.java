/**
 * 
 */
package goclient;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import goclient.game.GameManager;
import goclient.game.StoneType;
import goclient.game.WrongCoordsException;
import goclient.gui.BoardPanel;
import goclient.gui.GUIMediator;
import goclient.program.Program;
import goclient.program.ProgramManager;

/**
 * @author mk
 *
 */
public class BoardPanelTest
{
	BoardPanel boardPanel;
	GUIMediator mediator;
	static private int boardSize = 19;
	ProgramManager programManager;
	GameManager gm;
	
	@Before
	public void initBoardPanel(){
		
		programManager = new ProgramManager(new Program());
		
		mediator = new GUIMediator(programManager);
		
		boardPanel = new BoardPanel(mediator);
	}
    @Test
    public void addBlackStoneTest()
    {
    	
    	for(int i=0; i<boardSize; i++){
    		for(int j=0; j<boardSize; j++){
    			try {
    				boardPanel.addStone(StoneType.BLACK, i, j);
    				assertTrue(boardPanel.getStones().elementAt(0).type.equals(StoneType.BLACK));
    				assertTrue(boardPanel.getStones().elementAt(0).getX() == i);
    				assertTrue(boardPanel.getStones().elementAt(0).getY() == j);
    				boardPanel.removeStone(i, j, StoneType.BLACK);
    				
    			} catch (WrongCoordsException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    		}
    	}
    }
    @Test
    public void addWhiteStoneTest()
    {
    	
    	for(int i=0; i<boardSize; i++){
    		for(int j=0; j<boardSize; j++){
    			try {
    				boardPanel.addStone(StoneType.WHITE, i, j);
    				assertTrue(boardPanel.getStones().elementAt(0).type.equals(StoneType.WHITE));
    				assertTrue(boardPanel.getStones().elementAt(0).getX() == i);
    				assertTrue(boardPanel.getStones().elementAt(0).getY() == j);
    				boardPanel.removeStone(i, j, StoneType.WHITE);
    				
    			} catch (WrongCoordsException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    		}
    	}
    }
    
    
}