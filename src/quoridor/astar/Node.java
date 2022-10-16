package quoridor.astar;

public class Node {

	
	/**
	 * how far away the node is from the starting point
	 */
	private int g = 0;
	
	/**
	 * how far away that node is from the end node
	 */
	private int h;
	private int rowIndex;
	private int colIndex;

	/**
	 * previous node (to track the shortest path)
	 */
	private Node predecessor;
	
	/**
	 * the node may be a wall
	 */
	boolean isRBlock = false;
	boolean isLBlock = false;
	boolean isUBlock = false;
	boolean isDBlock = false;

	/**
	 * Create a node
	 * @param rowIndex of the node
	 * @param colIndex of the node 
	 */
	public Node(int rowIndex, int colIndex) {
		this.rowIndex = rowIndex;
		this.colIndex = colIndex;
	}


	/**
	 * Gets the g
	 * @return the g
	 */
	public int getG() {
		return g;
	}

	/**
	 * @param g new value of g
	 */
	public void setG(int g) {
		this.g = g;
	}

	/**
	 * @return the total cost function
	 */
	public int getF() {
		return this.g+this.h;
	}

	/**
	 * @return the h
	 */
	public int getH() {
		return h;
	}

	/**
	 * @param h new value of h
	 */
	public void setH(int h) {
		this.h = h;
	}

	/**
	 * @return the col index
	 */
	public int getRowIndex() {
		return rowIndex;
	}

	/**
	 * @param rowIndex the new value of the row index
	 */
	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}

	/**
	 * @return the col index
	 */
	public int getColIndex() {
		return colIndex;
	}

	/**
	 * @param colIndex the new value of the column index
	 */
	public void setColIndex(int colIndex) {
		this.colIndex = colIndex;
	}

	/**
	 * @return the predecessor nodes
	 */
	public Node getPredecessor() {
		return predecessor;
	}

	/**
	 * @param predecessor the predecessor node
	 */
	public void setPredecessor(Node predecessor) {
		this.predecessor = predecessor;
	}

	/**
	 * True if node2 is equal to this
	 */
	@Override
	public boolean equals(Object node2) {
		Node otherNode = (Node) node2;
		return this.rowIndex == otherNode.getRowIndex() && this.colIndex == otherNode.getColIndex();
	}
}
