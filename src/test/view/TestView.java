package test.view;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import quoridor.control.Controller;
import quoridor.model.QuoridorGame;
import quoridor.model.UIMode;
import quoridor.view.MenuConsoleUI;
import quoridor.view.MenuGUI;


public class TestView {
	@Test
	public void testView() {
		QuoridorGame game = new QuoridorGame(UIMode.CONSOLE);
		Controller control1 = new Controller(UIMode.CONSOLE, game);
		Controller control2 = new Controller(UIMode.GUI, game);
		MenuConsoleUI console = new MenuConsoleUI(control1);
		MenuGUI gui = new MenuGUI(control2);
		assertEquals(console.getController(), control1);
		assertEquals(gui.getController(), control2);
	}
}
