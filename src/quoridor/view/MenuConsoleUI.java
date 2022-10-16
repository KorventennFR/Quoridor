package quoridor.view;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Scanner;

import quoridor.control.Controller;
import quoridor.model.GameMode;
import quoridor.model.Parameters;
import quoridor.model.QuoridorGame;

/**
 * Represents the Menu UI in console UI Mode
 * @author P-L. de Villers, C. Lussiez, B. Brisson
 *
 */
public class MenuConsoleUI extends MenuUIView {

	/**
	 * Creates the UI and show it
	 * @param controller the controller used by the game to display the view
	 */
	public MenuConsoleUI(Controller controller) {
		super(controller);
		this.mainMenu();
	}
	
	/**
	 * Display the main menu
	 */
	private void mainMenu() {
		Scanner sc = new Scanner(System.in);
		String s = "";
		while (!(s.equals("1") || s.equals("2") || s.equals("3") || s.equals("4"))) {;
			System.out.print("\n\n\nQUORIDOR\n"
					+ "\n"
					+ " - 1 - Nouvelle partie\n"
					+ " - 2 - Charger une partie\n"
					+ " - 3 - Jouer en Reseau local\n"
					+ " - 4 - Quitter\n"
					+ "> ");
			s = sc.nextLine();
		}
		int rep = Integer.parseInt(s);
		switch (rep) {
		case 1:
			this.nbJMenu();
			break;
			
		case 2:
			this.loadGameMenu();
			break;
		
		case 3:
			this.localNetMenu();
			break;
			
		case 4:
			System.exit(0);
			break;

		default:
			break;
		}
	}

	/**
	 * Display the local network menu
	 */
	private void localNetMenu() {
		Scanner sc = new Scanner(System.in);
		String s = "";
		while (!(s.equals("1") || s.equals("2") || s.equals("3"))) {
			System.out.print("\n\n\nQUORIDOR\n"
					+ "\n"
					+ " - 1 - Hôte\n"
					+ " - 2 - Client\n"
					+ " - 3 - Retour\n"
					+ "> ");
			s = sc.nextLine();
		}
		int rep = Integer.parseInt(s);
		switch (rep) {
		case 1:
			this.hostMenu();
			break;
			
		case 2:
			this.clientMenu();
			break;
			
		case 3:
			this.mainMenu();
			break;

		default:
			break;
		}
	}
	
