package quoridor.model;
import java.io.*;
import java.util.ArrayList;

import com.sun.org.apache.bcel.internal.generic.AALOAD;
import com.sun.org.apache.bcel.internal.generic.INEG;

import quoridor.astar.AStar;

/**
 * Represents a player, that can either be a {@link HumanPlayer} or a {@link AutoPlayer}.
 * @author P-L. de Villers, C. Lussiez, B. Brisson
 *
 */
public abstract class Player implements Serializable {

	/**
	 * The main game object
	 */
	QuoridorGame game;
	
	/**
	 * The number of walls remaining to the player. 9 at the beginning (or 5 for the 4 players mode)
	 */
	protected int nbWalls;
	
	protected final int MAX_NB_WALLS;
	
	/**
	 * The cells'coordinates where the player pawn must go to win
	 */
	private int[][] winCells;

	private String name;

	/**
	 * Creates the player
	 * @param game the main game object
	 * @param name the player name
	 */
	public Player(QuoridorGame game, String name) {
		this.game = game;
		this.name = name;
		if (game.getParams().getGMode() == GameMode.HA || game.getParams().getGMode() == GameMode.HH) {
			this.nbWalls = 9;
		}else {
			this.nbWalls = 5;
		}
		this.MAX_NB_WALLS = nbWalls;
	}
	
	/**
	 * Sets the win cells for the player
	 * @param winCells the array containing array of two coordinates for the win cells
	 */
	public void setWinCells(int[][] winCells) {
		this.winCells = winCells;
	}

	/**
	 * Make the player have his turn and play a move
	 */
	public abstract void play();

