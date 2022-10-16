package quoridor.view;

import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.table.AbstractTableModel;

import quoridor.control.Controller;
import quoridor.model.Cell;
import quoridor.model.Player;

public class GameBoardModel extends AbstractTableModel {
	
	Controller controller;

	public GameBoardModel(Controller controller) {
		this.controller = controller;
	}

	@Override
	public int getColumnCount() {
		return 10;
	}

	@Override
	public int getRowCount() {
		return 10;
	}

	@Override
	public Object getValueAt(int x, int y) {
		ImageIcon ret = null;
		if (x == 0) {
			try {
				ret = new ImageIcon(ImageIO.read(getClass().getResource("/tiles/index_U" + y + ".png")));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else if (y == 0) {
			try {
				ret = new ImageIcon(ImageIO.read(getClass().getResource("/tiles/index_L" + x + ".png")));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else {
			Cell[][] grid = controller.getGame().getBoard().getGrid();
			Player p = grid[x-1][y-1].getPawnPlayer();
			Player[] players = controller.getGame().getPlayers();
			String pawn = "void";
			String RWall = "void";
			String DWall = "void";
			
			if (p != null) {
				if (p.equals(players[0])) {
					if (players.length == 2) pawn = "BLACK";
					else pawn = "BLUE";
				}
				if (p.equals(players[1])) {
					if (players.length == 2) pawn = "WHITE";
					else pawn = "RED";
				}
				if (players.length == 4) {
					if (p.equals(players[2])) pawn = "YELLOW";
					else if (p.equals(players[3])) pawn = "GREEN";
				}
			}
			
			if (grid[x-1][y-1].isWallV()) RWall = "RD";
			if (x > 1) {
				if (grid[x-2][y-1].isWallV()) RWall = "RU";
			}
			
			if (grid[x-1][y-1].isWallH()) DWall = "DR";
			if (y > 1) {
				if (grid[x-1][y-2].isWallH()) DWall = "DL";
			}
			
			try {
				ret = new ImageIcon(ImageIO.read(getClass().getResource("/tiles/cell_" + pawn + "_" + RWall + "_" + DWall + ".png")));
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		return ret;
	}
	
	public Class<? extends Object> getColumnClass(int c) {
		return this.getValueAt(0, c).getClass();
	}

}
