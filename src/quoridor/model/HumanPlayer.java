package quoridor.model;

/**
 * Represents a hand-controlled player.
 * The player choose himself moves and play it using UI
 * @author P-L. de Villers, C. Lussiez, B. Brisson
 *
 */
public class HumanPlayer extends Player {

	/**
	 * Creates the human player
	 * @param game the main game object
	 */
	public HumanPlayer(QuoridorGame game, String name) {
		super(game, name);
	}

	/**
	 * Make the player have his turn and play a move
	 */
	@Override
	public void play(){
		boolean valide = false;
		while (!valide) {
			int[] move = game.getController().humanMove();
			if (move[0] == 0) {
				valide = placePawn(move[1], move[2]);
			}else if (move[0] == 1) {
				valide = placeWall(move[1], move[2], true);
			}else if (move[0] == 2) {
				valide = placeWall(move[1], move[2], false);
			}
			if(!valide) System.out.println("\n" + "Vous ne pouvez pas jouer ce coup veuillez en saisir un nouveau" + "\n");
		}
	}

}