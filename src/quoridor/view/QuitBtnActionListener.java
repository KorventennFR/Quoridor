package quoridor.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuitBtnActionListener implements ActionListener {
	
	GameGUI gui;

	public QuitBtnActionListener(GameGUI gui) {
		this.gui = gui;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		this.gui.quit();

	}

}
