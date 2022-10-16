package test.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import quoridor.model.AutoPlayer;
import quoridor.model.HumanPlayer;
import quoridor.model.Player;
import quoridor.model.QuoridorGame;
import quoridor.model.UIMode;

public class TestPlayer {
	
	@Test
	public void testMovePawn() {
		QuoridorGame game = new QuoridorGame(UIMode.CONSOLE);
		Player p1 = new HumanPlayer(game, "test1");
		assertFalse(p1.checkMovePawn(-1,-1));
	}
	
	@Test
	public void testPlaceWall() {
		QuoridorGame game = new QuoridorGame(UIMode.CONSOLE);
		Player p1 = new AutoPlayer(game, "test2");
		assertFalse(p1.placeWall(-1,-1, true));
	}
	
	@Test
	public void testGetRemainingWallsNumber() {
		QuoridorGame game = new QuoridorGame(UIMode.CONSOLE);
		Player p1 = new HumanPlayer(game, "ee");
		assertTrue(p1.getRemainingWallsNumber() >= 0);
	}
}
