package quoridor.view;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;

import quoridor.control.Controller;
import quoridor.model.GameMode;
import quoridor.model.Parameters;


/**
 * Listener for the Menu buttons actions
 * @author P-L. de Villers, C. Lussiez, B. Brisson
 *
 */
public class MenuBtnListener implements ActionListener {

	private MenuGUI menu;

	/**
	 * Creates the Listener
	 * @param menu the menuGUI object reference
	 * @param controller 
	 */
	public MenuBtnListener(MenuGUI menu) {
		this.menu = menu;
	}

	/**
	 * Triggered when a button on the menu is clicked on the button can be get with the event parameter
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		JButton button = (JButton) event.getSource();
		if(button.getName().equals("Quitter")) {
            this.menu.getFrame().dispose();
        }
        if(button.getName().equals("Nouvelle partie")) {
            this.menu.newGameClic();
        }
        if(button.getName().equals("RetourMain")) {
            this.menu.mainClic();
        }
        if (button.getName().equals("2 joueurs")) {
            button.setEnabled(false);
            this.menu.get4players().setEnabled(true);
            this.menu.suivant();
            this.menu.precedent();
        }
        if (button.getName().equals("4 joueurs")) {
            button.setEnabled(false);
            this.menu.get2players().setEnabled(true);
            this.menu.suivant();
            this.menu.precedent();
        }
        if (button.getName().equals("DMode")) {
            this.menu.suivant();
        }
        if (button.getName().equals("GMode")) {
            this.menu.precedent();
        }
        if (button.getName().equals("SuivantName")) {
            this.menu.nomJoueurClic();
        }
        if (button.getName().equals("RetourNewGame")) {
            this.menu.newGameClic();
        }
        if (button.getName().equals("SLevelIA")) {
            this.menu.suivantLevel();
        }
        if (button.getName().equals("PLevelIA")) {
            this.menu.precedentLevel();
        }
        if (button.getName().equals("Charger une partie")) {
        	this.menu.loadGame();
        }
        if (button.getName().equals("Suprimer")) {
            this.menu.getController().getGame().deleteFile(this.menu.getJList().getSelectedValue());
            this.menu.getJList().setListData(this.menu.getAllSaves());
        }
        if (button.getName().equals("SuivantStart")) {
        	int i = 0;
        	while (i < this.menu.getnbHumain()) {
        		this.menu.getNameOfPlayers()[i] = this.menu.nom(i);
        		i++;
        	}
        	if (this.menu.getmodetitre().equals(" Humain VS Humain ") || this.menu.getmodetitre().equals(" Humain VS IA ")) {
        		if (this.menu.getmodetitre().equals(" Humain VS Humain ")) {
        			this.menu.getController().getGame().setParams(new Parameters(2, GameMode.HH, this.menu.getNameOfPlayers(), 0));
        		}
        		else {
        			if (this.menu.getIaLevel()[this.menu.getK()].equals(" Simple ")) {
        				this.menu.getController().getGame().setParams(new Parameters(2, GameMode.HA, this.menu.getNameOfPlayers(), 0));
        			}
        			else {
        				this.menu.getController().getGame().setParams(new Parameters(2, GameMode.HA, this.menu.getNameOfPlayers(), 1));
        			}
        		}
        		
        	} else {
        		if (this.menu.getmodetitre().equals(" 4 Humains ")) {
        			this.menu.getController().getGame().setParams(new Parameters(4, GameMode.HHHH, this.menu.getNameOfPlayers(), 0));
        		}
        		if (this.menu.getmodetitre().equals(" 3 Humains 1 IA ")) {
        			if (this.menu.getIaLevel()[this.menu.getK()].equals(" Simple ")) {
        				this.menu.getController().getGame().setParams(new Parameters(4, GameMode.HHHA, this.menu.getNameOfPlayers(), 0));
        			}
        			else {
        				this.menu.getController().getGame().setParams(new Parameters(4, GameMode.HHHA, this.menu.getNameOfPlayers(), 1));
        			}
        			
        		}
        		if (this.menu.getmodetitre().equals(" 2 Humains 2 IA ")) {
        			if (this.menu.getIaLevel()[this.menu.getK()].equals(" Simple ")) {
        				this.menu.getController().getGame().setParams(new Parameters(4, GameMode.HHAA, this.menu.getNameOfPlayers(), 0));
        			}
        			else {
        				this.menu.getController().getGame().setParams(new Parameters(4, GameMode.HHAA, this.menu.getNameOfPlayers(), 1));
        			}
        		}
        		if (this.menu.getmodetitre().equals(" 1 Humain 3 IA ")) {
        			if (this.menu.getIaLevel()[this.menu.getK()].equals(" Simple ")) {
        				this.menu.getController().getGame().setParams(new Parameters(4, GameMode.HAAA, this.menu.getNameOfPlayers(), 0));
        			}
        			else {
        				this.menu.getController().getGame().setParams(new Parameters(4, GameMode.HAAA, this.menu.getNameOfPlayers(), 1));
        			}
        		}
   
        	}
       
        	this.menu.launchGame();
        	
        	
        }
        if (button.getName().equals("Charger")) {
        	this.menu.launchLoad();
        }
        if (button.getName().equals("Jouer en Local")) {
        	this.menu.localClic();
        }
        if (button.getName().equals("Host")) {
        	this.menu.hostClic();
        }
        if (button.getName().equals("SuivantID")) {
        	this.menu.IdClic();
        }
        if (button.getName().equals("Connexion")) {
        	this.menu.launchConnexion();
        }
        if (button.getName().equals("Client")) {
        	this.menu.clientId();
        }
        if (button.getName().equals("Retour Local")) {
        	this.menu.localClic();
        }
        if (button.getName().equals("Retour Local ID")) {
        	this.menu.clientId();
        }
        if (button.getName().equals("SuivantNomClient")) {
        	this.menu.clientNom();
        }
        if (button.getName().equals("Connexion2")) {
        	this.menu.launchConnexionClient();
        	
        	
        }
	}

}