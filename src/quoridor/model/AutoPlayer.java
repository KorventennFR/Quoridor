package quoridor.model;

import java.util.ArrayList;

import quoridor.astar.AStar;

/**
 * Represents an automatically controlled player, that use Artificial Intelligence to choose what to play
 * @author P-L. de Villers, C. Lussiez, B. Brisson
 *
 */
public class AutoPlayer extends Player {
	 private int nbTour = 0;
	
	/**
	 * Create the auto-player and initialize its attributes
	 * @param game the main game object
	 */
	public AutoPlayer(QuoridorGame game, String name) {
		super(game, name);
	}

	
	/**
	 * Make the auto-player have his turn, choose a move and play it
	 */
	@Override
	public void play() {
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		int[] move = this.takedecision();
		boolean valide = false;
		
		if (game.getParams().getAIMode() == 0) {
			int random = (int) (Math.random()*100);
			if (random<15 && nbTour==-1) {		//On ne joue pas de random au debut
				move = this.randomMove(true);
			}
		}
		
		while (!valide) {
			if (move[0] == 0) {
				valide = placePawn(move[1], move[2]);
			}else if (move[0] == 1) {
				valide = placeWall(move[1], move[2], true);
			}else if (move[0] == 2) {
				valide = placeWall(move[1], move[2], false);
			}
			if (!valide) move = this.randomMove(false);	
		}
	}
	
	

