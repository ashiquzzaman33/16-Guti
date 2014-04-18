package com.guti16.model;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.guti16.controller.Guti_16_Game;

/**
 * This class perform most logically operation of the game it is the main logic
 * class
 * 
 * @author Md. Ashiquzzaman & Rashik Hasnat
 * @version 1.00
 */
public class GameLogic extends GameAttribute {

	private int gameGrid[][] = new int[9][5];
	private Node node[][] = new Node[9][5];
	private Node selectedNode = null;
	private Node vulnarableNode = null;
	private ExecutorService timerExecutor = Executors.newCachedThreadPool();
	private Timer timer;
	private Guti_16_Game guti_16_game;
	private boolean clz;

	/**
	 * Initialize game logic
	 */
	public GameLogic(Guti_16_Game g) {
		guti_16_game = g;
		initNode();
		setAttakAndMoveNode();
		clearGameOverByTimeUp();
		gridInitialize();
		resetAllTimer();
		clz = false;
	}

	/**
	 * This method reset game timer when player turn change or player make a
	 * move or attack
	 */
	private void resetGameTimer() {
		resetAllTimer();
		timer.resetTimer();
	}

	public void startTimer() {
		setWiner(0);
		timer = new Timer(this);
		timerExecutor.execute(timer);
	}

	public void close() {
		if (!timerExecutor.isShutdown())
			timerExecutor.shutdownNow();
		clz = true;
		guti_16_game.close();
	}

	public boolean isClosed() {
		return clz;
	}

	/**
	 * This method initialize all valid point of grid
	 */
	private void gridInitialize() {
		for (int i = 2; i <= 3; i++) {
			for (int j = 0; j < 5; j++) {
				gameGrid[i][j] = 1;
				node[i][j].setPlayer(1);
				gameGrid[i + 3][j] = 3;
				node[i + 3][j].setPlayer(2);
			}
		}
		for (int i = 0; i <= 1; i++) {
			for (int j = 0; j < 5; j++) {
				if (i == 0) {
					if (j % 2 == 0) {
						gameGrid[0][j] = 1;
						node[0][j].setPlayer(1);
						gameGrid[8][j] = 3;
						node[8][j].setPlayer(2);
					} else {
						gameGrid[0][j] = -1;
						gameGrid[8][j] = -1;
					}
				} else {
					if (j >= 1 && j <= 3) {
						gameGrid[1][j] = 1;
						node[1][j].setPlayer(1);
						gameGrid[7][j] = 3;
						node[7][j].setPlayer(2);
					} else {
						gameGrid[1][j] = -1;
						gameGrid[7][j] = -1;
					}
				}
			}
		}
		for (int j = 0; j < 5; j++) {
			gameGrid[4][j] = 0;
		}
		setChanged();
		notifyObservers();
	}

