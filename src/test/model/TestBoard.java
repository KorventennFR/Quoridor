package test.model;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import quoridor.model.AutoPlayer;
import quoridor.model.Board;
import quoridor.model.Player;
import quoridor.model.QuoridorGame;
import quoridor.model.UIMode;

public class TestBoard {

	@Test
	public void testBoard() {
		QuoridorGame game = new QuoridorGame(UIMode.CONSOLE);
		Board b = new Board(game);
		assertNotNull(b.getGrid());
		assertNotNull(b.getCell(0, 0));
		assertTrue(b.getGridSize() == 9);
	}
	
	@Test
	public void testPlayerPos() {
		QuoridorGame game = new QuoridorGame(UIMode.CONSOLE);
		Board b = new Board(game);
		Player p = new AutoPlayer(game, "");
		b.getCell(5, 5).setPawnPlayer(p);
		assertNotNull(b.getCell(5, 5).getPawnPlayer());
		int[] expect = {5,5};
		assertTrue(expect == b.getPlayerPawnPosition(p));
	}
}