	/**
	 * Take a decision for the AI
	 * @return The type of move and the positions
	 */
	private int[] takedecision() {
		int[] bestConfig = new int[3];	//int[0] --> 0 si V 1 si H (Wall)
										//int[1] --> xCord
										//int[2] --> yCord
		boolean movePawn = true;
		int[] ret = new int[3];
		int[][] currentAPath = getTheShortestPath(this);
		if(this.nbWalls > 0) {
			int[][] newEPath;
			int best = Integer.MAX_VALUE;
			Player bestPlayer = null;
			for (Player p : getEnnemies()) {		//We get the best ennemi
				newEPath = getTheShortestPath(p);
				if (newEPath.length < best) {
					best = newEPath.length;
					bestPlayer = p;
				}
			}
			int[][] currentEPath = getTheShortestPath(bestPlayer);
			bestConfig[0] = -1;
			//If the ennemy is about to jump over us
			if (((Math.abs(currentAPath[0][0]-currentEPath[0][0]) == 2 && currentAPath[0][1] == currentEPath[0][1]) || (Math.abs(currentAPath[0][1]-currentEPath[0][1]) == 2 && currentAPath[0][0] == currentEPath[0][0]))
					&& this.checkMovePawn(currentAPath[1][0], currentAPath[1][1]) && bestPlayer.checkMovePawn(currentEPath[1][0], currentEPath[1][1])) {
				bestConfig = BlockPlayer(bestPlayer);
				movePawn = bestConfig[0] == -1;
			}//If the ennemi is about to finish
			else if (currentEPath.length < 5) {
				bestConfig = BlockPlayer(bestPlayer);
				movePawn = bestConfig[0] == -1;
			}
			if (bestConfig[0] == -1) {			
				ArrayList<Cell> allCells = getAllCells();
				movePawn = false;
				best = Integer.MIN_VALUE;
				int tempA;
				int tempE;
				int[][] newAPath;
				int minRange = 1;	//The determine how good we want the walls to be
				if (currentEPath.length<6 || currentAPath.length<4) {
					minRange = 0;
				}else if (nbWalls<6) {
					minRange = 3;
				}else if (nbTour<6 && nbTour!=-1) {
					minRange = 2;
				}
				
				for (Cell cell : allCells) {
					newEPath = bestPlayer.checkPlaceWall(cell.getX(), cell.getY(), true);
					newAPath = this.checkPlaceWall(cell.getX(), cell.getY(), true);
					if (newEPath != null && newAPath != null) {
						tempA = (newAPath.length-currentAPath.length);		//The difference between our shortest path before and after this wall placement 
						tempE = (newEPath.length-currentEPath.length);		//The difference between his shortest path before and after this wall placement
						if (tempE-tempA > minRange && best <= (tempE-tempA)) {
							best = (tempE-tempA);
							bestConfig[0] = 0;
							bestConfig[1] = cell.getX();
							bestConfig[2] = cell.getY();
						}
					}

					newEPath = bestPlayer.checkPlaceWall(cell.getX(), cell.getY(), false);
					newAPath = this.checkPlaceWall(cell.getX(), cell.getY(), false);
					if (newAPath != null && newEPath != null) {
						tempA = (newAPath.length-currentAPath.length);		//The difference between our shortest path before and after this wall placement 
						tempE = (newEPath.length-currentEPath.length);		//The difference between his shortest path before and after this wall placement
						if (tempE-tempA > minRange && best <= (tempE-tempA)) {
							best = (tempE-tempA);
							bestConfig[0] = 1;
							bestConfig[1] = cell.getX();
							bestConfig[2] = cell.getY();
						}
					}
				}
				
				movePawn = best == Integer.MIN_VALUE;
			}
			
		}
		if (movePawn) {
			if (nbTour >= 0) nbTour++;
			if(nbTour>5) nbTour=-1;
			if (currentAPath.length == 2 && game.getBoard().getCell(currentAPath[1][0], currentAPath[1][1]).getPawnPlayer() != null) {
				if (checkMovePawn(this.getX()-1, this.getY()-1) && isWinningCell(this.getX()-1, this.getY()-1)) {
					ret[0] = 0;
					ret[1] = this.getX()-1;
					ret[2] = this.getY()-1;
				}else if (checkMovePawn(this.getX()+1, this.getY()+1) && isWinningCell(this.getX()+1, this.getY()+1)) {
					ret[0] = 0;
					ret[1] = this.getX()+1;
					ret[2] = this.getY()+1;
				}else if (checkMovePawn(this.getX()-1, this.getY()+1) && isWinningCell(this.getX()-1, this.getY()+1)) {
					ret[0] = 0;
					ret[1] = this.getX()-1;
					ret[2] = this.getY()+1;
				}else if (checkMovePawn(this.getX()+1, this.getY()-1) && isWinningCell(this.getX()+1, this.getY()-1)) {
					ret[0] = 0;
					ret[1] = this.getX()+1;
					ret[2] = this.getY()-1;
				}else {
					if (checkMovePawn(this.getX()+1, this.getY())) {
						ret[0] = 0;
						ret[1] = this.getX()+1;
						ret[2] = this.getY();
					}else if (checkMovePawn(this.getX()-1, this.getY())) {
						ret[0] = 0;
						ret[1] = this.getX()-1;
						ret[2] = this.getY();
					}else if (checkMovePawn(this.getX(), this.getY()-1)) {
						ret[0] = 0;
						ret[1] = this.getX();
						ret[2] = this.getY()-1;
					}else {
						ret[0] = 0;
						ret[1] = this.getX();
						ret[2] = this.getY()+1;
					}
				}
			}else if (currentAPath[1][0] == currentAPath[0][0] + 1) {		//If the AI decides to go down
				if (this.game.getBoard().getCell(this.getX()+1, this.getY()).getPawnPlayer() == null) {	//We check if the Cell is empty
					ret[0] = 0;
					ret[1] = currentAPath[1][0];
					ret[2] = currentAPath[1][1];
				}else {												//If there is a player we try to jump over
					if (this.checkMovePawn(this.getX()+2, this.getY())) {
						ret[0] = 0;
						ret[1] = currentAPath[1][0]+1;
						ret[2] = currentAPath[1][1];
					}else {											//If we can't we try to jump on the one of the two diagonal
						//Lower Right diagonal
						if (currentAPath[2][0] == currentAPath[0][0]+1 && currentAPath[2][1] == currentAPath[0][1]+1 && checkMovePawn(currentAPath[2][0], currentAPath[2][1])) {
							ret[0] = 0;
							ret[1] = currentAPath[2][0];
							ret[2] = currentAPath[2][1];
						//Lower left diagonal
						}else if (currentAPath[2][0] == currentAPath[0][0]+1 && currentAPath[2][1] == currentAPath[0][1]-1 && checkMovePawn(currentAPath[2][0], currentAPath[2][1])) {
							ret[0] = 0;
							ret[1] = currentAPath[2][0];
							ret[2] = currentAPath[2][1];
						}else {
							ret = this.randomMove(false);
						}
					}
				}
			}else if (currentAPath[1][0] == currentAPath[0][0] - 1) {	//If the AI decides to go up
				if (this.game.getBoard().getCell(this.getX()-1, this.getY()).getPawnPlayer() == null) {	//We check if the Cell is empty
					ret[0] = 0;
					ret[1] = currentAPath[1][0];
					ret[2] = currentAPath[1][1];
				}else {
					if (this.checkMovePawn(this.getX()-2, this.getY())) {
						ret[0] = 0;
						ret[1] = currentAPath[1][0]-1;
						ret[2] = currentAPath[1][1];
					}else {											//If we can't we try to jump on the one of the two diagonal
						//Upper left diagonal
						if (currentAPath[2][0] == currentAPath[0][0]-1 && currentAPath[2][1] == currentAPath[0][1]-1 && checkMovePawn(currentAPath[2][0], currentAPath[2][1])) {
							ret[0] = 0;
							ret[1] = currentAPath[2][0];
							ret[2] = currentAPath[2][1];
						//Upper Right diagonal
						}else if (currentAPath[2][0] == currentAPath[0][0]-1 && currentAPath[2][1] == currentAPath[0][1]+1 && checkMovePawn(currentAPath[2][0], currentAPath[2][1])) {
							ret[0] = 0;
							ret[1] = currentAPath[2][0];
							ret[2] = currentAPath[2][1];
						}else {
							ret = this.randomMove(false);
						}
					}
				}
			}else if (currentAPath[1][1] == currentAPath[0][1] + 1) {	//If the AI decides to go right
				if (this.game.getBoard().getCell(this.getX(), this.getY()+1).getPawnPlayer() == null) {	//We check if the Cell is empty
					ret[0] = 0;
					ret[1] = currentAPath[1][0];
					ret[2] = currentAPath[1][1];
				}else {
					if (this.checkMovePawn(this.getX(), this.getY()+2)) {
						ret[0] = 0;
						ret[1] = currentAPath[1][0];
						ret[2] = currentAPath[1][1]+1;
					}else {											//If we can't we try to jump on the one of the two diagonal
						//Lower Right diagonal
						if (currentAPath[2][0] == currentAPath[0][0]+1 && currentAPath[2][1] == currentAPath[0][1]+1 && checkMovePawn(currentAPath[2][0], currentAPath[2][1])) {
							ret[0] = 0;
							ret[1] = currentAPath[2][0];
							ret[2] = currentAPath[2][1];
						//Upper Right diagonal
						}else if (currentAPath[2][0] == currentAPath[0][0]-1 && currentAPath[2][1] == currentAPath[0][1]+1 && checkMovePawn(currentAPath[2][0], currentAPath[2][1])) {
							ret[0] = 0;
							ret[1] = currentAPath[2][0];
							ret[2] = currentAPath[2][1];
						}else {
							ret = this.randomMove(false);
						}
					}
				}
			}else if (currentAPath[1][1] == currentAPath[0][1] - 1) {	//If the AI decides to go left
				if (this.game.getBoard().getCell(this.getX(), this.getY()-1).getPawnPlayer() == null) {	//We check if the Cell is empty
					ret[0] = 0;
					ret[1] = currentAPath[1][0];
					ret[2] = currentAPath[1][1];
				}else {
					if (this.checkMovePawn(this.getX(), this.getY()-2)) {
						ret[0] = 0;
						ret[1] = currentAPath[1][0];
						ret[2] = currentAPath[1][1]-1;
					}else {											//If we can't we try to jump on the one of the two diagonal
						//Upper left diagonal
						if (currentAPath[2][0] == currentAPath[0][0]-1 && currentAPath[2][1] == currentAPath[0][1]-1 && checkMovePawn(currentAPath[2][0], currentAPath[2][1])) {
							ret[0] = 0;
							ret[1] = currentAPath[2][0];
							ret[2] = currentAPath[2][1];
						//Lower left diagonal
						}else if (currentAPath[2][0] == currentAPath[0][0]+1 && currentAPath[2][1] == currentAPath[0][1]-1 && checkMovePawn(currentAPath[2][0], currentAPath[2][1])) {
							ret[0] = 0;
							ret[1] = currentAPath[2][0];
							ret[2] = currentAPath[2][1];
						}else {
							ret = this.randomMove(false);
						}
					}
				}
			}
		}else {
			ret[0] = bestConfig[0]+1;	//We decide to place the wall
			ret[1] = bestConfig[1];
			ret[2] = bestConfig[2];
		}
		return ret;
	}
	
