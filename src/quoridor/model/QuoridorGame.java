package quoridor.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import com.sun.org.apache.xpath.internal.axes.OneStepIterator;

import quoridor.control.*;

/**
 * Represents the game core, that host the game loop and reference each object used in the game
 * @author P-L. de Villers, C. Lussiez, B. Brisson
 *
 */
public class QuoridorGame implements Serializable{

	/**
	 * The parameters of the game
	 */
	private Parameters params;
	
	
	/**
	 * The array of players in the order they're playing. First player to play is at index 0
	 */
	private Player[] players;
	
	/**
	 * The board used in the game
	 */
	private Board board;
	
	/**
	 * The controller that transmit data to the view in order to display the game
	 */
	private Controller controller;
	
	private int nowPlaying;

	/**
	 * Creates the game instance, and so start the application
	 * @param uiMode the mode the application must me started with
	 */
	public QuoridorGame(UIMode uiMode) {
		this.controller = new Controller(uiMode, this);
		this.controller.showMenuUI();
	}

	/**
	 * Init the game and then start the game loop. Interrupt it when the game detects someone won, and then stop the game and return to menu.
	 */
	public void startGameLoop() {
		this.initGame();
		
		this.controller.showGameUI();
		this.board.updateUI();
		
		Player winner = null;
		nowPlaying = 0;
		while (winner == null) {
			Player np = this.players[nowPlaying];
			this.controller.nextTurn(np);
			np.play();
			this.board.updateUI();
			winner = isGameFinished();
			nowPlaying++;
			if (nowPlaying == this.params.getNbJ()) nowPlaying = 0;
		}
		
		this.controller.displayWinner(winner);
		
		this.controller.showMenuUI();
	}

	/**
	 * Initialise the game
	 */
	private void initGame() {
		this.board = new Board(this);
		this.players = new Player[this.params.getNbJ()];
		
		for (int i = 0; i < this.players.length; i++) {
						
			if (this.params.getGMode().getPlayer(i) == GameMode.HUMAN) this.players[i] = new HumanPlayer(this, this.params.getPlayerNames()[i]);
			else this.players[i] = new AutoPlayer(this, this.params.getPlayerNames()[i]);
		}
		
		List<Player> playersAL= Arrays.asList(players);
		Collections.shuffle(playersAL);
		this.players = playersAL.toArray(new Player[playersAL.size()]);
		
		for (int i = 0; i < this.players.length; i++) {
			int[][] winCells = new int[9][2];
			
			if (params.getNbJ() == 2) {
				int x;
				if (i == 0) x = 0;
				else x = 8;
				for (int y = 0; y < 9; y++) {
					winCells[y][0] = x;
					winCells[y][1] = y;
				}
			}
			
			else if (params.getNbJ() == 4 && (i == 0 || i == 2)) {
				int x;
				if (i == 0) x = 0;
				else x = 8;
				for (int y = 0; y < 9; y++) {
					winCells[y][0] = x;
					winCells[y][1] = y;
				}
			}
			
			else if (params.getNbJ() == 4 && (i == 1 || i == 3)) {
				int y;
				if (i == 1) y = 8;
				else y = 0;
				for (int x = 0; x < 9; x++) {
					winCells[x][0] = x;
					winCells[x][1] = y;
				}
			}
			
			this.players[i].setWinCells(winCells);
		}
		
		
		if (this.params.getNbJ() == 2) {
			this.board.getCell(8, 4).setPawnPlayer(this.players[0]);
			this.board.getCell(0, 4).setPawnPlayer(this.players[1]);
		} else {
			this.board.getCell(8, 4).setPawnPlayer(this.players[0]);
			this.board.getCell(4, 0).setPawnPlayer(this.players[1]);
			this.board.getCell(0, 4).setPawnPlayer(this.players[2]);
			this.board.getCell(4, 8).setPawnPlayer(this.players[3]);
		}
	}
	
	/**
	 * Restarts the game and then start the game loop.
	 */
	public void restartGameLoop() {
		this.controller.showGameUI();
		this.board.updateUI();
		
		Player winner = null;
		while (winner == null) {
			Player np = this.players[nowPlaying];
			this.controller.nextTurn(np);
			np.play();
			this.board.updateUI();
			winner = isGameFinished();
			nowPlaying++;
			if (nowPlaying == this.params.getNbJ()) nowPlaying = 0;
		}
		
		this.controller.displayWinner(winner);
		
		this.controller.showMenuUI();
	}
	
	/**
	 * Gets the board used in the game
	 * @return the board, or null if the board hasn't already been initializated
	 */
	public Board getBoard() {
		return this.board;
	}

