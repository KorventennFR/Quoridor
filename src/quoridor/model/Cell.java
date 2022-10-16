package quoridor.model;

import java.io.Serializable;

/**
 * Represents one cell on the board
 * @author P-L. de Villers, C. Lussiez, B. Brisson
 *
 */
public class Cell implements Serializable{

	/**
	 * If a pawn is on this cell, the player who owns this pawn will be referenced. Else it will contain null
	 */
	private Player pawnPlayer;
	
	/**
	 * True if an horizontal wall is placed at the south east of this cell :
	 * <code><br><br>
	 * [x] [ ] <br>
	 * ------- <br>
	 * [ ] [ ] </code>
	 */
	private boolean wallH;
	
	/**
	 * True if a vertical wall is placed at the south east of this cell :
	 * <code><br><br>
	 * [x]|[ ] <br>
	 *    |    <br>
	 * [ ]|[ ] </code>
	 */
	private boolean wallV;
	
	/**
	 * x coordinate of the cell
	 */
	private int x;
	
	/**
	 * y coordinate of the cell
	 */
	private int y;
	
	/**
	 * The board that contains this cell
	 */
	private Board board;

	/**
	 * Creates a void cell
	 * @param board the board tha contains this cell
	 */
	public Cell(Board board, int x, int y) {
		this.wallH = false;
		this.wallV = false;
		if (board == null) throw new IllegalArgumentException("Cell(): Board must not be null");
		this.board = board;
		this.x = x;
		this.y = y;
	}

	/**
	 * Sets this cell to contain the given Player's pawn
	 * @param pawnPlayer the player that own the pawn placed on the cell. Null if there is no pawn anymore.
	 */
	public void setPawnPlayer(Player pawnPlayer) {
		this.pawnPlayer = pawnPlayer;
	}

	/**
	 * Sets if there is an horizontal wall at the south east of the cell
	 * @see #wallH
	 * @param wallH true if there is a wall
	 */
	public void setWallH(boolean wallH) {
		this.wallH = wallH;
	}

	/**
	 * Sets if there is a vertical wall at the south east of the cell
	 * @see #wallV
	 * @param wallV true if there is a wall
	 */
	public void setWallV(boolean wallV) {
		this.wallV = wallV;
	}

	/**
	 * Gets the player who owns the pawn on the cell
	 * @return the player or null if there is no pawn on this cell
	 */
	public Player getPawnPlayer() {
		return this.pawnPlayer;
	}

	/**
	 * Gets if there is an horizontal wall at the south east of the cell
	 * @see #wallH
	 * @return true if there is one
	 */
	public boolean isWallH() {
		return this.wallH;
	}

	/**
	 * Gets if there is a vertical wall at the south east of the cell
	 * @see #wallV
	 * @return true if there is one
	 */
	public boolean isWallV() {
		return this.wallV;
	}

	/**
	 * Check if there is a wall at the top edge of the cell
	 * @return true if there is one
	 */
	public boolean checkTopWall() {
		boolean ret = false;
		if (this.x == 0) {
			ret = true;
		}else if (this.y == 0) {
			ret = this.board.getCell(this.x-1, this.y).isWallH();
		}else {
			ret = (this.board.getCell(this.x-1, this.y).isWallH() || this.board.getCell(this.x-1, this.y-1).isWallH());
		}
		return ret;
	}

	/**
	 * Check if there is a wall at the bottom edge of the cell
	 * @return true if there is one
	 */
	public boolean checkBottomWall() {
		boolean ret = false;
		if (this.x == this.board.getGridSize()-1) {
			ret = true;
		}else if (this.y == 0) {
			ret = this.wallH;
		}else {
			ret = (this.wallH || this.board.getCell(this.x, this.y-1).isWallH());
		}
		return ret;
	}
	
	/**
	 * Check if there is a wall at the left edge of the cell
	 * @return true if there is one
	 */
	public boolean checkLeftWall() {
		boolean ret = false;
		if (this.y == 0) {
			ret = true;
		}else if (this.x == 0) {
			ret = this.board.getCell(this.x, this.y-1).isWallV();
		}else {
			ret = (this.board.getCell(this.x, this.y-1).isWallV() || this.board.getCell(this.x-1, this.y-1).isWallV());
		}
		return ret;
	}

	/**
	 * Check if there is a wall at the right edge of the cell
	 * @return true if there is one
	 */
	public boolean checkRightWall() {
		boolean ret = false;
		if (y == this.board.getGridSize()-1) {
			ret = true;
		}
		else if (this.x == 0) {
			ret = this.wallV;
		}else {
			ret = (this.wallV || this.board.getCell(this.x-1, this.y).isWallV());
		}
		return ret;
	}

	/**
	 * Get the x coordinate
	 * @return x
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Get the y coordinate
	 * @return y
	 */
	public int getY() {
		return y;
	}
}
