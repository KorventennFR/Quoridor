package quoridor.control;

import quoridor.view.*;

import java.io.Serializable;

import quoridor.model.*;

/**
 * Control data exchange between the view and the model
 * @author P-L. de Villers, C. Lussiez, B. Brisson
 *
 */
public class Controller implements Serializable{
 
	/**
	 * The object used to make the UI for the game.
	 */
	private GameUIView gameUIView;
	
	/**
	 * The object used to make the UI for the menu.
	 */
	private MenuUIView menuUIView;
	
	/**
	 * The mode used to display user interface. Either in console or in a Graphical environnment
	 */
	private UIMode mode;
	
	/**
	 * The main game object
	 */
	private QuoridorGame game;

	/**
	 * Creates the controller and then show the menu view
	 * @param uiMode The UI mode used to display the application
	 * @param game The main game object
	 */
	public Controller(UIMode uiMode, QuoridorGame game) {
		this.mode = uiMode;
		this.game = game;
	}

	/**
	 * Update the board displayed to the screen
	 * @param grid the game's board
	 */
	public void updateBoardUI(Cell[][] grid, Player[] players) {
		this.gameUIView.updateBoard(grid, players);
	}

	/**
	 * Gets the main game object
	 * @return the game
	 */
	public QuoridorGame getGame() {
		return this.game;
	}
	
	/**
	 * Display the ui to the menu to the screen
	 */
	public void showMenuUI() {
		if (this.mode.equals(UIMode.CONSOLE)) this.menuUIView = new MenuConsoleUI(this);
		else this.menuUIView = new MenuGUI(this);
	}

	/**
	 * Display the ui of the game to the screen
	 */
	public void showGameUI() {
		if (this.mode.equals(UIMode.CONSOLE)) this.gameUIView = new GameConsoleUI(this);
		else this.gameUIView = new GameGUI(this);
	}
	
	/**
	 * Notify the view it's the turn of the given player
	 * @param player the player who now has turn
	 */
	public void nextTurn(Player player) {
		this.gameUIView.newTurn(player);
	}
	
	/**
	 * Get the human move
	 * @return the move <br>
	 * ret[0] gives the type of move (0 = pawn move, 1 = V wall, 2 = H wall) <br>
	 * ret[1] x pos <br>
	 * ret[2] y pos 
	 */
	public int[] humanMove() {
		return this.gameUIView.humanMove();
	}
	
	/**
	 * Display the winner of the game to the screen
	 * @param winner the player who won the game
	 */
	public void displayWinner(Player winner) {
		this.gameUIView.displayWinner(winner);
		
	}

}