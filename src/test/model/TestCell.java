package test.model;
import org.junit.*;

import quoridor.model.Board;
import quoridor.model.HumanPlayer;
import quoridor.model.QuoridorGame;
import quoridor.model.UIMode;

import static org.junit.Assert.*;

public class TestCell {
	
	@Test
	public void testCheckTopWall() {
		QuoridorGame game = new QuoridorGame(UIMode.CONSOLE);
		Board b = new Board(game);
		b.getCell(0, 1).setWallH(true);
		assertTrue(b.getCell(1,1).checkTopWall());
		assertFalse(b.getCell(5, 5).checkTopWall());
	}
	
	@Test
	public void testCheckBottomWall() {
		QuoridorGame game = new QuoridorGame(UIMode.CONSOLE);
		Board b = new Board(game);
		b.getCell(1, 1).setWallH(true);
		assertTrue(b.getCell(1,1).checkBottomWall());
		assertFalse(b.getCell(5, 5).checkBottomWall());
	}
	
	@Test
	public void testCheckLeftWall() {
		QuoridorGame game = new QuoridorGame(UIMode.CONSOLE);
		Board b = new Board(game);
		b.getCell(1, 0).setWallV(true);
		assertTrue(b.getCell(1,1).checkLeftWall());
		assertFalse(b.getCell(5, 5).checkLeftWall());
	}
	
	@Test
	public void testCheckRightWall() {
		QuoridorGame game = new QuoridorGame(UIMode.CONSOLE);
		Board b = new Board(game);
		b.getCell(1, 1).setWallV(true);
		assertTrue(b.getCell(1,1).checkRightWall());
		assertFalse(b.getCell(5, 5).checkRightWall());
	}
	
	@Test
	public void testGetPawnPlayer() {
		QuoridorGame game = new QuoridorGame(UIMode.CONSOLE);
		Board b = new Board(game);
		b.getCell(1, 1).setPawnPlayer(new HumanPlayer(game, "ff"));
		assertNotNull(b.getCell(1,1).getPawnPlayer());
	}
}
