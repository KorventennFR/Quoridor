package quoridor.model;

/**
 * Represents a mode of the game. It can be a mode with 2 players, or 4
 * Different mode is different number of human and auto players
 * In each mode, there are at least one human player
 * @author P-L. de Villers, C. Lussiez, B. Brisson
 *
 */
public enum GameMode {	
	
	/**
	 * Human vs Auto mode
	 */
	HA(GameMode.HUMAN, GameMode.AUTO),
	
	/**
	 * Human vs Human mode
	 */
	HH(GameMode.HUMAN, GameMode.HUMAN),
	
	/**
	 * Human vs Human vs Human vs Human mode
	 */
	HHHH(GameMode.HUMAN, GameMode.HUMAN, GameMode.HUMAN, GameMode.HUMAN),
	/**
	 * Human vs Human vs Human vs Auto mode
	 */
	HHHA(GameMode.HUMAN, GameMode.HUMAN, GameMode.HUMAN, GameMode.AUTO),
	
	/**
	 * Human vs Human vs Auto vs Auto mode
	 */
	HHAA(GameMode.HUMAN, GameMode.HUMAN, GameMode.AUTO, GameMode.AUTO),
	
	/**
	 * Human vs Auto vs Auto vs Auto mode
	 */
	HAAA(GameMode.HUMAN, GameMode.AUTO, GameMode.AUTO, GameMode.AUTO);
	
	public static final int HUMAN = 0;
	public static final int AUTO = 1;
	
	private int[] players;
	
	private GameMode(int i0, int i1) {
		this.players = new int[2];
		this.players[0] = i0;
		this.players[1] = i1;
	}
	
	private GameMode(int i0, int i1, int i2, int i3) {
		this.players = new int[4];
		this.players[0] = i0;
		this.players[1] = i1;
		this.players[2] = i2;
		this.players[3] = i3;
	}
	
	public int getPlayer(int index) {
		int ret = -1;
		if (index >= 0 && index < this.players.length) ret = this.players[index];
		return ret;
	}
}