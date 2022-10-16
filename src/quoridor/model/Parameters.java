package quoridor.model;
import java.io.*;
/**
 * Represents the parameters of the game
 * @author P-L. de Villers, C. Lussiez, B. Brisson
 *
 */
public class Parameters implements Serializable{

	/**
	 * The number of players in the game. It can be either 2 or 4 
	 */
	private final int nbJ;
	
	/**
	 * the game mode of this game
	 */
	private final GameMode gMode;
	
	/**
	 * The list of the name of the players
	 */
	private final String[] playerNames;
	
	/**
	 * The mode of the AI
	 * 0 for easy
	 * 1 for normal
	 */
	private final int AIMode;

	/**
	 * Creates the parameter object
	 * @param nbJ the number of players (2 or 4 only)
	 * @param gMode the game Mode we use 
	 * @param playerNames The player's name
	 * @param AIMode How strong is the AI
	 */
	public Parameters(int nbJ, GameMode gMode, String[] playerNames, int AIMode) {
		this.nbJ = nbJ;
		this.gMode = gMode;
		this.playerNames = playerNames;
		this.AIMode = AIMode;
	}

	/**
	 * Get the AIMode
	 * @return the 0 for easy and 1 for normal
	 */
	public int getAIMode() {
		return AIMode;
	}
	
	/**
	 * Gets the number of players in the game
	 * @return the number of players
	 */
	public int getNbJ() {
		return this.nbJ;
	}

	/**
	 * Gets the game mode of the game
	 * @return the game mode
	 */
	public GameMode getGMode() {
		return this.gMode;
	}
	
	/**
	 * Gets the names of the players
	 * @return the array containing the names
	 */
	public String[] getPlayerNames() {
		return this.playerNames;
	}

}