	/**
	 * Try to move the player pawn at the given coordinates
	 * Check if the move is allowed
	 * @param x the new x coordinate of the player's pawn
	 * @param y the new y coordinate of the player's pawn
	 * @return false if the move was not allowed
	 */
	public boolean checkMovePawn(int x, int y) {
		boolean ret = false;
		if (x<game.getBoard().getGridSize() && y<game.getBoard().getGridSize() && x>=0 && y>=0) {
			int[] myPos = game.getBoard().getPlayerPawnPosition(this);
			
			/*
			 * The Diagonals : 
			 * We verify the direction
			 * Then if no player is where we want to go
			 * Then if we have a wall behind the player, a player and no wall between the player and me
			 * We check that for the two possibilities (the || operation)
			 * Then we repeat it for the 4 diagonals
			 */
			if (myPos[0]-1 == x && myPos[1]+1 == y && game.getBoard().getCell(x, y).getPawnPlayer() == null &&	   
					((game.getBoard().getCell(myPos[0]-1, myPos[1]).checkTopWall() && game.getBoard().getCell(myPos[0]-1, myPos[1]).getPawnPlayer() != null && !game.getBoard().getCell(myPos[0], myPos[1]).checkTopWall() && !game.getBoard().getCell(myPos[0]-1, myPos[1]).checkRightWall()) 
					|| (game.getBoard().getCell(myPos[0], myPos[1]+1).checkRightWall() && game.getBoard().getCell(myPos[0], myPos[1]+1).getPawnPlayer() != null && !game.getBoard().getCell(myPos[0], myPos[1]).checkRightWall() && !game.getBoard().getCell(myPos[0], myPos[1]+1).checkTopWall()))) {	//If we ask to go Top Right   
				ret = true;
			}else if (myPos[0]-1 == x && myPos[1]-1 == y && game.getBoard().getCell(x, y).getPawnPlayer() == null &&
					((game.getBoard().getCell(myPos[0]-1, myPos[1]).checkTopWall() && game.getBoard().getCell(myPos[0]-1, myPos[1]).getPawnPlayer() != null && !game.getBoard().getCell(myPos[0], myPos[1]).checkTopWall() && !game.getBoard().getCell(myPos[0]-1, myPos[1]).checkLeftWall())
					|| (game.getBoard().getCell(myPos[0], myPos[1]-1).checkLeftWall() && game.getBoard().getCell(myPos[0], myPos[1]-1).getPawnPlayer() != null && !game.getBoard().getCell(myPos[0], myPos[1]).checkLeftWall() && !game.getBoard().getCell(myPos[0], myPos[1]-1).checkTopWall()))) {		//If we ask to go Top Left
				ret = true;
			}else if (myPos[0]+1 == x && myPos[1]-1 == y && game.getBoard().getCell(x, y).getPawnPlayer() == null &&
					((game.getBoard().getCell(myPos[0]+1, myPos[1]).checkBottomWall() && game.getBoard().getCell(myPos[0]+1, myPos[1]).getPawnPlayer() != null && !game.getBoard().getCell(myPos[0], myPos[1]).checkBottomWall() && !game.getBoard().getCell(myPos[0]+1, myPos[1]).checkLeftWall())
					|| (game.getBoard().getCell(myPos[0], myPos[1]-1).checkLeftWall() && game.getBoard().getCell(myPos[0], myPos[1]-1).getPawnPlayer() != null && !game.getBoard().getCell(myPos[0], myPos[1]).checkLeftWall()) && !game.getBoard().getCell(myPos[0], myPos[1]-1).checkBottomWall())) {		//If we ask to Down Left
				ret = true;
			}else if (myPos[0]+1 == x && myPos[1]+1 == y && game.getBoard().getCell(x, y).getPawnPlayer() == null &&
					((game.getBoard().getCell(myPos[0]+1, myPos[1]).checkBottomWall() && game.getBoard().getCell(myPos[0]+1, myPos[1]).getPawnPlayer() != null && !game.getBoard().getCell(myPos[0], myPos[1]).checkBottomWall() && !game.getBoard().getCell(myPos[0]+1, myPos[1]).checkRightWall())
					|| (game.getBoard().getCell(myPos[0], myPos[1]+1).checkRightWall() && game.getBoard().getCell(myPos[0], myPos[1]+1).getPawnPlayer() != null && !game.getBoard().getCell(myPos[0], myPos[1]).checkRightWall() && !game.getBoard().getCell(myPos[0], myPos[1]+1).checkBottomWall()))) {
				ret = true;
			}
			
			/*
			 * Now we check the 4 straight moves
			 * We verify the direction
			 * We check of there is a wall in that direction
			 * Then we check if there is no player on the cell
			 */
			else if (myPos[0]+1 == x && myPos[1] == y &&
					!game.getBoard().getCell(myPos[0], myPos[1]).checkBottomWall()) {		//If we ask to go down
				ret = game.getBoard().getCell(x, y).getPawnPlayer() == null;
			}else if (myPos[0]-1 == x && myPos[1] == y && 
					!game.getBoard().getCell(myPos[0], myPos[1]).checkTopWall()) {		//If we ask to go up
				ret = game.getBoard().getCell(x, y).getPawnPlayer() == null;
			}else if (myPos[1]+1 == y && myPos[0] == x &&
					!game.getBoard().getCell(myPos[0], myPos[1]).checkRightWall()) {	//If we ask to go right
				ret = game.getBoard().getCell(x, y).getPawnPlayer() == null;
			}else if (myPos[1]-1 == y && myPos[0] == x &&
					!game.getBoard().getCell(myPos[0], myPos[1]).checkLeftWall()) {	//If we ask to go left
				ret = game.getBoard().getCell(x, y).getPawnPlayer() == null;
			}
			
			/*
			 * Now we check the 4 straight jumps
			 * We verify the direction
			 * We check of there is a wall in that direction
			 * We check if there is a wall one cell further
			 * Then we check if there is no player on the cell
			 */
			else if (myPos[0]+2 == x && myPos[1] == y &&
					!game.getBoard().getCell(myPos[0], myPos[1]).checkBottomWall() &&
					!game.getBoard().getCell(myPos[0]+1, myPos[1]).checkBottomWall() &&
					game.getBoard().getCell(myPos[0]+1, myPos[1]).getPawnPlayer() != null &&
					game.getBoard().getCell(myPos[0]+2, myPos[1]).getPawnPlayer() == null) {		//If we jump down
				ret = game.getBoard().getCell(x, y).getPawnPlayer() == null;
			}else if (myPos[0]-2 == x && myPos[1] == y &&
					!game.getBoard().getCell(myPos[0], myPos[1]).checkTopWall() &&
					!game.getBoard().getCell(myPos[0]-1, myPos[1]).checkTopWall() &&
					game.getBoard().getCell(myPos[0]-1, myPos[1]).getPawnPlayer() != null &&
					game.getBoard().getCell(myPos[0]-2, myPos[1]).getPawnPlayer() == null) {		//If we jump up
				ret = game.getBoard().getCell(x, y).getPawnPlayer() == null;
			}else if (myPos[1]+2 == y && myPos[0] == x &&
					!game.getBoard().getCell(myPos[0], myPos[1]).checkRightWall() &&
					!game.getBoard().getCell(myPos[0], myPos[1]+1).checkRightWall() &&
					game.getBoard().getCell(myPos[0], myPos[1]+1).getPawnPlayer() != null &&
					game.getBoard().getCell(myPos[0], myPos[1]+2).getPawnPlayer() == null) {		//If we jump right
				ret = game.getBoard().getCell(x, y).getPawnPlayer() == null;
			}else if (myPos[1]-2 == y && myPos[0] == x &&
					!game.getBoard().getCell(myPos[0], myPos[1]).checkLeftWall() &&
					!game.getBoard().getCell(myPos[0], myPos[1]-1).checkLeftWall() &&
					game.getBoard().getCell(myPos[0], myPos[1]-1).getPawnPlayer() != null &&
					game.getBoard().getCell(myPos[0], myPos[1]-2).getPawnPlayer() == null) {		//If we jump left
				ret = game.getBoard().getCell(x, y).getPawnPlayer() == null;
			}
		}
		return ret;
	}