	/**
	 * Check if the game is finished, and if so gets the player who won
	 * @return null if game is not finished, else the player who won
	 */
	private Player isGameFinished() {
		Player ret = null;
		for (Player p : this.players) {
			for (int[] winCoords : p.getWinCells()) {
				if (Arrays.equals(this.board.getPlayerPawnPosition(p), winCoords)) ret = p;
			}
		}
		return ret;
	}

	/**
	 * Gets the controller used to display the game view
	 * @return the controller
	 */
	public Controller getController() {
		return this.controller;
	}

	/**
	 * Gets the parameters of the game
	 * @return the parameters object
	 */
	public Parameters getParams() {
		return this.params;
	}

	/**
	 * Sets the new parameters of the game
	 * @param params the new parameters object
	 */
	public void setParams(Parameters params) {
		this.params = params;
	}
	
	/**
	 * Creates a save of the now playing game
	 * @param name the name of the save
	 * @return true if it correctly saved, false if it failed
	 */
	public boolean save(String name) {
		Boolean ret = true;
		String path = "./saves/";
		File dir = new File(path);
		if (!dir.isDirectory()) dir.mkdir();
		File f = new File(path+name);
		if (f.isFile()) {		//If a file with this name already exist 
			ret = false;
		}else {
			f = new File(path);
			File[] paths = f.listFiles();
			if(paths!=null && paths.length>6) ret = false;
			if (ret) {
				try {
					String fileName = path+name;
					FileOutputStream fos = new FileOutputStream(fileName);
					ObjectOutputStream oos = new ObjectOutputStream(fos);
					oos.writeObject(this);
					oos.close();
				}catch (IOException e) {
					ret = false;
				}
			}
		}
		return ret;
	}
	
	/**
	 * Load a game
	 * @param name the name of the save to load
	 * @return true if it correctly load the save, false if it failed or if the named savefile doesn't exist
	 */
	public boolean load(String name) {
		Boolean ret = true;
		String path = "./saves/";
		try {
			FileInputStream fis = new FileInputStream(path+name);
			ObjectInputStream ois = new ObjectInputStream(fis);
			QuoridorGame me = (QuoridorGame) ois.readObject();
			this.board = me.getBoard();
			this.players = me.getPlayers();
			this.controller = me.getController();
			this.params = me.getParams();
			this.nowPlaying = me.getNowPlaying();
			ois.close();
		}catch (IOException e) {
			ret = false;
		}catch (ClassNotFoundException e) {
			ret = false;
		}		
		return ret;
	}

	/**
	 * Launch the game for the host
	 * @param name The host's name
	 * @throws IOException if the connexion between host and client is over
	 */
	public void Host(String name) throws IOException{
		ServerSocket serverSocket = new ServerSocket(49494, 1);	//We restrict the nb of client to 1 
		serverSocket.setSoTimeout(480000);	//The client has 5min to connect or an exception is launch
		Socket socket = serverSocket.accept();	
		socket.setSoTimeout(480000);	//5min to play same as above
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		String clientName = in.readLine();	//We get the client name
		String[] names = {name, clientName};
		setParams(new Parameters(2, GameMode.HH, names, 0)); 
		initGame();	
		
		this.controller.showGameUI();
		this.board.updateUI();
		int hostPlayer = 0;
		Player winner = null;
		if (this.players[1].getName().equals(name)) hostPlayer = 1;		//The starting player is chosen randomly so we get our player from the list
		ObjectOutputStream outGame = new ObjectOutputStream(socket.getOutputStream());
		ObjectInputStream inGame = null;
		outGame.writeObject(this);
		outGame.flush();
		nowPlaying = hostPlayer;
		Player np = this.players[nowPlaying];
		this.controller.nextTurn(np);
		while (winner == null) {
			if (nowPlaying == hostPlayer) {		//Host turn
				np.play();
			}else {		//Client turn
				try {	//We send the data
					outGame = new ObjectOutputStream(socket.getOutputStream());
					outGame.writeObject(this);
					outGame.flush();
				} catch (IOException e) {
					System.err.println("Impossible d'envoyer les données de jeu au client");
					this.controller.showMenuUI();
				}
				
				try {	//After the client played we get the data back and update the game
					inGame = new ObjectInputStream(socket.getInputStream());
					QuoridorGame me = (QuoridorGame) inGame.readObject();
					this.board = me.getBoard();
					this.players = me.getPlayers();
					this.controller = me.getController();
					this.params = me.getParams();
					this.nowPlaying = me.getNowPlaying();
				} catch (IOException | ClassNotFoundException e) {
					System.err.println("Impossible de recevoir les données du client");
					this.controller.showMenuUI();
				}
			}
			this.board.updateUI();	//After someone played we print the board for the host
			winner = isGameFinished();	//check if the game is over 
			nowPlaying++;
			if (nowPlaying == this.params.getNbJ()) nowPlaying = 0;
			np = this.players[nowPlaying];
			this.controller.nextTurn(np);
		}
		
		try {	//We send the data at the end of the game
			outGame = new ObjectOutputStream(socket.getOutputStream());
			outGame.writeObject(this);
			outGame.flush();
		} catch (IOException e) {
			System.err.println("Impossible d'envoyer les données de jeu au client");
			this.controller.showMenuUI();
		}
		inGame.close();
		in.close();
		outGame.close();
		socket.close();
		serverSocket.close();
		
		this.controller.displayWinner(winner);	//We show the winner to the host
		
		this.controller.showMenuUI();
	}
	
