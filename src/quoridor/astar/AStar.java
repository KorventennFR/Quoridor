package quoridor.astar;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import quoridor.model.Cell;

public class AStar {
	
	private Node[][] searchSpace;
	
	/**
	 * where we start
	 */
	private Node startNode;
	
	/**
	 * where we want to go
	 */
	private Node finalNode;
	
	/**
	 * the set of nodes that we already visited
	 */
	private List<Node> closedSet;
	
	/**
	 * not visited yet
	 */
	private Queue<Node> openSet;

	private final int HORIZONTAL_VERTICAL_COST = 1;
	private final int NUM_ROWS;
	private final int NUM_COLS;

	/**
	 * Initialize the AStar
	 * @param tab the game's board
	 * @param startX the x coordinate of the starting node
	 * @param startY the y coordinate of the starting node
	 * @param endX the x coordinate of the ending node 
	 * @param endY the y coordinate of the ending node
	 */
	public AStar(Cell[][] tab, int startX, int startY, int endX, int endY) {
		this.NUM_COLS = tab[0].length;
		this.NUM_ROWS = tab.length;
		this.searchSpace = new Node[this.NUM_ROWS][this.NUM_COLS];
		this.openSet = new PriorityQueue<>(new NodeComparator());
		this.closedSet = new ArrayList<>();
		initializeSearchSpace(tab, startX, startY, endX, endY);
	}

	/**
	 * It Initializes the search space, placing walls and starting and ending nodes
	 * @param tab the game's board
	 * @param startX the x coordinate of the starting node
	 * @param startY the y coordinate of the starting node
	 * @param endX the x coordinate of the ending node 
	 * @param endY the y coordinate of the ending node
	 */
	private void initializeSearchSpace(Cell[][] tab, int startX, int startY, int endX, int endY) {
		//Initialize all the nodes on the Cell grid and placing walls
		for (int i = 0; i < NUM_ROWS; i++) {
			for (int j = 0; j < NUM_COLS; j++) {
				this.searchSpace[i][j] = new Node(i, j);
				this.searchSpace[i][j].isDBlock = tab[i][j].checkBottomWall();
				this.searchSpace[i][j].isUBlock = tab[i][j].checkTopWall();
				this.searchSpace[i][j].isRBlock = tab[i][j].checkRightWall();
				this.searchSpace[i][j].isLBlock = tab[i][j].checkLeftWall();
			}
		}
		
		this.startNode = this.searchSpace[startX][startY];
		this.finalNode = this.searchSpace[endX][endY];
	}

	/**
	 * Does the search through the searchSpace
	 */
	public void search() {

		startNode.setH(manhattanHeuristic(startNode, finalNode));
		openSet.add(startNode);
		while (!openSet.isEmpty()) {
			Node currentNode = openSet.poll();
			if(currentNode.equals(finalNode)) return;
			
			openSet.remove(currentNode);
			closedSet.add(currentNode);
			
			for (Node neighbour : getAllNeighbours(currentNode)) {
				if (closedSet.contains(neighbour)) continue;
				if (!openSet.contains(neighbour)) openSet.add(neighbour);
				
				neighbour.setPredecessor(currentNode);
			}
		}
	}

	/**
	 * Does the search with an existing searchSpace
	 */
	public void newSearch(int startX, int startY, int endX, int endY) {
		this.openSet = new PriorityQueue<>(new NodeComparator());
		this.closedSet = new ArrayList<>();
		this.startNode = this.searchSpace[startX][startY];
		this.finalNode = this.searchSpace[endX][endY];
		for (int i = 0; i < NUM_ROWS; i++) {		//Puts all predecessor to null;
			for (int j = 0; j < NUM_COLS; j++) {
				searchSpace[i][j].setPredecessor(null);
			}
		}
		this.search();
	}
	
	
	/**
	 * get the neighbours of a Node, does not give the wall
	 * @param node the node we are looking
	 * @return a list a of neighbours
	 */
	private List<Node> getAllNeighbours(Node node) {
		//We store the neighbours in a list (4 possible neighbours)
		int x = node.getRowIndex();
		int y = node.getColIndex();
		List<Node> neighboursList = new ArrayList<>();
		
		if (!node.isUBlock) {
			searchSpace[x-1][y].setH(manhattanHeuristic(searchSpace[x-1][y], finalNode));
			searchSpace[x-1][y].setG(node.getG()+this.HORIZONTAL_VERTICAL_COST);
			neighboursList.add(this.searchSpace[x-1][y]);
		}
		if (!node.isDBlock) {
			searchSpace[x+1][y].setH(manhattanHeuristic(searchSpace[x+1][y], finalNode));
			searchSpace[x+1][y].setG(node.getG()+this.HORIZONTAL_VERTICAL_COST);
			neighboursList.add(this.searchSpace[x+1][y]);
		}
		if (!node.isLBlock) {
			searchSpace[x][y-1].setH(manhattanHeuristic(searchSpace[x][y-1], finalNode));
			searchSpace[x][y-1].setG(node.getG()+this.HORIZONTAL_VERTICAL_COST);
			neighboursList.add(this.searchSpace[x][y-1]);
		}
		if (!node.isRBlock) {
			searchSpace[x][y+1].setH(manhattanHeuristic(searchSpace[x][y+1], finalNode));
			searchSpace[x][y+1].setG(node.getG()+this.HORIZONTAL_VERTICAL_COST);
			neighboursList.add(this.searchSpace[x][y+1]);
		}
		
		return neighboursList;
	}

	/**
	 * Calculate the distance between the current and the end node
	 * We reduce the heuristic value to be sure we have the best path (Wiki A* for more info)
	 * @param node1 the current node
	 * @param node2 the end node
	 * @return the distance
	 */
	public int manhattanHeuristic(Node node1, Node node2) {
		return (int)((Math.abs(node1.getRowIndex()-node2.getRowIndex()) + Math.abs(node1.getColIndex()-node2.getColIndex()))*0.7);
	}

	/**
	 * Get the shortest path.
	 * @return the path
	 */
	public int[][] getPath() {
		Node node = finalNode;
		ArrayList<Node> tab = new ArrayList<>();
		while (node != null) {
			tab.add(node);
			node = node.getPredecessor();
		}
		
		int[][] ret = new int[tab.size()][2];
		for (int i = 0; i < ret.length; i++) {
			ret[i][0] = tab.get(tab.size()-1).getRowIndex();
			ret[i][1] = tab.get(tab.size()-1).getColIndex();
			tab.remove(tab.size()-1);
		}
		if (ret.length == 1) ret = null;
		return ret;
	}
	
	/**
	 * Does the search and get the path
	 * @param tab the game's board
	 * @param startX the x coordinate of the starting node
	 * @param startY the y coordinate of the starting node
	 * @param endX the x coordinate of the ending node 
	 * @param endY the y coordinate of the ending node
	 * @return the shortest path, or null when there is no path
	 */
	public static int[][] staticSearch(Cell[][] tab, int startX, int startY, int endX, int endY){
		int[][] ret;
		AStar algo = new AStar(tab, startX, startY, endX, endY);
		algo.search();
		ret = algo.getPath();
		return ret;
	}
}
