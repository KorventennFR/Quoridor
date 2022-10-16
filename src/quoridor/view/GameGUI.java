package quoridor.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import quoridor.control.*;
import quoridor.model.Cell;
import quoridor.model.Player;
import sun.security.util.Length;

/**
 * Represents the Game UI in graphical UI Mode
 * @author P-L. de Villers, C. Lussiez, B. Brisson
 *
 */
public class GameGUI extends GameUIView {

	private static final Font FONT = new Font("Thaoma",Font.BOLD,35);
	private JFrame frame;
	private JTextField saisie;
	
	JFrame gameFrame;
	JTable board;
	
	ArrayList<JLabel> playerLbls;
	
	boolean quit;
	
	int[] move;

	/**
	 * Creates the UI and show it
	 * @param controller the controller used by the game to display the view
	 */
	public GameGUI(Controller controller) {
		super(controller);
		this.quit = false;
		
		this.gameFrame = new JFrame("Quoridor");
		this.gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		JPanel menuPanel = new JPanel();
		menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.LINE_AXIS));
		
		
		JButton quitterBtn = new JButton("Retour");
        quitterBtn.setFont(GameGUI.FONT);
        quitterBtn.setHorizontalTextPosition(JButton.CENTER);
        quitterBtn.setVerticalTextPosition(JButton.CENTER);
        quitterBtn.setIcon(new ImageIcon(getClass().getResource("/images/ButtonSimple.png")));
        quitterBtn.setHorizontalTextPosition(JButton.CENTER);
        quitterBtn.setPressedIcon(new ImageIcon(getClass().getResource( "/images/ButtonPressed.png" ))); 
        quitterBtn.setRolloverIcon(new ImageIcon(getClass().getResource( "/images/ButtonRollOver.png" ))); 
        quitterBtn.setBorderPainted(false);
        quitterBtn.setFocusPainted( false );
        quitterBtn.setOpaque(true);
        quitterBtn.setBackground(Color.WHITE);
        quitterBtn.setForeground(Color.WHITE);
        quitterBtn.setContentAreaFilled(false);
        
        quitterBtn.addActionListener(new QuitBtnActionListener(this));
        
        menuPanel.add(quitterBtn);
        
        
        
        JButton saveBtn = new JButton("Save");
        saveBtn.setName("Sauvegarder");
        saveBtn.setFont(GameGUI.FONT);
        saveBtn.setHorizontalTextPosition(JButton.CENTER);
        saveBtn.setVerticalTextPosition(JButton.CENTER);
        saveBtn.setIcon(new ImageIcon(getClass().getResource("/images/ButtonSimple.png")));
        saveBtn.setHorizontalTextPosition(JButton.CENTER);
        saveBtn.setPressedIcon(new ImageIcon(getClass().getResource( "/images/ButtonPressed.png" ))); 
        saveBtn.setRolloverIcon(new ImageIcon(getClass().getResource( "/images/ButtonRollOver.png" ))); 
        saveBtn.setBorderPainted(false);
        saveBtn.setFocusPainted( false );
        saveBtn.setOpaque(true);
        saveBtn.setBackground(Color.WHITE);
        saveBtn.setForeground(Color.WHITE);
        saveBtn.setContentAreaFilled(false);
        
        saveBtn.addActionListener(new SaveBtnActionListener(this));
        
        menuPanel.add(saveBtn);
        
        
        JPanel playerPanel = new JPanel();
        playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.LINE_AXIS));
        
        
        playerLbls = new ArrayList<JLabel>();
        for (Player p : this.controller.getGame().getPlayers()) {
        	JLabel lbl = new JLabel(p.getName());
        	lbl.setFont(GameGUI.FONT);
        	playerLbls.add(lbl);
        }

        try {
        	
	        if (this.controller.getGame().getPlayers().length == 2) {
	        	playerLbls.get(0).setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/color_BLACK.png"))));
	        	playerLbls.get(1).setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/color_WHITE.png"))));
	        } else {
	        	playerLbls.get(0).setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/color_BLUE.png"))));
	        	playerLbls.get(1).setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/color_RED.png"))));
	        	playerLbls.get(2).setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/color_YELLOW.png"))));
	        	playerLbls.get(3).setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/color_GREEN.png"))));
	        }
	        
        } catch (IOException e) {
        	e.printStackTrace();
        }
        
        playerPanel.add(Box.createRigidArea(new Dimension(30, 0)));
        for (JLabel label : playerLbls) {
        	playerPanel.add(label);
        	playerPanel.add(Box.createRigidArea(new Dimension(30, 0)));
        	
        }        
        
        JPanel boardPanel = new JPanel();
		
		GameBoardModel bModel = new GameBoardModel(controller);
		this.board = new JTable(bModel);
		
		this.board.setRowHeight(80);
		for (int i = 0; i < this.board.getColumnCount(); i++) {
			this.board.getColumnModel().getColumn(i).setMinWidth(80);
		}
		this.board.setShowGrid(false);
		this.board.setIntercellSpacing(new Dimension(0, 0));
		
		this.board.addMouseListener(new BoardMouseListener(this.board, this));
		
		this.gameFrame.setLayout(new BoxLayout(this.gameFrame.getContentPane(), BoxLayout.PAGE_AXIS));
		
		boardPanel.add(this.board);
		
		this.gameFrame.getContentPane().add(menuPanel);
		this.gameFrame.getContentPane().add(playerPanel);
		this.gameFrame.getContentPane().add(boardPanel);
		this.gameFrame.pack();
		this.gameFrame.setVisible(true);
		this.gameFrame.repaint();
	}
	
	public void saveClic() {
		this.gameFrame = new JFrame("Quoridor Sauvegarder");
		this.gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		frame = new JFrame();
		JPanel panel = new JPanel(new GridLayout(2,1));
		
		JPanel titreSaisie = new JPanel();
		titreSaisie.setBackground(Color.WHITE);
		titreSaisie.setLayout(new BoxLayout(titreSaisie, BoxLayout.X_AXIS));
		
		JLabel titre = new JLabel("Saisir le nom de la sauvegarde : ");
		titre.setFont(FONT);
		saisie = new JTextField();
		saisie.setFont(FONT);
		
		titreSaisie.add(titre);
		titreSaisie.add(saisie);
		
		JPanel buttonP = new JPanel();
        JPanel buttonPy = new JPanel();
        buttonP.setBackground(Color.WHITE);
        buttonPy.setBackground(Color.WHITE);

        buttonP.setLayout(new BoxLayout(buttonP, BoxLayout.X_AXIS));
        buttonPy.setLayout(new BoxLayout(buttonPy, BoxLayout.Y_AXIS));


        JButton retour = new JButton("Retour");
        retour.setName("retourJeu");
        retour.setFont(FONT);
        retour.setHorizontalTextPosition(JButton.CENTER);
        retour.setVerticalTextPosition(JButton.CENTER);
        retour.setIcon(new ImageIcon(getClass().getResource("/images/ButtonSimple.png")));
        retour.setPressedIcon(new ImageIcon(getClass().getResource( "/images/ButtonPressed.png" ))); 
        retour.setRolloverIcon(new ImageIcon(getClass().getResource( "/images/ButtonRollOver.png" ))); 
        retour.setBorderPainted(false);
        retour.setFocusPainted(false);
        retour.setOpaque(true);
        retour.setBackground(Color.WHITE);
        retour.setForeground(Color.WHITE);
        retour.setContentAreaFilled(false);

        JButton suivant = new JButton("Sauvegarder");
        suivant.setName("save");
        suivant.setFont(FONT);
        suivant.setHorizontalTextPosition(JButton.CENTER);
        suivant.setVerticalTextPosition(JButton.CENTER);
        suivant.setIcon(new ImageIcon(getClass().getResource("/images/ButtonSimple.png")));
        suivant.setPressedIcon(new ImageIcon( getClass().getResource("/images/ButtonPressed.png" ))); 
        suivant.setRolloverIcon(new ImageIcon( getClass().getResource("/images/ButtonRollOver.png" ))); 
        suivant.setBorderPainted(false);
        suivant.setFocusPainted(false);
        suivant.setOpaque(true);
        suivant.setBackground(Color.WHITE);
        suivant.setForeground(Color.WHITE);
        suivant.setContentAreaFilled(false);
        
        retour.addActionListener(new SaveBtnActionListener(this));
        suivant.addActionListener(new SaveBtnActionListener(this));
        
        buttonP.add(retour);
        buttonP.add(suivant);
        buttonPy.add(buttonP);
        
        panel.add(titreSaisie);
        panel.add(buttonPy);
        
        frame.add(panel);
        frame.revalidate();
        frame.repaint();
        frame.pack();
        frame.setVisible(true);

	}

	@Override
	public void updateBoard(Cell[][] grid, Player[] players) {
		((GameBoardModel) this.board.getModel()).fireTableDataChanged();
	}

	@Override
	public void newTurn(Player player) {
		for (int i = 0; i < this.controller.getGame().getPlayers().length; i++) {
			if (this.controller.getGame().getPlayers()[i].equals(player)) {
				this.playerLbls.get(i).setForeground(Color.RED);
			}
			else {
				this.playerLbls.get(i).setForeground(Color.BLACK);
			}
		}
	}

	@Override
	public synchronized int[] humanMove() {
		if (!quit) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		if (quit) {
			this.gameFrame.dispose();
			this.controller.showMenuUI();
		}
		
		return this.move;
		
	}

	@Override
	public void displayWinner(Player winner) {
		JOptionPane.showMessageDialog(this.gameFrame, winner.getName() + " a gagn� la partie !", "Fin de la partie", JOptionPane.PLAIN_MESSAGE);
		this.gameFrame.dispose();
		this.controller.showMenuUI();
	}

	synchronized void setMove(int[] move) {
		this.move = move;
		this.notifyAll();
	}
	

	synchronized void quit() {
		this.quit = true;
		this.notifyAll();
		
	}
	
	public JFrame getFrame() {
		return this.frame;
	}
	
	public Controller getController() {
		return this.controller;
	}
	
	public JTextField getSaisie() {
		return this.saisie;
	}

}