	/**
	 * Place the pawn to the given coordinates
	 * @param x the new x coordinate of the player's pawn
	 * @param y the new y coordinate of the player's pawn
	 * @return false if the move was not allowed
	 */
	public boolean placePawn(int x, int y) {
		boolean ret = this.checkMovePawn(x, y);
		if (ret) {
			int[] myPos = game.getBoard().getPlayerPawnPosition(this);
			game.getBoard().getCell(x, y).setPawnPlayer(this);
			game.getBoard().getCell(myPos[0], myPos[1]).setPawnPlayer(null);
		}
		return ret;
	}
	
	/**
	 * Try to place a player wall at the given coordinates
	 * Check if the move is allowed, and if so, modify board data
	 * @param x the x coordinate of the new wall
	 * @param y the y coordinate of the new wall
	 * @param vertical true if the wall is vertical, false if it is horizontal 
	 * @return null if we can't place the wall, the path after the wall if we can 
	 */
	public int[][] checkPlaceWall(int x, int y, boolean vertical) {
		boolean possible = false;
		//We check if there is a wall where we want to put one
		if (x<game.getBoard().getGridSize() && y<game.getBoard().getGridSize() && x>=0 && y>=0 && nbWalls>0) {
			Cell newCell = game.getBoard().getCell(x, y);
			if (vertical) {
				if (x == game.getBoard().getGridSize()-1) {
					possible = false;
				}else {
					possible = (!newCell.checkRightWall() && !newCell.isWallH() && !game.getBoard().getCell(x+1, y).isWallV());
				}
			}else {
				if (y == game.getBoard().getGridSize()-1) {
					possible = false;
				}else {
					possible = (!newCell.checkBottomWall() && !newCell.isWallV() && !game.getBoard().getCell(x, y+1).isWallH());
				}
			}
		}
		
		int[][] resAStar = null;	
		int bestPathLength = Integer.MAX_VALUE;
		int[][] temp = null;
		if (possible) {		//If we can place the wall we check if it will block the player
			if (vertical) {
				game.getBoard().getCell(x, y).setWallV(true);		//We place a wall for the test
			}else {
				game.getBoard().getCell(x, y).setWallH(true);
			}
			
			int startX = this.game.getBoard().getPlayerPawnPosition(this)[0];
			int startY = this.game.getBoard().getPlayerPawnPosition(this)[1];
			int[][] endPos = this.getWinCells();		//List of possible cells to win
			AStar algo = new AStar(this.game.getBoard().getGrid(), startX, startY, 0, 0);
			for (int i = 0; i < endPos.length; i++) {
				algo.newSearch(startX, startY, endPos[i][0], endPos[i][1]);
				temp = algo.getPath();
				if (temp != null && temp.length<bestPathLength) {
					resAStar = temp;
					bestPathLength = temp.length;
				}
				// If resAStar is not null it means there is at least one path for us, we take the best
			}
			
			temp = resAStar;
			int i = 0;
			while (temp != null && i<getEnnemies().size()) {		//Verify if no ennemi is blocked by ; If temp if null it means at least one player has no path
				int[][] resLocalAStar = null;
				Player player = getEnnemies().get(i);
				startX = player.game.getBoard().getPlayerPawnPosition(player)[0];
				startY = player.game.getBoard().getPlayerPawnPosition(player)[1];
				endPos = player.getWinCells();
				for (int j = 0; j < endPos.length; j++) {
					algo.newSearch(startX, startY, endPos[j][0], endPos[j][1]);
					resLocalAStar = algo.getPath();
					if (resLocalAStar != null) break; 	// If resAStar is not null it means there is a path for a player
				}
				temp = resLocalAStar;
				i++;
			}
			if (temp == null) resAStar = null;
			
			if (vertical) {
				game.getBoard().getCell(x, y).setWallV(false);	//We remove the wall we placed to test if it blocked the player
			}else {
				game.getBoard().getCell(x, y).setWallH(false);
			}
		}

		return resAStar;
	}
	
