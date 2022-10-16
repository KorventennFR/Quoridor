package quoridor.view;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import quoridor.control.*;

/**
 * Represents the Menu UI in GUI Mode
 * @author P-L. de Villers, C. Lussiez, B. Brisson
 *
 */
public class MenuGUI extends MenuUIView {
	
	 private int launchMode;

	 private JFrame frame;
	 private JPanel panel;
	 private Font font;  
	 private JLabel title;
	 private JPanel midPrincipal;
	 private JPanel midNewGame;
	 private JPanel bottomNewGame;
	 private JButton nb4player;
	 private JButton nb2player;
	 private int i;
	 private int j;
	 private int k;
	 private JLabel modetitre;
	 private JLabel niveauIaLabel;
	 private String[] mode2J = {" Humain VS Humain ", " Humain VS IA "};
	 private String[] mode4J = {" 4 Humains ", " 3 Humains 1 IA ", " 2 Humains 2 IA ", " 1 Humain 3 IA "};
	 private String[] iaLevel = {" Simple ", " Difficile"};
	 private JList<String> listGameSave;
	 private String[] sauvegarde;
	 private String[] nameOfPlayers = new String[4];
	 private JTextField saisie;
	 private JTextField saisieID;
	 private JTextField saisieNom;
	 private JTextField nom1Saisie;
	 private JTextField nom2Saisie;
	 private JTextField nom3Saisie;
	 private JTextField nom4Saisie;
	 private int nbHumanPlayer ;
	 
	 
	/**
	 * Creates the Menu in a GUI Frame and display it
	 * @param controller the controller used by the game to display the view
	 */
	public MenuGUI(Controller controller) {
		super(controller);
		frame = new JFrame("Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(1000,900));
        frame.setMinimumSize(new Dimension(1000,900));

        font = new Font("Thaoma",Font.BOLD,35);
        
        panel = new JPanel(new GridLayout(2, 1));
        panel.setOpaque(true);
        panel.setBackground(Color.WHITE);
        title = new JLabel(new ImageIcon(getClass().getResource("/images/titre.png")));
        title.setOpaque(true);
        title.setBackground(Color.WHITE);
        panel.add(title);

        mainClic();
        
        frame.pack();
        frame.setVisible(true);
        
        this.launchMode = 0;
        
        this.startGame();

	}
	
	/**
	 * Wait for the game is launched and then start it
	 */
	private synchronized void startGame() {
		try {
			this.wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		this.frame.dispose();
		
		if (this.launchMode == 0) {
			this.controller.getGame().startGameLoop();
		} else if (this.launchMode == 1) {
			this.connexion();
		} else if (this.launchMode == 2){
			this.connexionClient();
		} else {
			this.load();
		}
		
	}
	
	/**
	 * Launch the game
	 */
	synchronized void launchGame() {
		this.notifyAll();
	}

	/**
	 * Called when the frame is created
	 */
	public void mainClic() {
        
        frame.remove(panel);
        
        panel = new JPanel(new GridLayout(2,1));
        panel.setBackground(Color.WHITE);

        
        panel.add(title);

        JButton newGameBtn = new JButton("Nouvelle partie");
        newGameBtn.setName("Nouvelle partie");
        newGameBtn.setFont(font);
        newGameBtn.setHorizontalTextPosition(JButton.CENTER);
        newGameBtn.setVerticalTextPosition(JButton.CENTER);
        newGameBtn.setIcon(new ImageIcon(getClass().getResource("/images/ButtonSimple.png")));
        newGameBtn.setPressedIcon(new ImageIcon(getClass().getResource( "/images/ButtonPressed.png" ))); 
        newGameBtn.setRolloverIcon(new ImageIcon(getClass().getResource( "/images/ButtonRollOver.png" ))); 
        newGameBtn.setBorderPainted(false);
        newGameBtn.setFocusPainted( false );
        newGameBtn.setOpaque(true);
        newGameBtn.setBackground(Color.WHITE);
        newGameBtn.setForeground(Color.WHITE);
        newGameBtn.setContentAreaFilled(false);

        JButton loadBtn = new JButton("Charger une partie");
        loadBtn.setName("Charger une partie");
        loadBtn.setFont(font);
        loadBtn.setHorizontalTextPosition(JButton.CENTER);
        loadBtn.setVerticalTextPosition(JButton.CENTER);
        loadBtn.setIcon(new ImageIcon(getClass().getResource("/images/ButtonSimple.png")));
        loadBtn.setPressedIcon(new ImageIcon(getClass().getResource( "/images/ButtonPressed.png" ))); 
        loadBtn.setRolloverIcon(new ImageIcon(getClass().getResource( "/images/ButtonRollOver.png" ))); 
        loadBtn.setBorderPainted(false);
        loadBtn.setOpaque(true);
        loadBtn.setFocusPainted(false);
        loadBtn.setBackground(Color.WHITE);
        loadBtn.setForeground(Color.WHITE);
        loadBtn.setContentAreaFilled(false);
        
        JButton localGame = new JButton("Jouer en Local");
        localGame.setName("Jouer en Local");
        localGame.setFont(font);
        localGame.setHorizontalTextPosition(JButton.CENTER);
        localGame.setVerticalTextPosition(JButton.CENTER);
        localGame.setIcon(new ImageIcon(getClass().getResource("/images/ButtonSimple.png")));
        localGame.setPressedIcon(new ImageIcon(getClass().getResource( "/images/ButtonPressed.png" ))); 
        localGame.setRolloverIcon(new ImageIcon(getClass().getResource( "/images/ButtonRollOver.png" ))); 
        localGame.setBorderPainted(false);
        localGame.setOpaque(true);
        localGame.setFocusPainted(false);
        localGame.setBackground(Color.WHITE);
        localGame.setForeground(Color.WHITE);
        localGame.setContentAreaFilled(false);
        localGame.setEnabled(false);

        JButton leaveBtn = new JButton("Quitter");
        leaveBtn.setName("Quitter");
        leaveBtn.setFont(font);
        leaveBtn.setHorizontalTextPosition(JButton.CENTER);
        leaveBtn.setVerticalTextPosition(JButton.CENTER);
        leaveBtn.setIcon(new ImageIcon(getClass().getResource("/images/ButtonSimple.png")));
        leaveBtn.setPressedIcon(new ImageIcon(getClass().getResource( "/images/ButtonPressed.png" ))); 
        leaveBtn.setRolloverIcon(new ImageIcon(getClass().getResource( "/images/ButtonRollOver.png" ))); 
        leaveBtn.setBorderPainted(false);
        leaveBtn.setFocusPainted(false);
        leaveBtn.setOpaque(true);
        leaveBtn.setBackground(Color.WHITE);
        leaveBtn.setForeground(Color.WHITE);
        leaveBtn.setContentAreaFilled(false);
        
        midPrincipal = new JPanel();
        midPrincipal.setLayout(new BoxLayout(midPrincipal, BoxLayout.Y_AXIS));
        midPrincipal.setOpaque(true);
        midPrincipal.setBackground(Color.WHITE);
        midPrincipal.add(newGameBtn);
        midPrincipal.add(loadBtn);
        midPrincipal.add(localGame);
        midPrincipal.add(leaveBtn);
        
        newGameBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        loadBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        localGame.setAlignmentX(Component.CENTER_ALIGNMENT);
        leaveBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(midPrincipal);
        
        
        leaveBtn.addActionListener(new MenuBtnListener(this));
        newGameBtn.addActionListener(new MenuBtnListener(this));
        localGame.addActionListener(new MenuBtnListener(this));
        loadBtn.addActionListener(new MenuBtnListener(this));

        frame.setLayout(new GridLayout(1,1));
        frame.setBackground(Color.WHITE);
        frame.add(panel);

        frame.revalidate();
        frame.repaint();

        
    }

	/**
	 * Called when you click to start a new game
	 */
    public void newGameClic() {

        frame.remove(panel);
        panel.remove(midPrincipal);

        panel = new JPanel(new GridLayout(3,1));
        panel.setBackground(Color.WHITE);
        
        panel.add(title);
        
        midNewGame = new JPanel(new GridLayout(3,1));
        midNewGame.setBackground(Color.WHITE);

        JLabel nbPlayerLabel = new JLabel("Nombre de joueur", SwingConstants.CENTER);
        nbPlayerLabel.setFont(font);
        midNewGame.add(nbPlayerLabel);

        JPanel nbPlayerPanel = new JPanel();
        JPanel nbPlayerPanely = new JPanel();
        nbPlayerPanely.setLayout(new BoxLayout(nbPlayerPanely, BoxLayout.Y_AXIS));
        nbPlayerPanel.setLayout(new BoxLayout(nbPlayerPanel, BoxLayout.X_AXIS));
        nbPlayerPanel.setBackground(Color.WHITE);
        nbPlayerPanely.setBackground(Color.WHITE);

        nb2player = new JButton("2 joueurs");
        nb2player.setName("2 joueurs");
        nb2player.setFont(font);
        nb2player.setHorizontalTextPosition(JButton.CENTER);
        nb2player.setVerticalTextPosition(JButton.CENTER);
        nb2player.setIcon(new ImageIcon(getClass().getResource("/images/ButtonSimple.png")));
        nb2player.setPressedIcon(new ImageIcon(getClass().getResource( "/images/ButtonPressed.png" ))); 
        nb2player.setRolloverIcon(new ImageIcon(getClass().getResource( "/images/ButtonRollOver.png" ))); 
        nb2player.setBorderPainted(false);
        nb2player.setFocusPainted(false);
        nb2player.setOpaque(true);
        nb2player.setBackground(Color.WHITE);
        nb2player.setForeground(Color.WHITE);
        nb2player.setContentAreaFilled(false);
        nb2player.setEnabled(false);

        nb4player = new JButton("4 joueurs");
        nb4player.setName("4 joueurs");
        nb4player.setFont(font);
        nb4player.setHorizontalTextPosition(JButton.CENTER);
        nb4player.setVerticalTextPosition(JButton.CENTER);
        nb4player.setIcon(new ImageIcon(getClass().getResource("/images/ButtonSimple.png")));
        nb4player.setPressedIcon(new ImageIcon(getClass().getResource( "/images/ButtonPressed.png" ))); 
        nb4player.setRolloverIcon(new ImageIcon(getClass().getResource( "/images/ButtonRollOver.png" ))); 
        nb4player.setBorderPainted(false);
        nb4player.setFocusPainted(false);
        nb4player.setOpaque(true);
        nb4player.setBackground(Color.WHITE);
        nb4player.setForeground(Color.WHITE);
        nb4player.setContentAreaFilled(false);
        
        nb2player.setAlignmentX(Component.CENTER_ALIGNMENT);
        nb4player.setAlignmentX(Component.CENTER_ALIGNMENT);

        
        nbPlayerPanel.add(nb2player);
        nbPlayerPanel.add(nb4player);
        nbPlayerPanely.add(nbPlayerPanel);
        midNewGame.add(nbPlayerPanely);

        JLabel mode = new JLabel("Mode de jeu", SwingConstants.CENTER);
        mode.setFont(font);
        midNewGame.add(mode);

        JPanel bottomNewGame = new JPanel(new GridLayout(2,1));
        bottomNewGame.setBackground(Color.WHITE);


        JPanel modeX = new JPanel();
        modeX.setLayout(new BoxLayout(modeX, BoxLayout.X_AXIS));
        JPanel modeY= new JPanel();
        modeY.setLayout(new BoxLayout(modeY,  BoxLayout.Y_AXIS));

        modeX.setBackground(Color.WHITE);
        modeY.setBackground(Color.WHITE);
        i = 0;
        j = 0;
        modetitre = new JLabel(mode2J[i] , SwingConstants.CENTER);
        
        modetitre.setFont(font);
        modetitre.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));
        modetitre.setMinimumSize(new Dimension(200,100));
        modetitre.setPreferredSize(new Dimension(200,100));

        JButton gauche1 = new JButton();
        gauche1.setName("GMode");
        gauche1.setIcon(new ImageIcon(getClass().getResource("/images/FlecheGSimple.png")));
        gauche1.setPressedIcon(new ImageIcon(getClass().getResource( "/images/FlecheGPressed.png" ))); 
        gauche1.setRolloverIcon(new ImageIcon(getClass().getResource( "/images/FlecheGRollOver.png" ))); 
        gauche1.setBorderPainted(false);
        gauche1.setFocusPainted(false);
        gauche1.setOpaque(true);
        gauche1.setBackground(Color.WHITE);
        gauche1.setForeground(Color.WHITE);
        gauche1.setContentAreaFilled(false);

        JButton droite1 = new JButton();
        droite1.setName("DMode");
        droite1.setIcon(new ImageIcon(getClass().getResource("/images/FlecheDSimple.png")));
        droite1.setPressedIcon(new ImageIcon( getClass().getResource("/images/FlecheDPressed.png" ))); 
        droite1.setRolloverIcon(new ImageIcon( getClass().getResource("/images/FlecheDRollOver.png" ))); 
        droite1.setBorderPainted(false);
        droite1.setFocusPainted(false);
        droite1.setOpaque(true);
        droite1.setBackground(Color.WHITE);
        droite1.setForeground(Color.WHITE);
        droite1.setContentAreaFilled(false);

        modeX.add(gauche1);
        modeX.add(modetitre);
        modeX.add(droite1);

        modeY.add(modeX);
        bottomNewGame.add(modeY);


        JPanel buttonP = new JPanel();
        JPanel buttonPy = new JPanel();
        buttonP.setBackground(Color.WHITE);
        buttonPy.setBackground(Color.WHITE);

        buttonP.setLayout(new BoxLayout(buttonP, BoxLayout.X_AXIS));
        buttonPy.setLayout(new BoxLayout(buttonPy, BoxLayout.Y_AXIS));


        JButton retour = new JButton("Retour");
        retour.setName("RetourMain");
        retour.setFont(font);
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

        JButton suivant = new JButton("Suivant");
        suivant.setName("SuivantName");
        suivant.setFont(font);
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
        
        buttonP.add(retour);
        buttonP.add(suivant);
        buttonPy.add(buttonP);
        bottomNewGame.add(buttonPy);

        panel.add(midNewGame);
        panel.add(bottomNewGame);

        droite1.addActionListener(new MenuBtnListener(this));
        gauche1.addActionListener(new MenuBtnListener(this));
        retour.addActionListener(new MenuBtnListener(this));
        nb2player.addActionListener(new MenuBtnListener(this));
        nb4player.addActionListener(new MenuBtnListener(this));
        suivant.addActionListener(new MenuBtnListener(this));

        frame.add(panel);
        frame.revalidate();
        frame.repaint();

    }

