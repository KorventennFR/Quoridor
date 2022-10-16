package test.astar;
import quoridor.model.*;
import quoridor.view.*;
import quoridor.control.*;
import quoridor.*;
import quoridor.astar.*;

import static org.junit.Assert.*;
import org.junit.Test;



public class TestAStar {
	
	@Test
	public void testSearch() {
		Cell[][] tab = new Cell[6][6];
		int[] expected= {0, 1};
		AStar algoAStar = new AStar(tab, 0, 0, 0, 3);
		assertNotNull(algoAStar);
		algoAStar.search();
		int[][] ret = algoAStar.getPath();
		assertArrayEquals(expected, ret[1]);
	}
	
	@Test
	public void testNode() {
		Node n1 = new Node(1, 2);
		Node n2 = new Node(1, 2);
		Node n3 = new Node(5, 3);
		assertTrue(n1.equals(n2));
		assertFalse(n1.equals(n3));
	}
}