	/**
	 * Get the shortest path through all posibilities for a player
	 * @param p the to look for
	 * @return the shortest path
	 */
	private int[][] getTheShortestPath(Player p) {
		int shortest = Integer.MAX_VALUE;
		int[][] currentLength;
		int[][] finalRet = null;
		for (int i = 0; i < p.getWinCells().length; i++) {
			currentLength = AStar.staticSearch(game.getBoard().getGrid(), game.getBoard().getPlayerPawnPosition(p)[0], game.getBoard().getPlayerPawnPosition(p)[1], p.getWinCells()[i][0], p.getWinCells()[i][1]);
			if (currentLength != null && currentLength.length < shortest) {
				shortest = currentLength.length;
				finalRet = currentLength;
			}
		}
		return finalRet;
	}
	
	/**
	 * Get a list of Cells surrounding a player
	 * @param p the player
	 * @return the surrounding cells
	 */
	private ArrayList<Cell> getAllCells() {
		ArrayList<Cell> ret = new ArrayList<>();
		for (int i = 0; i < game.getBoard().getGrid().length; i++) {
			for (int j = 0; j < game.getBoard().getGrid()[i].length; j++) {
				ret.add(game.getBoard().getCell(i, j));
			}
		}
		return ret;
	}
	
	/**
	 * Place a wall in front of the player
	 * @param player to block
	 * @return the config to block the player
	 */
	private int[] BlockPlayer(Player player) {
		int pX = player.getX();
		int pY = player.getY();
		int[] bestConfig = new int[3];
		boolean valide = false;
		if (player.getWinCells()[3][0] == 0) {		//If the player is the one that starts at the bottom
			if (checkPlaceWall(pX-1, pY, false) != null) {
				bestConfig[0] = 1;
				bestConfig[1] = pX-1;
				bestConfig[2] = pY;
				valide = true;
			}else if (checkPlaceWall(pX-1, pY-1, false) != null) {
				bestConfig[0] = 1;
				bestConfig[1] = pX-1;
				bestConfig[2] = pY-1;
				valide = true;
			}
		}
		else if (player.getWinCells()[3][1] == game.getBoard().getGridSize()-1) {		//If the player is the one that starts at the left
			if (checkPlaceWall(pX, pY, true) != null) {
				bestConfig[0] = 0;
				bestConfig[1] = pX;
				bestConfig[2] = pY;
				valide = true;
			}else if (checkPlaceWall(pX-1, pY, true) != null) {
				bestConfig[0] = 0;
				bestConfig[1] = pX-1;
				bestConfig[2] = pY;
				valide = true;
			}
		}
		else if (player.getWinCells()[3][0] == game.getBoard().getGridSize()-1) {		//If the player is the one that starts at the top
			if (checkPlaceWall(pX, pY, false) != null) {
				bestConfig[0] = 1;
				bestConfig[1] = pX;
				bestConfig[2] = pY;
				valide = true;
			}else if (checkPlaceWall(pX, pY-1, false) != null) {
				bestConfig[0] = 1;
				bestConfig[1] = pX;
				bestConfig[2] = pY-1;
				valide = true;
			}
		}
		else if (player.getWinCells()[3][1] == 0) {		//If the player is the one that starts at the right
			if (checkPlaceWall(pX, pY-1, true) != null) {
				bestConfig[0] = 0;
				bestConfig[1] = pX;
				bestConfig[2] = pY-1;
				valide = true;
			}else if (checkPlaceWall(pX, pY-1, true) != null) {
				bestConfig[0] = 0;
				bestConfig[1] = pX;
				bestConfig[2] = pY-1;
				valide = true;
			}
		}
		if(!valide) bestConfig[0] = -1;
		return bestConfig;
	}