	/**
	 * This method instantiate all point of the game board and initialize each
	 * point/node position
	 */
	private void initNode() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 5; j++) {
				node[i][j] = new Node(i, j);
			}
		}
	}

	/**
	 * This method return specific node
	 * 
	 * @param x
	 *            the value used to be as first index of Node array
	 * @param y
	 *            the value used to be as second index of Node array
	 * @return Node identifies from x and y index of node;
	 */
	public Node getNode(int x, int y) {
		return node[x][y];
	}

	/**
	 * This method return specific value from gridValue array by using Node
	 * reference
	 * 
	 * @param n
	 *            used to determine a specific node
	 * @return player identifier
	 */
	public int getGridValue(Node n) {
		return gameGrid[n.getRelativeX()][n.getRelativeY()];
	}

	/**
	 * 
	 * @param n
	 *            Node used to identify specific node to select that node
	 */
	public void setSelectedNode(Node n) {
		selectedNode = n;
		if (n != null) // Null from changeTurn(int p)
			setGridValue(n.getRelativeX(), n.getRelativeY(),
					getGridValue(n) + 1);
	}

	/**
	 * This method return selected node reference
	 * 
	 * @return Node
	 */
	public Node getSelectedNode() {
		return selectedNode;
	}

	/**
	 * This method return current turn of this game
	 * 
	 * @return turn
	 */
	public int getTurn() {
		return turn;
	}
	/**
	 * This method invoked when player clicked in game court then this method
	 * manage all logical operation of the game
	 * 
	 * @param x
	 *            X coordinate of game court clicked by player
	 * @param y
	 *            Y coordinate of game court clicked by player
	 */
	public void processClick(int x, int y) {
		Node p = getNode(x, y);
		if (p != null) {
			if (p.getPlayer() == getTurn() || p.getPlayer() == 0) {

				if (getGridValue(p) != 0) {
					if (getSelectedNode() != null) {

						if (!hasInAttack()) {
							addChangedGuti(selectedNode, p);
							deSelectNode(selectedNode);
							setSelectedNode(p);
						}
					} else {
						addChangedGuti(p);
						setSelectedNode(p);
					}
					setChangeGutiPosition();
				} else {
					if (getSelectedNode() != null) {
						if (isValidMove(getSelectedNode(), p)) {
							clearInAttack();
							addChangedGuti(getSelectedNode(), p);
							moveGuti(getSelectedNode(), p);
							addChangedGuti(p);
							deSelectNode(p);
							changeTurn();
						} else if (isValidAttack(getSelectedNode(), p)) {

							setInAttack();
							setMakeAnyMove();
							addChangedGuti(getSelectedNode(), p, vulnarableNode);
							moveGuti(getSelectedNode(), p);
							deSelectNode(p);
							setSelectedNode(p);
							addChangedGuti(getSelectedNode(), p, vulnarableNode);
							removeGuti(vulnarableNode);
							decreaseGuti(getOppositionPlayerNo(getTurn()));
							resetGameTimer();
							vulnarableNode = null;
						} else {
							errormsg = true;
							errormsg = false;
						}
					}
					setChangeGutiPosition();

				}
			}

		}

	}

	/**
	 * This method De-select current selected node
	 * 
	 * @param n
	 *            Node reference to be de-selected
	 */
	private void deSelectNode(Node n) {
		if (n.getPlayer() == 1)
			setGridValue(n, 1);
		else if (n.getPlayer() == 2)
			setGridValue(n, 3);
	}

	/**
	 * This method check whether it is opposition player guti or not
	 * 
	 * @param n
	 *            Node reference
	 * @return boolean value whether it is opposition player guti or not
	 */
	public boolean isOpposition(Node n) {
		if ((n.getPlayer() != 0 && getTurn() != n.getPlayer()))
			return true;
		return false;
	}

	/**
	 * This method physically move guti source to destination
	 * 
	 * @param source
	 *            source point of the node
	 * @param des
	 *            destination point of the node
	 */
	private void moveGuti(Node source, Node des) {
		node[des.getRelativeX()][des.getRelativeY()].setPlayer(source
				.getPlayer());
		setGridValue(des.getRelativeX(), des.getRelativeY(),
				getGridValue(source));
		node[source.getRelativeX()][source.getRelativeY()].setPlayer(0);
		setGridValue(source.getRelativeX(), source.getRelativeY(), 0);
	}

	/**
	 * This method remove a guti from game court
	 * 
	 * @param n
	 *            Node reference
	 */
	private void removeGuti(Node n) {
		setGridValue(n, 0);
		node[n.getRelativeX()][n.getRelativeY()].setPlayer(0);
	}

	/**
	 * This method determine whether performed move is valid or nod
	 * 
	 * @param source
	 *            Source Node
	 * @param des
	 *            Destination Node
	 * @return boolean value
	 */
	private boolean isValidMove(Node source, Node des) {

		if (hasInAttack())
			return false;
		Node mList[] = node[source.getRelativeX()][source.getRelativeY()]
				.getMoveList();
		for (Node n : mList) {
			if (des == n)
				return true;
		}
		return false;
	}

	/**
	 * This method check whether performed attack is valid or nod
	 * 
	 * @param source
	 *            Source Node
	 * @param des
	 *            Destination Node
	 * @return boolean value of status
	 */
	private boolean isValidAttack(Node source, Node des) {

		Node aList[] = node[source.getRelativeX()][source.getRelativeY()]
				.getAttackList();
		Node mList[] = node[source.getRelativeX()][source.getRelativeY()]
				.getMoveList();
		for (int i = 0; i < aList.length; i++) {
			if (des == aList[i]) {
				if (isOpposition(mList[i])) {
					vulnarableNode = mList[i];
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * This method initialize attack list and move list of all Node
	 */
	private void setAttakAndMoveNode() { // Attack left right down up left-corner
										// right-corner, Move first as Attack
										// then left right down up
		// Row 1
		node[0][0].setAttackList(getNode(0, 4), getNode(2, 2));
		node[0][0].setMoveList(getNode(0, 2), getNode(1, 1));

		node[0][2].setAttackList(getNode(2, 2));
		node[0][2].setMoveList(getNode(1, 2), getNode(0, 0), getNode(0, 4));

		node[0][4].setAttackList(getNode(0, 0), getNode(2, 2));
		node[0][4].setMoveList(getNode(0, 2), getNode(1, 3));
		// Row 2
		node[1][1].setAttackList(getNode(1, 3), getNode(3, 3));
		node[1][1].setMoveList(getNode(1, 2), getNode(2, 2), getNode(0, 0));

		node[1][2].setAttackList(getNode(3, 2));
		node[1][2].setMoveList(getNode(2, 2), getNode(1, 1), getNode(1, 3),
				getNode(0, 2));

		node[1][3].setAttackList(getNode(1, 1), getNode(3, 1));
		node[1][3].setMoveList(getNode(1, 2), getNode(2, 2), getNode(0, 4));
		// Row 3
		node[2][0].setAttackList(getNode(2, 2), getNode(4, 0), getNode(4, 2));
		node[2][0].setMoveList(getNode(2, 1), getNode(3, 0), getNode(3, 1));

		node[2][1].setAttackList(getNode(2, 3), getNode(4, 1));
		node[2][1].setMoveList(getNode(2, 2), getNode(3, 1), getNode(2, 0));

		node[2][2].setAttackList(getNode(2, 0), getNode(2, 4), getNode(0, 0),
				getNode(0, 2), getNode(0, 4), getNode(4, 0), getNode(4, 2),
				getNode(4, 4));
		node[2][2].setMoveList(getNode(2, 1), getNode(2, 3), getNode(1, 1),
				getNode(1, 2), getNode(1, 3), getNode(3, 1), getNode(3, 2),
				getNode(3, 3));

		node[2][3].setAttackList(getNode(2, 1), getNode(4, 3));
		node[2][3].setMoveList(getNode(2, 2), getNode(3, 3), getNode(2, 4));

		node[2][4].setAttackList(getNode(2, 2), getNode(4, 2), getNode(4, 4));
		node[2][4].setMoveList(getNode(2, 3), getNode(3, 3), getNode(3, 4));
		// Row 4
		node[3][0].setAttackList(getNode(3, 2), getNode(5, 0));
		node[3][0].setMoveList(getNode(3, 1), getNode(4, 0), getNode(2, 0));

		node[3][1].setAttackList(getNode(3, 3), getNode(1, 3), getNode(5, 1),
				getNode(5, 3));
		node[3][1].setMoveList(getNode(3, 2), getNode(2, 2), getNode(4, 1),
				getNode(4, 2), getNode(3, 0), getNode(2, 0), getNode(2, 1),
				getNode(4, 0));

		node[3][2].setAttackList(getNode(3, 0), getNode(3, 4), getNode(1, 2),
				getNode(5, 2));
		node[3][2].setMoveList(getNode(3, 1), getNode(3, 3), getNode(2, 2),
				getNode(4, 2));

		node[3][3].setAttackList(getNode(3, 1), getNode(1, 1), getNode(5, 1),
				getNode(5, 3));
		node[3][3].setMoveList(getNode(3, 2), getNode(2, 2), getNode(4, 2),
				getNode(4, 3), getNode(3, 4), getNode(2, 3), getNode(2, 4),
				getNode(4, 4));

		node[3][4].setAttackList(getNode(3, 2), getNode(5, 4));
		node[3][4].setMoveList(getNode(3, 3), getNode(4, 4), getNode(2, 4));
		// Row 5
		node[4][0].setAttackList(getNode(4, 2), getNode(2, 0), getNode(2, 2),
				getNode(6, 0), getNode(6, 2));
		node[4][0].setMoveList(getNode(4, 1), getNode(3, 0), getNode(3, 1),
				getNode(5, 0), getNode(5, 1));

		node[4][1].setAttackList(getNode(4, 3), getNode(2, 1), getNode(6, 1));
		node[4][1].setMoveList(getNode(4, 2), getNode(3, 1), getNode(5, 1),
				getNode(4, 0));

		node[4][2].setAttackList(getNode(4, 0), getNode(4, 4), getNode(2, 0),
				getNode(2, 2), getNode(2, 4), getNode(6, 0), getNode(6, 2),
				getNode(6, 4));
		node[4][2].setMoveList(getNode(4, 1), getNode(4, 3), getNode(3, 1),
				getNode(3, 2), getNode(3, 3), getNode(5, 1), getNode(5, 2),
				getNode(5, 3));

		node[4][3].setAttackList(getNode(4, 1), getNode(2, 3), getNode(6, 3));
		node[4][3].setMoveList(getNode(4, 2), getNode(3, 3), getNode(5, 3),
				getNode(4, 4));

		node[4][4].setAttackList(getNode(4, 2), getNode(2, 2), getNode(2, 4),
				getNode(6, 2), getNode(6, 4));
		node[4][4].setMoveList(getNode(4, 3), getNode(3, 3), getNode(3, 4),
				getNode(5, 3), getNode(5, 4));
		// Row 6
		node[5][0].setAttackList(getNode(5, 2), getNode(3, 0));
		node[5][0].setMoveList(getNode(5, 1), getNode(4, 0), getNode(6, 0));

		node[5][1].setAttackList(getNode(5, 3), getNode(3, 1), getNode(3, 3),
				getNode(7, 3));
		node[5][1].setMoveList(getNode(5, 2), getNode(4, 1), getNode(4, 2),
				getNode(6, 2), getNode(5, 0), getNode(4, 0), getNode(6, 0),
				getNode(6, 1));

		node[5][2].setAttackList(getNode(5, 0), getNode(5, 4), getNode(3, 2),
				getNode(7, 2));
		node[5][2].setMoveList(getNode(5, 1), getNode(5, 3), getNode(4, 2),
				getNode(6, 2));

		node[5][3].setAttackList(getNode(5, 1), getNode(3, 1), getNode(3, 3),
				getNode(7, 1));
		node[5][3].setMoveList(getNode(5, 2), getNode(4, 2), getNode(4, 3),
				getNode(6, 2), getNode(5, 4), getNode(4, 4), getNode(6, 3),
				getNode(6, 4));

		node[5][4].setAttackList(getNode(5, 2), getNode(3, 4));
		node[5][4].setMoveList(getNode(5, 3), getNode(4, 4), getNode(6, 4));
		// Row 7
		node[6][0].setAttackList(getNode(6, 2), getNode(4, 0), getNode(4, 2));
		node[6][0].setMoveList(getNode(6, 1), getNode(5, 0), getNode(5, 1));

		node[6][1].setAttackList(getNode(6, 3), getNode(4, 1));
		node[6][1].setMoveList(getNode(6, 2), getNode(5, 1), getNode(6, 0));

		node[6][2].setAttackList(getNode(6, 0), getNode(6, 4), getNode(4, 0),
				getNode(4, 2), getNode(4, 4), getNode(8, 0), getNode(8, 2),
				getNode(8, 4));
		node[6][2].setMoveList(getNode(6, 1), getNode(6, 3), getNode(5, 1),
				getNode(5, 2), getNode(5, 3), getNode(7, 1), getNode(7, 2),
				getNode(7, 3));

		node[6][3].setAttackList(getNode(6, 1), getNode(4, 3));
		node[6][3].setMoveList(getNode(6, 2), getNode(5, 3), getNode(6, 4));

		node[6][4].setAttackList(getNode(6, 2), getNode(4, 2), getNode(4, 4));
		node[6][4].setMoveList(getNode(6, 3), getNode(5, 3), getNode(5, 4));
		// Row 8
		node[7][1].setAttackList(getNode(7, 3), getNode(5, 3));
		node[7][1].setMoveList(getNode(7, 2), getNode(6, 2), getNode(8, 0));

		node[7][2].setAttackList(getNode(5, 2));
		node[7][2].setMoveList(getNode(6, 2), getNode(7, 1), getNode(7, 3),
				getNode(8, 2));

		node[7][3].setAttackList(getNode(7, 1), getNode(5, 1));
		node[7][3].setMoveList(getNode(7, 2), getNode(6, 2), getNode(8, 4));
		// Row 9
		node[8][0].setAttackList(getNode(8, 4), getNode(6, 2));
		node[8][0].setMoveList(getNode(8, 2), getNode(7, 1));

		node[8][2].setAttackList(getNode(6, 2));
		node[8][2].setMoveList(getNode(7, 2), getNode(8, 0), getNode(8, 4));

		node[8][4].setAttackList(getNode(8, 0), getNode(6, 2));
		node[8][4].setMoveList(getNode(8, 2), getNode(7, 3));

	}

	/**
	 * This method is called when turn time end.
	 */
	public void timeUp() {
		if (hasMakeAnyMove())
			changeTurn();
		else {
			setGameOverByTimeUp();
			setWiner(getOppositionPlayerNo(getTurn()));
			setChanged();
			notifyObservers();
			close();

		}

	}

	/**
	 * Return remaining time in second unit
	 * 
	 * @return remaining time(s)
	 */
	public int getRemainingTimeSecond() {
		if (getRemainingTurnTime() >= 0) {
			return (int) (getRemainingTurnTime() / 1000);
		}
		return 0;
	}

	/**
	 * Return remaining time in milliseconds unit
	 * 
	 * @return remaining time(ms)
	 */
	public int getRemainingTimeMill() {
		if (getRemainingTurnTime() > 0)
			return (int) (getRemainingTurnTime() - getRemainingTimeSecond() * 1000) / 10;
		return 0;
	}

	/**
	 * Return Opposition Player id
	 * 
	 * @param playerId
	 *            current player id
	 * @return Player id(opposition)
	 */
	public int getOppositionPlayerNo(int playerId) {
		if (playerId == 1)
			return 2;
		else
			return 1;
	}

	/**
	 * Decrease number of guti
	 * 
	 * @param playerNo
	 *            Id of player
	 */
	public void decreaseGuti(int playerNo) {
		numberOfGuti[playerNo]--;
		if (numberOfGuti[playerNo] == 0) {
			deSelectNode(getSelectedNode());
			setWiner(getTurn());
			setChanged();
			notifyObservers();
			close();
		}
		setChangeNumberOfGuti(playerNo);
	}

	/**
	 * Decrease remaining time
	 * 
	 * @param t
	 *            time in milliseconds
	 */
	public void setDecreaseRemainingTime(long t) {
		setRemainingTurnTime(getRemainingTurnTime() - t);
		setChangeStopWatchTime();
	}

	/**
	 * This method set grid value by using relative index and notify observer
	 * 
	 * @param x
	 *            The value used to be as first Index of gameGrid array
	 * @param y
	 *            The value used to be second Index of gameGrid array
	 * @param playerNo
	 *            Player identifier
	 */
	public void setGridValue(int x, int y, int playerNo) {

		gameGrid[x][y] = playerNo;
		setChanged();
		notifyObservers();
	}

	/**
	 * This method set grid value by using node reference
	 * 
	 * @param n
	 *            The node type value used to identify specific node
	 * @param value
	 *            that set to the gameGrid value specified by n
	 */
	public void setGridValue(Node n, int value) {
		gameGrid[n.getRelativeX()][n.getRelativeY()] = value;
		setChanged();
		notifyObservers();
	}

	/**
	 * This method change current turn
	 */
	public void changeTurn() {
		if (turn == 1)
			turn = 2;
		else
			turn = 1;
		clearInAttack();
		clearMakeAnyMove();
		deSelectNode(selectedNode);
		setSelectedNode(null);
		resetGameTimer();
		setChangeGutiPosition();
	}
}