	/**
	 * Place the wall to the given coordinates
	 * @param x the x coordinate of the new wall
	 * @param y the y coordinate of the new wall
	 * @param vertical true if the wall is vertical, false if it is horizontal 
	 * @return false if we can't place the wall, true if we can 
	 */
	public boolean placeWall(int x, int y, boolean vertical) {
		boolean ret  = (checkPlaceWall(x, y, vertical) != null);
		if (ret) {
			if (vertical) {
				this.game.getBoard().getCell(x, y).setWallV(true);
			} else {
				this.game.getBoard().getCell(x, y).setWallH(true);
			}
			this.nbWalls--;
		}
		return ret;
	}
	
	/**
	 * Get the ennemies
	 * @return the ArrayList of all players
	 */
	protected ArrayList<Player> getEnnemies() {
		ArrayList<Player> ennemies = new ArrayList<>();
		for (int i = 0; i < game.getPlayers().length; i++) {
			if (!(game.getPlayers()[i] == this)) {
				ennemies.add(game.getPlayers()[i]);
			}
		}
		return ennemies;
	}
	
	/**
	 * Gets the number of walls remaining to the player
	 * @return the value of @link {@link Player#nbWalls}
	 */
	public int getRemainingWallsNumber() {
		return this.nbWalls;
	}
	
	/**
	 * Gets the cells where the player must be to win
	 * @return an array of x and y coordinates of the cells
	 */
	public int[][] getWinCells() {
		return this.winCells;
	}
	
	/**
	 * Get the x coordinate
	 * @return the x coordinate of this player
	 */
	public int getX() {
		return game.getBoard().getPlayerPawnPosition(this)[0];
	}
	
	/**
	 * Get the y coordinate
	 * @return the y coordinate of this player
	 */
	public int getY() {
		return game.getBoard().getPlayerPawnPosition(this)[1];
	}

	public String getName() {
		return this.name;
	}
}