	/**
	 * Gets all the possible moves including placing walls
	 * @return an ArrayList of all moves
	 */
	private ArrayList<int[]> getAllpossible() {
		ArrayList<int[]> ret = new ArrayList<>();
		for (int i = 0; i < game.getBoard().getGridSize(); i++) {
			for (int j = 0; j < game.getBoard().getGridSize(); j++) {
				if (this.checkPlaceWall(i, j, true) != null) {
					int[] temp = new int[3];
					temp[0] = 1;
					temp[1] = i;
					temp[2] = j;
					ret.add(temp);
				}
				
				if (this.checkPlaceWall(i, j, false) != null) {
					int[] temp = new int[3];
					temp[0] = 2;
					temp[1] = i;
					temp[2] = j;
					ret.add(temp);
				}
				
				if (this.checkMovePawn(i, j)) {
					int[] temp = new int[3];
					temp[0] = 0;
					temp[1] = i;
					temp[2] = j;
					ret.add(temp);
				}
			}
		}
		return ret;
	}
	
	/**
	 * Give a ramdom moves through all the possible moves
	 * @param true if you allow the AI to place a Wall
	 * @return a possible move
	 */
	private int[] randomMove(Boolean wall) {
		ArrayList<int[]> possibilities = new ArrayList<>();
		if (wall) {
			possibilities = this.getAllpossible();
		}else {
			possibilities = this.getAllPlacePawn();
		}
		int randomIndex = (int)(Math.random()*possibilities.size());
		int[] ret = possibilities.get(randomIndex);
		return ret;
	}
	
	/**
	 * Gets all the possible cells where you can go
	 * @return an ArrayList of all moves
	 */
	private ArrayList<int[]> getAllPlacePawn() {
		ArrayList<int[]> ret = new ArrayList<>();
		for (int i = 0; i < game.getBoard().getGridSize(); i++) {
			for (int j = 0; j < game.getBoard().getGridSize(); j++) {
				if (this.checkMovePawn(i, j)) {
					int[] temp = new int[3];
					temp[0] = 0;
					temp[1] = i;
					temp[2] = j;
					ret.add(temp);
				}
			}
		}
		return ret;
	}
	
	/**
	 * Check if a Cell is a winning one
	 * @param x the x coordinate of the cell
	 * @param y the y coordinate of the cell
	 * @return true if the cell is a winning Cell
	 */
	private boolean isWinningCell(int x, int y) {
		boolean ret = false;
		for (int i = 0; i < this.getWinCells().length; i++) {
			if (this.getWinCells()[i][0] == x && this.getWinCells()[i][1] == y) {
				ret = true;
				break;
			}
		}
		return ret;
	}
}