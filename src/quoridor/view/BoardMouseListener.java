package quoridor.view;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTable;

public class BoardMouseListener implements MouseListener {
	
	private JTable board;
	private GameGUI gui;

	public BoardMouseListener(JTable board, GameGUI gui) {
		this.board = board;
		this.gui = gui;
	}

	@Override
	public void mouseClicked(MouseEvent event) {}

	@Override
	public void mouseEntered(MouseEvent event) {}

	@Override
	public void mouseExited(MouseEvent event) {}

	@Override
	public void mousePressed(MouseEvent event) {
		
		int x = this.board.rowAtPoint(event.getPoint());
		int y = this.board.columnAtPoint(event.getPoint());
		int[] move = new int[3];
		int cellX = event.getPoint().x - y*80;
		int cellY = event.getPoint().y - x*80;
		if (cellX <= 66 && cellY <= 66 ) {
			move[0] = 0;
			move[1] = x-1;
			move[2] = y-1;
			this.gui.setMove(move);
		}
		else if (cellX > 66 && cellY <= 66) {
			move[0] = 1;
			move[1] = x-1;
			move[2] = y-1;
			this.gui.setMove(move);
			
		}
		else if (cellX <= 66 && cellY > 66) {
			move[0] = 2;
			move[1] = x-1;
			move[2] = y-1;
			this.gui.setMove(move);
		}

	}

	@Override
	public void mouseReleased(MouseEvent event) {}

}
