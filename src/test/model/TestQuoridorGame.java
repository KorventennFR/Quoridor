package test.model;

import org.junit.*;

import quoridor.model.GameMode;
import quoridor.model.Parameters;
import quoridor.model.QuoridorGame;
import quoridor.model.UIMode;

import static org.junit.Assert.*;

public class TestQuoridorGame {
	
	@Test
	public void testGetBoard() {
		QuoridorGame game = new QuoridorGame(UIMode.CONSOLE);
		//assertNotNull(game.getBoard()); Non teste car initGame prive
	}
	
	@Test
	public void testGetController() {
		QuoridorGame game = new QuoridorGame(UIMode.CONSOLE);
		assertNotNull(game.getController());
	}
	
	@Test
	public void testGetParams() {
		QuoridorGame game = new QuoridorGame(UIMode.CONSOLE);
		String[] names = {"", ""};
		game.setParams(new Parameters(2, GameMode.HH, names, 1));
		assertEquals(GameMode.HH, game.getParams().getGMode());
		assertNotNull(game.getParams());
	}
	
	@Test
	public void testSaveAndLoad() {
		QuoridorGame game = new QuoridorGame(UIMode.CONSOLE);
		game.save("game_save_test.h1");
		QuoridorGame game2 = new QuoridorGame(UIMode.CONSOLE);
		game2.load("game_save_test.h1");
		assertEquals(game, game2);
	}
}