    /**
     * Called when you click on next to go set the difficulty of the IA and the name of the players
     */
    public void nomJoueurClic() {

        frame.remove(panel);

        panel = new JPanel(new GridLayout(3,1));
        panel.setBorder(BorderFactory.createMatteBorder(20, 20, 20, 20, Color.WHITE));
        panel.setBackground(Color.WHITE);
        
        panel.add(title);

        JPanel listeNom = new JPanel();
        listeNom.setBackground(Color.WHITE);
        listeNom.setLayout(new BoxLayout(listeNom, BoxLayout.X_AXIS));
        JPanel listeNomY = new JPanel();
        listeNomY.setBackground(Color.WHITE);
        listeNomY.setLayout(new BoxLayout(listeNomY, BoxLayout.Y_AXIS));
        listeNomY.add(listeNom);
        
        
        JPanel nomP = new JPanel();
        nomP.setBackground(Color.WHITE);
        nomP.setLayout(new BoxLayout(nomP, BoxLayout.X_AXIS));
        JPanel nomPY = new JPanel();
        nomPY.setBackground(Color.WHITE);
        nomPY.setLayout(new BoxLayout(nomPY, BoxLayout.Y_AXIS));

        JLabel nom1 = new JLabel("Nom du joueur 1 : ");
        nom1.setFont(font);
        nom1Saisie = new JTextField();
        nom1Saisie.setMaximumSize(new Dimension(400,100));
        nom1Saisie.setFont(font);

        nomP.add(nom1);
        nomP.add(nom1Saisie);
        nomPY.add(nomP);
        listeNomY.add(nomPY);

        nbHumanPlayer = 1;
       

        if (!modetitre.getText().equals(" Humain VS IA ") && !modetitre.getText().equals(" 1 Humain 3 IA ")) {
            JPanel nomP2 = new JPanel();
            nomP2.setBackground(Color.WHITE);
            nomP2.setLayout(new BoxLayout(nomP2, BoxLayout.X_AXIS));
            JPanel nomPY2 = new JPanel();
            nomPY2.setBackground(Color.WHITE);
            nomPY2.setLayout(new BoxLayout(nomPY2, BoxLayout.Y_AXIS));

            JLabel nom2 = new JLabel("Nom du joueur 2 : ");
            nom2.setFont(font);
            nom2Saisie = new JTextField(" ");
            nom2Saisie.setMaximumSize(new Dimension(400,100));
            nom2Saisie.setFont(font);
    
            nomP2.add(nom2);
            nomP2.add(nom2Saisie);
            nomPY2.add(nomP2);
            listeNomY.add(nomPY2);
            
            nbHumanPlayer++;
            
        }

        if (modetitre.getText().equals(" 3 Humains 1 IA ") || modetitre.getText().equals(" 4 Humains ")) {
            JPanel nomP3 = new JPanel();
            nomP3.setBackground(Color.WHITE);
            nomP3.setLayout(new BoxLayout(nomP3, BoxLayout.X_AXIS));
            JPanel nomPY3 = new JPanel();
            nomPY3.setBackground(Color.WHITE);
            nomPY3.setLayout(new BoxLayout(nomPY3, BoxLayout.Y_AXIS));
    
            JLabel nom3 = new JLabel("Nom du joueur 3 : ");
            nom3.setFont(font);
            nom3Saisie = new JTextField();
            nom3Saisie.setMaximumSize(new Dimension(400,100));
            nom3Saisie.setFont(font);
    
            nomP3.add(nom3);
            nomP3.add(nom3Saisie);
            nomPY3.add(nomP3);
            listeNomY.add(nomPY3);
            
            nbHumanPlayer++;
            
        }

        if (modetitre.getText().equals(" 4 Humains ")) {
            JPanel nomP4 = new JPanel();
            nomP4.setBackground(Color.WHITE);
            nomP4.setLayout(new BoxLayout(nomP4, BoxLayout.X_AXIS));
            JPanel nomPY4 = new JPanel();
            nomPY4.setBackground(Color.WHITE);
            nomPY4.setLayout(new BoxLayout(nomPY4, BoxLayout.Y_AXIS));
    
            JLabel nom4 = new JLabel("Nom du joueur 4 : ");
            nom4.setFont(font);
            nom4Saisie = new JTextField();
            nom4Saisie.setMaximumSize(new Dimension(400,100));
            nom4Saisie.setFont(font);
    
            nomP4.add(nom4);
            nomP4.add(nom4Saisie);
            nomPY4.add(nomP4);
            listeNomY.add(nomPY4);
            
            nbHumanPlayer++;
            
        }
        
        panel.add(listeNomY);

        JPanel bottomName = new JPanel(new GridLayout(3,1));
        bottomName.setBackground(Color.WHITE);

        if (!modetitre.getText().equals(" 4 Humains ") && !modetitre.getText().equals(" Humain VS Humain ")) {

            JLabel titre = new JLabel("Niveau de l'IA", SwingConstants.CENTER);
            titre.setBackground(Color.WHITE);
            titre.setFont(font);
            bottomName.add(titre);

            JPanel modeX = new JPanel();
            modeX.setLayout(new BoxLayout(modeX, BoxLayout.X_AXIS));
            JPanel modeY = new JPanel();
            modeY.setLayout(new BoxLayout(modeY, BoxLayout.Y_AXIS));

            modeX.setBackground(Color.WHITE);
            modeY.setBackground(Color.WHITE);

            k = 0;
            niveauIaLabel = new JLabel(iaLevel[k], SwingConstants.CENTER);
        
            niveauIaLabel.setFont(font);
            niveauIaLabel.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));
            niveauIaLabel.setMinimumSize(new Dimension(200,100));
            niveauIaLabel.setPreferredSize(new Dimension(200,100));

