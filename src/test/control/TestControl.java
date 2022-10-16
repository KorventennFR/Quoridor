package test.control;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import quoridor.model.*;
import quoridor.view.*;
import quoridor.control.*;
import quoridor.*;
import quoridor.astar.*;

public class TestControl {
	@Test
	public void testControl() {
		QuoridorGame game = new QuoridorGame(UIMode.CONSOLE);
		Controller control = new Controller(UIMode.CONSOLE, game);
		assertEquals(control.getGame(), game);
	}
}
