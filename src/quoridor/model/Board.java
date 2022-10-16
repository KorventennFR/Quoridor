package quoridor.model;

import java.io.Serializable;

/**
 * Represents the board used by the Quoridor game
 * it is mainly a 9 by 9 grid where you can place pawn on cells, and two-cells-long walls between the cells.
 * @author P-L. de Villers, C. Lussiez, B. Brisson
 *
 */
public class Board implements Serializable {

	/**
	 * The grid containing every cells
	 */
	private Cell[][] grid;
	
	/**
	 * The main game object
	 */
	private QuoridorGame game;

	/**
	 * Creates the board and initialize it
	 * @param game the main game object
	 */
	public Board(QuoridorGame game) {
		this.game = game;
		grid = new Cell[9][9];
		for (int x = 0; x < this.grid.length; x++) {
			for (int y = 0; y < this.grid.length; y++) {
				this.grid[x][y] = new Cell(this, x, y);
			}
		}			
	}

	/**
	 * Gets the cell localized at the given coordinates
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @return the cell
	 */
	public Cell getCell(int x, int y) {
		return this.grid[x][y];
	}

	/**
	 * Gets the coordinates of a given player pawn on the board
	 * @param player the player we want the pawn location of
	 * @return an int array containing the two coordinates, x and y (in this order)
	 */
	public int[] getPlayerPawnPosition(Player player) {
		int[] coor = new int[2];
		Boolean trouve = false;
		for (int i = 0; i < this.grid.length; i++) {
			for (int j = 0; j < this.grid.length; j++) {
				if (player.equals(this.grid[i][j].getPawnPlayer())) {
					coor[0] = i;
					coor[1] = j;
					trouve = true;
					break;
				}
			}
			if (trouve) break;
		}
		return coor;
	}

	/**
	 * Ask the controller to update the view with the actual state of the Board object
	 */
	public void updateUI() {
		this.game.getController().updateBoardUI(this.grid, this.game.getPlayers());
	}
	
	/**
	 * Gets the board size
	 * @return the grid length
	 */
	public int getGridSize() {
		return this.grid.length;
	}

	/**
	 * Gets the grid
	 * @return the array of cells representing the board
	 */
	public Cell[][] getGrid() {
		return this.grid;
	}
}