            JButton gauche1 = new JButton();
            gauche1.setName("PLevelIA");
            gauche1.setIcon(new ImageIcon(getClass().getResource("/images/FlecheGSimple.png")));
            gauche1.setPressedIcon(new ImageIcon(getClass().getResource( "/images/FlecheGPressed.png" ))); 
            gauche1.setRolloverIcon(new ImageIcon(getClass().getResource( "/images/FlecheGRollOver.png" ))); 
            gauche1.setHorizontalTextPosition(JButton.LEFT);
            gauche1.setBorderPainted(false);
            gauche1.setFocusPainted(false);
            gauche1.setOpaque(true);
            gauche1.setBackground(Color.WHITE);
            gauche1.setForeground(Color.WHITE);

            JButton droite1 = new JButton("niveauS");
            droite1.setName("SLevelIA");
            droite1.setIcon(new ImageIcon(getClass().getResource("/images/FlecheDSimple.png")));
            droite1.setPressedIcon(new ImageIcon( getClass().getResource("/images/FlecheDPressed.png" ))); 
            droite1.setRolloverIcon(new ImageIcon(getClass().getResource("/images/FlecheDRollOver.png" ))); 
            droite1.setBorderPainted(false);
            droite1.setFocusPainted(false);
            droite1.setOpaque(true);
            droite1.setBackground(Color.WHITE);
            droite1.setForeground(Color.WHITE);
            droite1.setContentAreaFilled(false);