	/**
	 * Display the host local network menu
	 */
	private void hostMenu() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Entrez votre nom: ");	//We get the host name
		String name = sc.next();
		System.out.print("Votre ID est: ");
		try {
			String add = InetAddress.getLocalHost().toString();
			String[] temp = add.split("\\.");
			add = temp[temp.length-1];	//We get the last number of the host local IP address
			System.out.println(add);	//It becomes the host ID
		} catch (UnknownHostException e1) {
			System.out.println("Impossible de récupérer votre ID");
			this.mainMenu();
		}
		System.out.println("Le client doit entrer cet ID pour se connecter.");
		try {
			this.controller.getGame().Host(name);	//We launch the game for the host
		}catch (SocketTimeoutException e) {
			System.out.println("Aucun client ne s'est connecté à temp");
			this.localNetMenu();
		} catch (IOException e) {
			System.out.println("La connexion au client a échoué");
			this.localNetMenu();
		}
	}
	
	/**
	 * Display the client local network menu
	 */
	private void clientMenu() {
		Scanner sc = new Scanner(System.in);
		String add = "";
		boolean validInterger = false;
		while (!validInterger) {
			validInterger = true;
			System.out.print("\n\n\nQUORIDOR\n"
					+ "\n"
					+ " Entrez l'ID de l'hôte : \n"
					+ "> ");
			add = sc.nextLine();	//We get the Host ID
			try {
				Integer.parseInt(add);
			} catch (Exception e) {
				validInterger = false;
				System.out.println("Veuillez entrer l'ID de l'hôte, celui ci doit être un nombre");
			}
		}
		
		System.out.println("Entrez votre nom: ");
		String name = sc.next();	//We get the client name
		
		System.out.println("Connexion à l'hôte veuillez patienter...");
		
		try {
			this.controller.getGame().Client(name, add);	//We launch the game for the client
		} catch (IOException e) {
			System.out.println("La connexion au serveur a échoué");
			this.localNetMenu();
		}
	}
	
	/**
	 * display the number of player choice menu
	 */
	private void nbJMenu() {
		Scanner sc = new Scanner(System.in);
		String s = "";
		while (!(s.equals("1") || s.equals("2") || s.equals("3"))) {
			System.out.print("\n\n\nQUORIDOR\n"
					+ "\n"
					+ " - 1 - partie � 2 joueurs\n"
					+ " - 2 - partie � 4 joueurs\n"
					+ " - 3 - Retour\n"
					+ "> ");
			s = sc.nextLine();
		}
		int rep = Integer.parseInt(s);
		switch (rep) {
		case 1: 
			this.modeMenu(2);
			break;
			
		case 2:
			this.modeMenu(4);
			break;
			
		case 3:
			this.mainMenu();
			break;

		default:
			break;
		}
	}

	/**
	 * Display the game mode choice menu
	 * @param nbJ the number of players
	 */
	private void modeMenu(int nbJ) {
		Scanner sc = new Scanner(System.in);
		String s = "";
		String ia = "";
		int AIMode = 2;
		if (nbJ == 2) {
			while (!(s.equals("1") || s.equals("2") || s.equals("3"))) {
				System.out.print("\n\n\nQUORIDOR\n"
						+ "\n"
						+ " - 1 - Humain vs Humain\n"
						+ " - 2 - Humain vs IA\n"
						+ " - 3 - Retour\n"
						+ "> ");
				s = sc.nextLine();
			}
			
			int rep = Integer.parseInt(s);
			if (s.equals("2")) {
				while (!(ia.equals("1") || ia.equals("2") || ia.equals("3"))) {
					System.out.print("\n\n\nQUORIDOR\n"
							+ "\n"
							+ " - 1 - Facile\n"
							+ " - 2 - Normal\n"
							+ " - 3 - Retour\n"
							+ "> ");
					ia = sc.nextLine();
				}
				AIMode = Integer.parseInt(ia);
			}
			AIMode--;
			//If we ask to return
			if(AIMode==2) this.nbJMenu();
			switch (rep) {
			case 1:
				this.controller.getGame().setParams(new Parameters(nbJ, GameMode.HH, this.askPlayerNames(nbJ, 2), AIMode)); 
						
				break;
				
			case 2:
				this.controller.getGame().setParams(new Parameters(nbJ, GameMode.HA, this.askPlayerNames(nbJ, 1), AIMode));
				break;
				
			case 3:
				this.nbJMenu();
				break;

			default:
				break;
			}
			
		} else {
			while (!(s.equals("1") || s.equals("2") || s.equals("3") || s.equals("4") || s.equals("5"))) {
				System.out.print("\n\n\nQUORIDOR\n"
						+ "\n"
						+ " - 1 - 4 Humains\n"
						+ " - 2 - 3 Humains et 1 IA\n"
						+ " - 3 - 2 Humains et 2 IA\n"
						+ " - 4 - 1 Humain et 3 IA\n"
						+ " - 5 - Retour\n"
						+ "> ");
				s = sc.nextLine();
			}
			
			if (!s.equals("1") && !s.equals("5")) {
				while (!(ia.equals("1") || ia.equals("2") || ia.equals("3"))) {
					System.out.print("\n\n\nQUORIDOR\n"
							+ "\n"
							+ " - 1 - Facile\n"
							+ " - 2 - Normal\n"
							+ " - 3 - Retour\n"
							+ "> ");
					ia = sc.nextLine();
				}
				AIMode = Integer.parseInt(ia);
			}
			
			AIMode--;
			if (AIMode == 2) this.nbJMenu();
			
			int rep = Integer.parseInt(s);
			switch (rep) {
			case 1:
				this.controller.getGame().setParams(new Parameters(nbJ, GameMode.HHHH, this.askPlayerNames(nbJ, 4), AIMode));
				break;
				
			case 2:
				this.controller.getGame().setParams(new Parameters(nbJ, GameMode.HHHA, this.askPlayerNames(nbJ, 3), AIMode));
				break;
				
			case 3:
				this.controller.getGame().setParams(new Parameters(nbJ, GameMode.HHAA, this.askPlayerNames(nbJ, 2), AIMode));
				break;
				
			case 4:
				this.controller.getGame().setParams(new Parameters(nbJ, GameMode.HAAA, this.askPlayerNames(nbJ, 1), AIMode));
				break;
				
			case 5:
				this.nbJMenu();
				break;

			default:
				break;
			}
		}
		
		this.controller.getGame().startGameLoop();
	}

	/**
	 * Print the load menu
	 */
	private void loadGameMenu() {
		Scanner sc = new Scanner(System.in);
		String s = "";
		String[] saves = this.getAllSaves();
		if (saves==null || saves.length<1) {
			System.out.println("\n" + "Il n'y a pas de partie a charger");
			this.mainMenu();
		}else {
			System.out.println("\n" + "Voici la liste des partie sauvegarde");
			int i = 1;
			for (String string : saves) {
				System.out.println(i + "  " + string);
				i++;
			}
			boolean charge = false;
			while (!charge) {
				System.out.println("\n" + "Entrez le numero de la partie a charger, 0 pour revenir au menu: ");
				s = sc.next();
				if (s.replaceAll(" ", "").equals("0")) {
					this.mainMenu();
				}
				try {
					int index = Integer.parseInt(s);
					controller.getGame().load(saves[index-1]);
					charge = true;
				} catch (Exception e) {
					System.out.println("Le numero de partie n'est pas valide");
				}
			}
		}
		this.controller.getGame().restartGameLoop();
	}
	
	private String[] askPlayerNames(int nbJ, int nbH) {
		String[] playerNames = new String[nbJ];
		Scanner sc = new Scanner(System.in);
		int i = 0;
		while (i < nbH) {
			System.out.print("\n\n\nQUORIDOR\n"
					+ "\n"
					+ " - nom du joueur " + (i+1) + "\n"
					+ "> ");
			playerNames[i] = sc.nextLine();
			i++;
		}
		int ia = 1;
		while(i < nbJ) {
			playerNames[i] = "IA " + ia;
			i++;
			ia++;
		}
		return playerNames;
	}
}