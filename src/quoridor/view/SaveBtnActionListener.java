package quoridor.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class SaveBtnActionListener implements ActionListener {

	private GameGUI gameGUI;
	
	public SaveBtnActionListener(GameGUI gameGUI) {
		this.gameGUI = gameGUI;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton but = (JButton) e.getSource();
		if (but.getName().equals("retourJeu")) {
			this.gameGUI.getFrame().dispose();
		}
		if (but.getName().equals("save")) {
			this.gameGUI.getController().getGame().save(this.gameGUI.getSaisie().getText());
			this.gameGUI.getFrame().dispose();
		}
		if (but.getName().equals("Sauvegarder")) {
			this.gameGUI.saveClic();
		}
		

	}

}
