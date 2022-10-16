package quoridor.astar;

import java.util.Comparator;

/**
 * Implementation of comparator
 * Used for the PriorityQueue
 */
public class NodeComparator implements Comparator<Node>{

	/**
	 * implement compare
	 */
	public int compare(Node node1, Node node2) {

		if(node1.getF()<node2.getF()) return -1;
		if(node1.getF()>node2.getF()) return +1;
		return 0;
	}
}
