package goserver.board;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import goserver.game.Color;
import goserver.game.board.Board;
import goserver.game.board.FieldOutOfBoardException;
import goserver.game.board.FieldType;

public class BoardTest {

	private int length;
	Board board;
	/**
	 * Creates board
	 */
	@Before
	public void setBoard(){
		length = 19;
		board = new Board(length);
		
	}
	
	/**
	 * Checks if moves are possible 
	 */
	@Test
	public void ifBlackMoveIsPossibleTest(){
		for(int i=0; i<length; i++){
			for(int j=0; j<length; j++){
				assertTrue(board.putStone(Color.BLACK, i, j));
				
			}
		}
	}
	
	@Test
	public void ifWhiteMoveIsPossibleTest(){
		for(int i=0; i<length; i++){
			for(int j=0; j<length; j++){
				assertTrue(board.putStone(Color.WHITE, i, j));
			}
		}
	}
	/**
	 * Checks if move is possible on not empty field, and if other fields are still empty.
	 */
	//NIEPRZECHODZI NIE WIEM CZEMU
	@Test
	public void ifMoveIsNotPossibleTest(){
		
		
		for(int i=0; i<length; i++){
			for(int j=0; j<length; j++){
				board.putStone(Color.BLACK, i, j);
				assertFalse(board.putStone(Color.BLACK, i, j));
				
				for(int l=0; l<length; l++){
					for(int k=0; k<length; k++){
						if(!(l==i && j==k)){
							
							try {
								assertTrue(board.getFieldType(l, k).equals(FieldType.EMPTY));
							} catch (FieldOutOfBoardException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							
						
						}
					}
				}
				board.getField(i, j).setEmpty();
				
			}
		}
	}
	
	/**
	 * Checks if move outside board is possible -- dont work, TODO!!!
	 */
//	@Test
//	public void moveOutsideBoardTest(){
//		for(int i=0; i<20; i++){
//			int j=-1;	
//			assertFalse(board.putStone(Color.BLACK, i, j));
//			j=19;
//			assertFalse(board.putStone(Color.BLACK, i, j));
//			j=-1;
//			assertFalse(board.putStone(Color.BLACK, j, i));
//			j=19;
//			assertFalse(board.putStone(Color.BLACK, j, i));
//		}
//	}
	
	@Test
	public void putWhiteOnBlackTest(){
		for(int i=0; i<19; i++)
			for(int j=0; j<19; j++){
				board.putStone(Color.BLACK, i, j);
				assertFalse(board.putStone(Color.WHITE, i, j));
				
			}
	}
}
