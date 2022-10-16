package quoridor;

import java.util.Scanner;

import quoridor.model.QuoridorGame;
import quoridor.model.UIMode;

public class QuoridorApp {

	/**
	 * Main Launcher of the game
	 * @param args application launch arguments
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String s = "";
		while (!s.matches("[12]")) {
			System.out.println("  " + "\n"
					+ "		           ____  _    _  ____  _____  _____ _____   ____  _____" + "\n"
					+ "		          / __ \\| |  | |/ __ \\|  __ \\|_   _|  __ \\ / __ \\|  __ \\ " + "\n"
					+ "		         | |  | | |  | | |  | | |__) | | | | |  | | |  | | |__) |" + "\n"
					+ "		         | |  | | |  | | |  | |  _  /  | | | |  | | |  | |  _  / " + "\n"
					+ "		         | |__| | |__| | |__| | | \\ \\ _| |_| |__| | |__| | | \\ \\ " + "\n"
					+ "		         \\____\\_\\\\____/ \\____/|_|  \\_\\_____|_____/ \\____/|_|  \\_\\ " + "\n" + "\n"
					+ "    " + "\n" + "\n" + "\n" 
					+ "Choisir mode graphique :\n"
					+ "[1] : GUI\n"
					+ "[2] : Console UI");
			s = sc.nextLine();
		}
		
		if (s.equals("1")) new QuoridorGame(UIMode.GUI);
		else new QuoridorGame(UIMode.CONSOLE);
	}

}