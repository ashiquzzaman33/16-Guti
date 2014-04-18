package com.guti16.model;

/**
 * This class is logical representation of all valid point that is used of stay,
 * move or attack a guti.
 * 
 * @author Md. Ashiquzzaman & Rashik Hasnat
 * @version 1.00
 */
public class Node {
	private int player;
	private int relativeX;
	private int relativeY;
	private Node moveList[];
	private Node attackList[];

	/**
	 * Initialize Node
	 * 
	 * @param x
	 *            Relative X coordinate of Node
	 * @param y
	 *            Relative Y coordinate of Node
	 */
	public Node(int x, int y) {
		player = 0;
		relativeX = x;
		relativeY = y;
	}

	/**
	 * Getter method return Relative X coordinate of this node
	 * 
	 * @return relative X coordinate
	 */
	public int getRelativeX() {
		return relativeX;
	}

	/**
	 * Getter method return Relative Y coordinate of this node
	 * 
	 * @return relative Y coordinate
	 */
	public int getRelativeY() {
		return relativeY;
	}

	/**
	 * Setter method set player identifier
	 */
	public void setPlayer(int value) {
		player = value;
	}

	/**
	 * Getter Method get player identifier
	 * 
	 * @return player identifier
	 */
	public int getPlayer() {
		return player;
	}

	/**
	 * Setter method, set attack list with variable argument list
	 * 
	 * @param aList
	 *            variable argument list of attack list
	 */
	public void setAttackList(Node... aList) {
		attackList = aList;
	}

	/**
	 * Setter method, set Move list with variable argument list
	 * 
	 * @param mList
	 *            variable argument list of move list
	 */
	public void setMoveList(Node... mList) {
		moveList = mList;
	}

	/**
	 * Getter method, return move list array
	 * 
	 * @return Node type array
	 */
	public Node[] getMoveList() {
		return moveList;
	}

	/**
	 * Getter method, return attack list array
	 * 
	 * @return Node type array
	 */
	public Node[] getAttackList() {
		return attackList;
	}
}