	/**
	 * Launch the game for the client
	 * @param name The client's name
	 * @throws IOException if the connexion between host and client is over
	 */
	public void Client(String name, String id) throws IOException{
		String serverAdress = InetAddress.getLocalHost().toString();	//Name + Address
		String[] temp = serverAdress.split("/");	//We get only the adress
		serverAdress = temp[temp.length-1];
		temp = serverAdress.split("(?<=\\.)");
		serverAdress = "";
		for (int i = 0; i < temp.length-1; i++) {	//Then we remove the last number and add host one
			serverAdress = serverAdress+temp[i];	//To get the host address
		}
		serverAdress = serverAdress + id;
		Socket clientSocket = new Socket(serverAdress, 49494);	//Connexion to the host
		clientSocket.setSoTimeout(480000);	
		PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
		out.println(name);	//We send our name
		out.flush();
		ObjectOutputStream outGame = null;	//And get the game
		ObjectInputStream inGame = new ObjectInputStream(clientSocket.getInputStream());
		QuoridorGame me;
		try {
			me = (QuoridorGame) inGame.readObject();
			this.board = me.getBoard();
			this.players = me.getPlayers();
			this.controller = me.getController();
			this.params = me.getParams();
			this.nowPlaying = me.getNowPlaying();
		} catch (ClassNotFoundException e1) {
			System.out.println("Les versions du jeu differentes");
		}
		Player winner = null;
		this.controller.showGameUI();
		this.board.updateUI();
		Player np = this.players[nowPlaying];
		this.controller.nextTurn(np);
		while (winner == null) {		//While the game isn't over
			try {	//We get the game's data and update the client's game
				inGame = new ObjectInputStream(clientSocket.getInputStream());
				me = (QuoridorGame) inGame.readObject();
				this.board = me.getBoard();
				this.players = me.getPlayers();
				this.controller = me.getController();
				this.params = me.getParams();
				this.nowPlaying = me.getNowPlaying();
			} catch (IOException | ClassNotFoundException e) {
				System.err.println("Impossible de recevoir les données de l'Hote");
				this.controller.showMenuUI();
			}
			
			winner = isGameFinished();	//We check if the host won
			if (winner == null) {
				this.board.updateUI();
				np = this.players[nowPlaying];
				this.controller.nextTurn(np);
				np.play();
				this.board.updateUI();
				if (nowPlaying == this.params.getNbJ()-1) {
					np = this.players[0];
				}else {
					np = this.players[nowPlaying+1];
				}
				this.controller.nextTurn(np);
				try {
					outGame = new ObjectOutputStream(clientSocket.getOutputStream());
					outGame.writeObject(this);
					outGame.flush();
				} catch (IOException e) {
					System.err.println("Impossible d'envoyer les données de jeu a l'hote");
					this.controller.showMenuUI();
				}
				winner = isGameFinished();	//We check if the client won
			}

		}
		
		try {	//We update our game for the last screen
			inGame = new ObjectInputStream(clientSocket.getInputStream());
			me = (QuoridorGame) inGame.readObject();
			this.board = me.getBoard();
			this.players = me.getPlayers();
			this.controller = me.getController();
			this.params = me.getParams();
			this.nowPlaying = me.getNowPlaying();
		} catch (IOException | ClassNotFoundException e) {
			System.err.println("Impossible de recevoir les données de l'Hote");
			this.controller.showMenuUI();
		}
		
		outGame.close();
		out.close();
		inGame.close();
		clientSocket.close();
		
		this.controller.displayWinner(winner);	//We print the winner to the client's sceen
		
		this.controller.showMenuUI();
	}
	
	/**
	 * Delete a game
	 * @param name the name of the save to delete
	 * @return true if it correctly delete the save, false if it failed or if the named save file doesn't exist
	 */
	public boolean deleteFile(String name) {
		Boolean ret = true;
		String path = "./saves/";
		File f = new File(path+name);
		if (f.isFile()) {		//If a file with this name already exist 
			f.delete();
		}else {
			ret = false;
		}
		return ret;
	}
	
	/**
	 * Get the player currently playing
	 * @return the index of the player
	 */
	public int getNowPlaying() {
		return this.nowPlaying;
	}
	
	/**
	 * Gets the player list in play order
	 * @return the list of the players objects
	 */
	public Player[] getPlayers() {
		return this.players;
	}

}