package quoridor.view;

import java.io.Serializable;

import quoridor.control.*;
import quoridor.model.*;

/**
 * An abstract representation of what the game UI must do
 * @author P-L. de Villers, C. Lussiez, B. Brisson
 *
 */
public abstract class GameUIView implements Serializable{

	/**
	 * The controller used to get data from and to send interactions to the model
	 */
	Controller controller;

	/**
	 * Creates the GameUIView
	 * @param controller the controller of the application
	 */
	public GameUIView(Controller controller) {
		this.controller = controller;
	}

	/**
	 * Update the board display
	 * @param grid The grid that must be displayed as the new board state
	 */
	public abstract void updateBoard(Cell[][] grid, Player[] players);

	/**
	 * Notify the UI that a new turn has started and that it is now the turn of the given player
	 * @param player the next player to play
	 */
	public abstract void newTurn(Player player);

	/**
	 * Get the human move
	 * @return the move <br>
	 * ret[0] gives the type of move (0 = pawn move, 1 = V wall, 2 = H wall) <br>
	 * ret[1] x pos <br>
	 * ret[2] y pos 
	 */
	public abstract int[] humanMove();

	/**
	 * Display to the screen who won the game
	 * @param winner
	 */
	public abstract void displayWinner(Player winner);

	
}