            modeX.add(gauche1);
            modeX.add(niveauIaLabel);
            modeX.add(droite1);

            gauche1.addActionListener(new MenuBtnListener(this));
            droite1.addActionListener(new MenuBtnListener(this));

            modeY.add(modeX);
            bottomName.add(modeY);

        } else {
            JLabel vide= new JLabel();
            bottomName.add(vide);
            JLabel vide2= new JLabel();
            bottomName.add(vide2);
        }

        JPanel buttonP = new JPanel();
        buttonP.setBackground(Color.WHITE);
        buttonP.setLayout(new BoxLayout(buttonP, BoxLayout.X_AXIS));
        JPanel buttonPY = new JPanel();
        buttonPY.setBackground(Color.WHITE);
        buttonPY.setLayout(new BoxLayout(buttonPY, BoxLayout.Y_AXIS));

        

        JButton retour = new JButton("Retour");
        retour.setName("RetourNewGame");
        retour.setFont(font);
        retour.setIcon(new ImageIcon(getClass().getResource("/images/ButtonSimple.png")));
        retour.setHorizontalTextPosition(JButton.CENTER);
        retour.setPressedIcon(new ImageIcon( getClass().getResource("/images/ButtonPressed.png" ))); 
        retour.setRolloverIcon(new ImageIcon( getClass().getResource("/images/ButtonRollOver.png" ))); 
        retour.setBorderPainted(false);
        retour.setFocusPainted(false);
        retour.setOpaque(true);
        retour.setBackground(Color.WHITE);
        retour.setForeground(Color.WHITE);
        retour.setContentAreaFilled(false);

        JButton suivant = new JButton("Suivant");
        suivant.setName("SuivantStart");
        suivant.setFont(font);
        suivant.setHorizontalTextPosition(JButton.CENTER);
        suivant.setVerticalTextPosition(JButton.CENTER);
        suivant.setIcon(new ImageIcon(getClass().getResource("/images/ButtonSimple.png")));
        suivant.setPressedIcon(new ImageIcon(getClass().getResource( "/images/ButtonPressed.png" ))); 
        suivant.setRolloverIcon(new ImageIcon( getClass().getResource("/images/ButtonRollOver.png" ))); 
        suivant.setBorderPainted(false);
        suivant.setFocusPainted(false);
        suivant.setOpaque(true);
        suivant.setBackground(Color.WHITE);
        suivant.setForeground(Color.WHITE);
        suivant.setContentAreaFilled(false);


        buttonP.add(retour);
        buttonP.add(suivant);
        buttonPY.add(buttonP);
        bottomName.add(buttonPY);

        panel.add(bottomName);
        retour.addActionListener(new MenuBtnListener(this));
        suivant.addActionListener(new MenuBtnListener(this));
        frame.add(panel);
        frame.revalidate();
        frame.repaint();
    }
    
    /**
     * Called when you click to load a game
     */
    public void loadGame() {
    	
    	frame.remove(panel);

        panel = new JPanel(new GridLayout(3,1));
        panel.setBorder(BorderFactory.createMatteBorder(20, 20, 20, 20, Color.WHITE));
        panel.setBackground(Color.WHITE);
        
        panel.add(title);
        
        JPanel saveY = new JPanel(new GridLayout(1,2));
     
        
        sauvegarde = getAllSaves();
        listGameSave = new JList<String>(sauvegarde);
     
        listGameSave.setSelectedIndex(0);
       
        DefaultListCellRenderer renderer = (DefaultListCellRenderer)listGameSave.getCellRenderer();
        renderer.setHorizontalAlignment(JLabel.CENTER);
        listGameSave.setFont(font);
        listGameSave.setBackground(Color.WHITE);
        
        JPanel buttons = new JPanel(new GridLayout(2,1));
        buttons.setBackground(Color.WHITE);
        buttons.setFont(font);
        
        JButton charger = new JButton("Charger");
        charger.setName("Charger");
        charger.setFont(font);
        charger.setIcon(new ImageIcon(getClass().getResource("/images/ButtonSimple.png")));
        charger.setHorizontalTextPosition(JButton.CENTER);
        charger.setPressedIcon(new ImageIcon(getClass().getResource( "/images/ButtonPressed.png" ))); 
        charger.setRolloverIcon(new ImageIcon(getClass().getResource( "/images/ButtonRollOver.png" ))); 
        charger.setBorderPainted(false);
        charger.setFocusPainted(false);
        charger.setOpaque(true);
        charger.setBackground(Color.WHITE);
        charger.setForeground(Color.WHITE);
        charger.setContentAreaFilled(false);
        buttons.add(charger);
        
        JButton suprimer = new JButton("Suprimer");
        suprimer.setName("Suprimer");
        suprimer.setFont(font);
        suprimer.setIcon(new ImageIcon(getClass().getResource("/images/ButtonSimple.png")));
        suprimer.setHorizontalTextPosition(JButton.CENTER);
        suprimer.setPressedIcon(new ImageIcon( getClass().getResource("/images/ButtonPressed.png" ))); 
        suprimer.setRolloverIcon(new ImageIcon( getClass().getResource("/images/ButtonRollOver.png" ))); 
        suprimer.setBorderPainted(false);
        suprimer.setFocusPainted(false);
        suprimer.setOpaque(true);
        suprimer.setBackground(Color.WHITE);
        suprimer.setForeground(Color.WHITE);
        suprimer.setContentAreaFilled(false);
        buttons.add(suprimer);
        
        saveY.add(listGameSave);
        saveY.add(buttons);

        panel.add(saveY);
        
        JPanel bottomLoad = new JPanel(new GridLayout(2,1));
        bottomLoad.setBackground(Color.WHITE);
        JLabel vide = new JLabel();
        vide.setBackground(Color.WHITE);
        bottomLoad.add(vide);
        
        JPanel buttonP = new JPanel();
        buttonP.setBackground(Color.WHITE);
        buttonP.setLayout(new BoxLayout(buttonP, BoxLayout.X_AXIS));
        JPanel buttonPY = new JPanel();
        buttonPY.setBackground(Color.WHITE);
        buttonPY.setLayout(new BoxLayout(buttonPY, BoxLayout.Y_AXIS));

        JButton retour = new JButton("Retour");
        retour.setName("RetourMain");
        retour.setFont(font);
        retour.setIcon(new ImageIcon(getClass().getResource("/images/ButtonSimple.png")));
        retour.setHorizontalTextPosition(JButton.CENTER);
        retour.setPressedIcon(new ImageIcon(getClass().getResource( "/images/ButtonPressed.png" ))); 
        retour.setRolloverIcon(new ImageIcon( getClass().getResource("/images/ButtonRollOver.png" ))); 
        retour.setBorderPainted(false);
        retour.setFocusPainted(false);
        retour.setOpaque(true);
        retour.setBackground(Color.WHITE);
        retour.setForeground(Color.WHITE);
        retour.setContentAreaFilled(false);

        buttonP.add(retour);
        buttonPY.add(buttonP);
        bottomLoad.add(buttonPY);
        
        panel.add(bottomLoad);
        
        retour.addActionListener(new MenuBtnListener(this));
        suprimer.addActionListener(new MenuBtnListener(this));
        charger.addActionListener(new MenuBtnListener(this));
        
    	frame.add(panel);
        frame.revalidate();
        frame.repaint();
    }
    
    /**
     * Load a game
     */
    public void load() {
		this.getController().getGame().load(this.getJList().getSelectedValue());
		this.getController().getGame().restartGameLoop();
    }
    
    /**
     * Launch the loading of a game
     */
    public synchronized void launchLoad() {
    	if (!this.getJList().getSelectedValue().equals("Pas de sauvegarde")) {
    		this.launchMode = 3;
    		this.notifyAll();
    	}
    }
    
    
    /**
     * When you click on local game
     */
    public void localClic() {
    	
    	frame.remove(panel);

        panel = new JPanel(new GridLayout(2 ,1));
        panel.setBorder(BorderFactory.createMatteBorder(20, 20, 20, 20, Color.WHITE));
        panel.setBackground(Color.WHITE);
        
        panel.add(title);
        
        JPanel buttonPY = new JPanel();
        buttonPY.setBackground(Color.WHITE);
        buttonPY.setLayout(new BoxLayout(buttonPY, BoxLayout.PAGE_AXIS));

        JButton hostBtn = new JButton("Host");
        hostBtn.setName("Host");
        hostBtn.setFont(font);
        hostBtn.setIcon(new ImageIcon(getClass().getResource("/images/ButtonSimple.png")));
        hostBtn.setHorizontalTextPosition(JButton.CENTER);
        hostBtn.setPressedIcon(new ImageIcon(getClass().getResource( "/images/ButtonPressed.png" ))); 
        hostBtn.setRolloverIcon(new ImageIcon( getClass().getResource("/images/ButtonRollOver.png" ))); 
        hostBtn.setBorderPainted(false);
        hostBtn.setFocusPainted(false);
        hostBtn.setOpaque(true);
        hostBtn.setBackground(Color.WHITE);
        hostBtn.setForeground(Color.WHITE);
        hostBtn.setContentAreaFilled(false);
        hostBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JButton clientBtn = new JButton("Client");
        clientBtn.setName("Client");
        clientBtn.setFont(font);
        clientBtn.setIcon(new ImageIcon(getClass().getResource("/images/ButtonSimple.png")));
        clientBtn.setHorizontalTextPosition(JButton.CENTER);
        clientBtn.setPressedIcon(new ImageIcon( getClass().getResource("/images/ButtonPressed.png" ))); 
        clientBtn.setRolloverIcon(new ImageIcon(getClass().getResource("/images/ButtonRollOver.png" ))); 
        clientBtn.setBorderPainted(false);
        clientBtn.setFocusPainted(false);
        clientBtn.setOpaque(true);
        clientBtn.setBackground(Color.WHITE);
        clientBtn.setForeground(Color.WHITE);
        clientBtn.setContentAreaFilled(false);
        clientBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton retour = new JButton("Retour");
        retour.setName("RetourMain");
        retour.setFont(font);
        retour.setIcon(new ImageIcon(getClass().getResource("/images/ButtonSimple.png")));
        retour.setHorizontalTextPosition(JButton.CENTER);
        retour.setPressedIcon(new ImageIcon( getClass().getResource("/images/ButtonPressed.png" ))); 
        retour.setRolloverIcon(new ImageIcon( getClass().getResource("/images/ButtonRollOver.png" ))); 
        retour.setBorderPainted(false);
        retour.setFocusPainted(false);
        retour.setOpaque(true);
        retour.setBackground(Color.WHITE);
        retour.setForeground(Color.WHITE);
        retour.setContentAreaFilled(false);
        retour.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        buttonPY.add(hostBtn);
        buttonPY.add(clientBtn);
        buttonPY.add(retour);
        
        panel.add(buttonPY);
        hostBtn.addActionListener(new MenuBtnListener(this));
        clientBtn.addActionListener(new MenuBtnListener(this));
        retour.addActionListener(new MenuBtnListener(this));
        
    	frame.add(panel);
        frame.revalidate();
        frame.repaint();
    }
    
    /**
     * Chen you click on host in the local game menu
     */
    public void hostClic() {
    	
    	frame.remove(panel);

        panel = new JPanel(new GridLayout(3,1));
        panel.setBorder(BorderFactory.createMatteBorder(20, 20, 20, 20, Color.WHITE));
        panel.setBackground(Color.WHITE);
        
        panel.add(title);
        
        JPanel titreSaisie = new JPanel();
        titreSaisie.setBackground(Color.WHITE);
        titreSaisie.setLayout(new BoxLayout(titreSaisie, BoxLayout.PAGE_AXIS));
        
        JLabel titre = new JLabel("Saisir votre nom");
        titre.setAlignmentX(Component.CENTER_ALIGNMENT);
        titre.setFont(font);
        
        saisie = new JTextField();
        saisie.setAlignmentX(Component.CENTER_ALIGNMENT);
        saisie.setFont(font);
        saisie.setMaximumSize(new Dimension(500,100));
        
        titreSaisie.add(titre);
        titreSaisie.add(saisie);
        panel.add(titreSaisie);
        
        JPanel buttonP = new JPanel();
        buttonP.setBackground(Color.WHITE);
        buttonP.setLayout(new BoxLayout(buttonP, BoxLayout.X_AXIS));
        JPanel buttonPY = new JPanel();
        buttonPY.setBackground(Color.WHITE);
        buttonPY.setLayout(new BoxLayout(buttonPY, BoxLayout.Y_AXIS));

        

        JButton retour = new JButton("Retour");
        retour.setName("Retour Local");
        retour.setFont(font);
        retour.setIcon(new ImageIcon(getClass().getResource("/images/ButtonSimple.png")));
        retour.setHorizontalTextPosition(JButton.CENTER);
        retour.setPressedIcon(new ImageIcon(getClass().getResource( "/images/ButtonPressed.png" ))); 
        retour.setRolloverIcon(new ImageIcon( getClass().getResource("/images/ButtonRollOver.png" ))); 
        retour.setBorderPainted(false);
        retour.setFocusPainted(false);
        retour.setOpaque(true);
        retour.setBackground(Color.WHITE);
        retour.setForeground(Color.WHITE);
        retour.setContentAreaFilled(false);

        JButton suivant = new JButton("Suivant");
        suivant.setName("SuivantID");
        suivant.setFont(font);
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


        buttonP.add(retour);
        buttonP.add(suivant);
        buttonPY.add(buttonP);
        panel.add(buttonPY);
        
        retour.addActionListener(new MenuBtnListener(this));
        suivant.addActionListener(new MenuBtnListener(this));
        
    	frame.add(panel);
        frame.revalidate();
        frame.repaint();
    }
    
    /**
     * Called when you entered your name when hosting 
     */
    public void IdClic() {
    	
    	frame.remove(panel);

        panel = new JPanel(new GridLayout(3,1));
        panel.setBorder(BorderFactory.createMatteBorder(20, 20, 20, 20, Color.WHITE));
        panel.setBackground(Color.WHITE);
        
        JPanel buttonPY = new JPanel();
        buttonPY.setBackground(Color.WHITE);
        buttonPY.setLayout(new BoxLayout(buttonPY, BoxLayout.PAGE_AXIS));
        
        panel.add(title);
        String add = "Erreur";
        try {
			add = InetAddress.getLocalHost().toString();
			String[] temp = add.split("\\.");
			add = temp[temp.length-1];	//We get the last number of the host local IP address
		} catch (UnknownHostException e1) {
			System.out.println("Impossible de récupérer votre ID");
			this.localClic();
		}
        
        
		JLabel text1 = new JLabel("Votre id est : "+add);
		text1.setFont(font);
		text1.setAlignmentX(Component.CENTER_ALIGNMENT);
		JLabel text2 = new JLabel("Le client doit entrer cet ID pour se connecter.");
		text2.setFont(font);
		text2.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JButton suivant = new JButton("Connexion");
        suivant.setName("Connexion");
        suivant.setFont(font);
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
		
		buttonPY.add(text1);
        buttonPY.add(text2);
        
        suivant.addActionListener(new MenuBtnListener(this));
        
		panel.add(buttonPY);
		panel.add(suivant);
    	frame.add(panel);
        frame.revalidate();
        frame.repaint();
       
        
    }
    
    /**
     * Called when you clicked on client in the local game menu. ask for the host id
     */
    public void clientId() {
    	frame.remove(panel);

        panel = new JPanel(new GridLayout(3,1));
        panel.setBorder(BorderFactory.createMatteBorder(20, 20, 20, 20, Color.WHITE));
        panel.setBackground(Color.WHITE);
           
        panel.add(title);
        
        JPanel titreSaisie = new JPanel();
        titreSaisie.setBackground(Color.WHITE);
        titreSaisie.setLayout(new BoxLayout(titreSaisie, BoxLayout.PAGE_AXIS));

        JLabel text1 = new JLabel("Entez l'ID de l'hôte : ");
        text1.setAlignmentX(Component.CENTER_ALIGNMENT);
        text1.setFont(font);
        
        saisieID = new JTextField();
        saisieID.setAlignmentX(Component.CENTER_ALIGNMENT);
        saisieID.setFont(font);
        saisieID.setMaximumSize(new Dimension(500,100));
        
        titreSaisie.add(text1);
        titreSaisie.add(saisieID);
        panel.add(titreSaisie);
        
        JPanel buttonP = new JPanel();
        buttonP.setBackground(Color.WHITE);
        buttonP.setLayout(new BoxLayout(buttonP, BoxLayout.X_AXIS));
        JPanel buttonPY = new JPanel();
        buttonPY.setBackground(Color.WHITE);
        buttonPY.setLayout(new BoxLayout(buttonPY, BoxLayout.Y_AXIS));

        

        JButton retour = new JButton("Retour");
        retour.setName("Retour Local");
        retour.setFont(font);
        retour.setIcon(new ImageIcon(getClass().getResource("/images/ButtonSimple.png")));
        retour.setHorizontalTextPosition(JButton.CENTER);
        retour.setPressedIcon(new ImageIcon( getClass().getResource("/images/ButtonPressed.png" ))); 
        retour.setRolloverIcon(new ImageIcon( getClass().getResource("/images/ButtonRollOver.png" ))); 
        retour.setBorderPainted(false);
        retour.setFocusPainted(false);
        retour.setOpaque(true);
        retour.setBackground(Color.WHITE);
        retour.setForeground(Color.WHITE);
        retour.setContentAreaFilled(false);

        JButton suivant = new JButton("Suivant");
        suivant.setName("SuivantNomClient");
        suivant.setFont(font);
        suivant.setHorizontalTextPosition(JButton.CENTER);
        suivant.setVerticalTextPosition(JButton.CENTER);
        suivant.setIcon(new ImageIcon(getClass().getResource("/images/ButtonSimple.png")));
        suivant.setPressedIcon(new ImageIcon( getClass().getResource("/images/ButtonPressed.png" ))); 
        suivant.setRolloverIcon(new ImageIcon(getClass().getResource( "/images/ButtonRollOver.png" ))); 
        suivant.setBorderPainted(false);
        suivant.setFocusPainted(false);
        suivant.setOpaque(true);
        suivant.setBackground(Color.WHITE);
        suivant.setForeground(Color.WHITE);
        suivant.setContentAreaFilled(false);

        retour.addActionListener(new MenuBtnListener(this));
        suivant.addActionListener(new MenuBtnListener(this));
        
        buttonP.add(retour);
        buttonP.add(suivant);
        buttonPY.add(buttonP);
        panel.add(buttonPY);
        
    	frame.add(panel);
        frame.revalidate();
        frame.repaint();
    }
    
    /**
     * Called when you entered host id. Ask for the name of the client
     */
    public void clientNom() {
    	frame.remove(panel);

        panel = new JPanel(new GridLayout(3,1));
        panel.setBorder(BorderFactory.createMatteBorder(20, 20, 20, 20, Color.WHITE));
        panel.setBackground(Color.WHITE);
           
        panel.add(title);
        
        JPanel titreSaisie = new JPanel();
        titreSaisie.setBackground(Color.WHITE);
        titreSaisie.setLayout(new BoxLayout(titreSaisie, BoxLayout.PAGE_AXIS));

        JLabel text1 = new JLabel("Entrez votre nom : ");
        text1.setAlignmentX(Component.CENTER_ALIGNMENT);
        text1.setFont(font);
        
        saisieNom = new JTextField();
        saisieNom.setAlignmentX(Component.CENTER_ALIGNMENT);
        saisieNom.setFont(font);
        saisieNom.setMaximumSize(new Dimension(500,100));
        
        titreSaisie.add(text1);
        titreSaisie.add(saisieNom);
        panel.add(titreSaisie);
        
        JPanel buttonP = new JPanel();
        buttonP.setBackground(Color.WHITE);
        buttonP.setLayout(new BoxLayout(buttonP, BoxLayout.X_AXIS));
        JPanel buttonPY = new JPanel();
        buttonPY.setBackground(Color.WHITE);
        buttonPY.setLayout(new BoxLayout(buttonPY, BoxLayout.Y_AXIS));

        

        JButton retour = new JButton("Retour");
        retour.setName("Retour Local ID");
        retour.setFont(font);
        retour.setIcon(new ImageIcon(getClass().getResource("/images/ButtonSimple.png")));
        retour.setHorizontalTextPosition(JButton.CENTER);
        retour.setPressedIcon(new ImageIcon(getClass().getResource( "/images/ButtonPressed.png" ))); 
        retour.setRolloverIcon(new ImageIcon( getClass().getResource("/images/ButtonRollOver.png" ))); 
        retour.setBorderPainted(false);
        retour.setFocusPainted(false);
        retour.setOpaque(true);
        retour.setBackground(Color.WHITE);
        retour.setForeground(Color.WHITE);
        retour.setContentAreaFilled(false);

        JButton suivant = new JButton("Connexion");
        suivant.setName("Connexion2");
        suivant.setFont(font);
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

        retour.addActionListener(new MenuBtnListener(this));
        suivant.addActionListener(new MenuBtnListener(this));
        buttonP.add(retour);
        buttonP.add(suivant);
        buttonPY.add(buttonP);
        panel.add(buttonPY);
        
    	frame.add(panel);
        frame.revalidate();
        frame.repaint();
    }
    
    /**
     * start a game as the host of the game
     */
    public void connexion() {
    	try {
			this.controller.getGame().Host(getsaisie());	//We launch the game for the host
		}catch (SocketTimeoutException e) {
			System.out.println("Aucun client ne s'est connecté à temp");
			this.localClic();
		} catch (IOException e) {
			System.out.println("La connexion au client a échoué");
			this.localClic();
		}
    }
    
    /**
     * launch the game when you host it
     */
    public synchronized void launchConnexion() {
    	this.launchMode = 1;
    	this.notifyAll();
    }
    
    /**
     * Start the game as a client
     */
    public void connexionClient() {
    	try {
			this.controller.getGame().Client(this.getClientName(), this.getClientID());	//We launch the game for the client
		} catch (IOException e) {
			System.out.println("La connexion au serveur a échoué");
			this.clientNom();
		}
    }
    
    /**
     * launch the connexion to the host when you are the client
     */
    public synchronized void launchConnexionClient() {
    	this.launchMode = 2;
    	this.notifyAll();
    }
    
    /**
     * The arrow next of the mode of 2 and 4 players
     */
    public void suivant() {
        if (!nb2player.isEnabled()) {
            if ( i < this.mode2J.length-1) {
                i++;
                modetitre.setText(mode2J[i]);
            } 
        }
        else {
            if ( j < this.mode4J.length-1) {
                j++;
                modetitre.setText(mode4J[j]);
            } 
        }
    }
    /**
     * The arrows next to choose the level of the IA
     */
    public void suivantLevel() {
        if (k < this.iaLevel.length-1) {
            k++;
            niveauIaLabel.setText(iaLevel[k]);
        }
    }
    /**
     * The arrow before to choose the level of the IA
     */
    public void precedentLevel() {
        if (k > 0) {
            k--;
            niveauIaLabel.setText(iaLevel[k]);
        }
    }

    /**
     * Show in the label the right option according to the mode
     */
    public void precedent() {
        if (!nb2player.isEnabled()) {
            if (i > 0) {
                i--;
                modetitre.setText(mode2J[i]);
            } 
        }
        else {
            if (j > 0) {
                j--;
                modetitre.setText(mode4J[j]);
            } 
        }
    }
    
    /**
     * Get the frame
     * @return the frame
     */
    public JFrame getFrame() {
        return this.frame;
    }

    /**
     * Get the middle of the panel new game
     * @return the middle
     */
    public JPanel getMidNewGame() {
        return this.midNewGame;
    }

    /**
     * Get the bottom of the panel new game
     * @return the bottom
     */
    public JPanel getBotNewGame() {
        return this.bottomNewGame;
    }

    /**
     * get the panel
     * @return the panel
     */
    public JPanel getFull() {
        return this.panel;
    }

    /**
     * get the button of 2 players
     * @return jbutton
     */
    public JButton get2players() {
        return this.nb2player;
    }
    /**
     * get the array of the mode
     * @return
     */
    public String[] getMode2players() {
    	return this.mode2J;
    }

    /**
     * get the button of 4 players
     * @return button
     */
    public JButton get4players() {
        return this.nb4player;
    }
    
    /**
     * get the i
     * @return i 
     */
    public int getI() {
    	return i;
    }
    
    /**
     * get the j
     * @return j
     */
    public int getJ() {
    	return j;
    }
    
    /**
     * get the k
     * @return k
     */
    public int getK() {
    	return k;
    }
    
    /**
     * get the array of mode for 4 player
     * @return array
     */
    public String[] getMode4players() {
    	return this.mode4J;
    }
    /**
     * the jlist of game save
     * @return jlist
     */
    public JList<String> getJList() {
    	return this.listGameSave;
    }
    
    /**
     * get the controller
     * @return controller
     */
    public Controller getController() {
    	return this.controller;
    }
    
    /**
     * get the array of save
     * @return saves
     */
    public String[] getSauvegarde() {
    	return this.sauvegarde;
    }
    
    /**
     * get the name of players
     * @return names of players
     */
    public String[] getNameOfPlayers() {
    	return this.nameOfPlayers;
    }
    
    /**
     * get the different level of ia
     * @return level of iA
     */
    public String[] getIaLevel() {
    	return this.iaLevel;
    }
  
    /**
     * Get the text in the field
     * @return field
     */
    public String getsaisie() {
    	return this.saisie.getText();
    }
    
    /**
     * get the id of the client
     * @return id
     */
    public String getClientID() {
    	return this.saisieID.getText();
    }
    
    /**
     * get the name of the client
     * @return name of client
     */
    public String getClientName() {
    	return this.saisieNom.getText();
    }
    /**
     * get the text of the field of the name of player
     * @param i
     * @return name
     */
    public String nom(int i) {
    	String ret = "null";
    	if (i == 0) {
    		ret = this.nom1Saisie.getText();
    	}else if (i == 1) {
    		ret = this.nom2Saisie.getText();
    	}
    	else if (i == 2) {
    		ret = this.nom3Saisie.getText();
    	} 
    	else if (i == 3) {
    		ret = this.nom4Saisie.getText();
    	}
    	return ret;
		
    	
    }

    /**
     * get the nb of human players
     * @return nb
     */
    public int getnbHumain() {
    	return this.nbHumanPlayer;
    }
    
    /**
     * get mdoe titre
     * @return mode titre
     */
    public String getmodetitre() {
    	return this.modetitre.getText();
    }



    
}