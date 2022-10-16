package quoridor.view;

import java.util.Scanner;

import quoridor.control.Controller;
import quoridor.model.Cell;
import quoridor.model.Player;

/**
 * Represents the Game UI in console UI Mode
 * @author P-L. de Villers, C. Lussiez, B. Brisson
 *
 */
public class GameConsoleUI extends GameUIView {

	/**
	 * Creates the UI and show it
	 * @param controller the controller used by the game to display the view
	 */
	public GameConsoleUI(Controller controller) {
		super(controller);
	}

	@Override
	public void updateBoard(Cell[][] grid, Player[] players) {
		System.out.println("\n\nQUORIDOR\n"
				+ "\n");
		for (int p = 0; p < players.length; p++) {
			System.out.print(" - " + players[p].getName() + " : " + players[p].getRemainingWallsNumber() + " murs restants.  ");
			if (p == 0) System.out.println("\u25B2 ");
			if (p == 1 && players.length == 2) System.out.println("\u25BC ");
			if (p == 1 && players.length == 4) System.out.println("\u25BA ");
			if (p == 2) System.out.println("\u25BC ");
			if (p == 3) System.out.println("\u25C4 ");
		}
		System.out.println("\n"
				+ this.boardToString(grid, players));
	}

	@Override
	public void newTurn(Player player) {
		System.out.print("C'est au tour de " + player.getName() + " de jouer !  ");
		Player[] players = controller.getGame().getPlayers();
		for (int p = 0; p < players.length; p++) {
			if (player.equals(players[p])) {
				if (p == 0) System.out.println("\u25B2 ");
				if (p == 1 && players.length == 2) System.out.println("\u25BC ");
				if (p == 1 && players.length == 4) System.out.println("\u25BA ");
				if (p == 2) System.out.println("\u25BC ");
				if (p == 3) System.out.println("\u25C4 ");
			}
		}
	}
	
	/**
	 * Gets a string that represent the board in order to print it to the console
	 * @return the board as a String
	 */
	private String boardToString(Cell[][] grid, Player[] players) {
		String ret = "  ";
		//Affichage des lettres
		for (int i = 0; i < 9; i++) {
			ret += "  " + (char) (i+97) + " ";
		}
		
		//Affichage première ligne
		ret += "\n  \u250C";
		for (int i = 0; i < 8; i++) { 
			ret = ret +"\u2500\u2500\u2500\u252C";
		}
		ret = ret +"\u2500\u2500\u2500\u2510\n";
		
		
		for (int x = 0; x < 8; x++) {
			ret += (x+1) + " ";
			for (int y = 0; y < 9; y++) {
				if (y>0 && grid[x][y].checkLeftWall()) {
					ret = ret + "\u2588 ";
				} else {
					ret = ret + "\u2502 ";
				}
				
				if (grid[x][y].getPawnPlayer() != null) {
					for (int p = 0; p < players.length; p++) {
						if (grid[x][y].getPawnPlayer().equals(players[p])) { 
							if (p == 0) ret = ret +"\u25B2 ";
							if (p == 1 && players.length == 2) ret = ret +"\u25BC ";
							if (p == 1 && players.length == 4) ret = ret +"\u25BA ";
							if (p == 2) ret = ret +"\u25BC ";
							if (p == 3) ret = ret +"\u25C4 ";
						}
					}
				}
				else {
					ret = ret +"  ";
				}
			}
			ret = ret +"\u2502\n  \u251C";
			for (int y = 0; y < 8; y++) { 
				if (grid[x][y].checkBottomWall()) {
					ret = ret + "\u2588\u2588\u2588";
				} else {
					ret = ret + "\u2500\u2500\u2500";
				}
				if (grid[x][y].isWallH() || grid[x][y].isWallV()) ret += "\u2588";
				else ret += "\u253c";
				
				
				
			}
			if (grid[x][8].checkBottomWall()) {
				ret = ret + "\u2588\u2588\u2588\u2524";
			} else {
				ret = ret +"\u2500\u2500\u2500\u2524";
			}
			ret = ret +"\n";
		}
		
		ret += "9 ";
		for (int y = 0; y < 9; y++) { 
			if (y>0 && grid[8][y].checkLeftWall()) {
				ret = ret + "\u2588 ";
			} else {
				ret = ret +"\u2502 ";
			}
			
			if (grid[8][y].getPawnPlayer() != null) { 
				for ( int p = 0;  p < players.length; p++) {
					if (grid[8][y].getPawnPlayer().equals(players[p])) { 
						if (p == 0) ret = ret +"\u25B2 ";
						if (p == 1 && players.length == 2) ret = ret +"\u25BC ";
						if (p == 1 && players.length == 4) ret = ret +"\u25BA ";
						if (p == 2) ret = ret +"\u25BC ";
						if (p == 3) ret = ret +"\u25C4 ";
					}
				}
			}
			else {
				ret = ret +"  ";
			}
		}
		
		ret = ret+"\u2502\n  \u2514";
		for (int l = 0; l < 8; l++) { 
			ret = ret +"\u2500\u2500\u2500\u2534";
		}
		ret = ret +"\u2500\u2500\u2500\u2518\n";
		return ret;
	}

	@Override
	public int[] humanMove() {
		int[] ret = new int[3];
		System.out.println("Pour savoir comment jouer tapez 'help','save' pour sauvergarder et 'quit' pour quitter");
		Scanner sc = new Scanner(System.in);
		System.out.print("> ");
		String s = sc.nextLine();
		s = s.replaceAll(" ", "");
		boolean saving = false;
		if (s.equals("save")) {
			saving = true;
		}else if (s.equals("quit")) {
			controller.showMenuUI();
		}else {
			while (!s.matches("(p[1-9][a-i])|(m[hv][1-9][a-i])") && !saving) {		
				System.out.print("Vous devez entrer une chaine de la forme\n"
						+ "  p 8e : déplace le pion en 8e\n"
						+ "  m h 4b : place un mur horizontal tel que la case 4b soit en haut à gauche du mur\n"
						+ "  m v 6c : place un mur vertical tel que la case 6c soit en haut à gauche du mur\n"
						+ "> ");
				s = sc.nextLine();
				s = s.replaceAll(" ", "");
				saving = s.equals("save");
				if(s.equals("quit")) controller.showMenuUI();;
			}
		}
		
		if (saving) {
			System.out.println("\n" + "\n" + "Entrez le nom de la sauvegarde: ");
			System.out.print("> ");
			String name = sc.nextLine();
			boolean saved = this.controller.getGame().save(name);
			if (saved) {
				System.out.println("Votre partie a été sauvegardée, reprise de la partie: " + "\n");
			}else {
				System.out.println("\n" + "La partie n'a pas pu être sauvegardée, soit vous n'avez plus d'emplacement de sauvegarde,"+ "\n" +
									" soit une sauvegarde avec le même nom existe déjà ");
			}
			ret = this.humanMove();
		}
		else if (s.charAt(0) == 'p') {
			ret[0] = 0;
			ret[1] = Character.getNumericValue(s.charAt(1))-1;
			ret[2] = s.charAt(2) - 97;	
		} else {
			if (s.charAt(1) == 'v') ret[0] = 1;
			else ret[0] = 2;
			ret[1] = Character.getNumericValue(s.charAt(2)) - 1;
			ret[2] = s.charAt(3) - 97;
		}
		
		return ret;
	}

	@Override
	public void displayWinner(Player winner) {
		System.out.println(winner.getName() + " a gagnï¿½ la partie");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
	}
}