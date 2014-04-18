package com.guti16.model;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.Observable;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import com.application.model.GeneralAttribute;

/**
 * This abstract class contain main parameter of the game and its setter and
 * getter function
 * 
 * @author Md. Ashiquzzaman & Rashik Hasnat
 * @version 1.00
 */

public abstract class GameAttribute extends Observable {
	public static boolean NETWORK_GAME;
	public static int NETWORK_GAME_PLAYER_ID;
	public static boolean FULL_SCREEN = false;
	public static Dimension FULL_SCREEN_SIZE = Toolkit.getDefaultToolkit()
			.getScreenSize();
	public static Dimension SMALL_SCREEN_SIZE = new Dimension(
			(FULL_SCREEN_SIZE.width * 9) / 10,
			(FULL_SCREEN_SIZE.height * 9) / 10);
	public static int MENU_BAR_HEIGHT = 0;
	public static Dimension MAX_DIMENTION;
	public static int GAME_BOARD_WIDTH;
	public static int VERTICAL_GAP = 25;
	public static int PLAYER_INFORMATION_PANE_HEIGHT = 70;
	public static int STATUS_BAR_HEIGHT = 40;
	public static int SCORE_BOARD[] = { 0, 0, 0 };
	public static int GAME_BOARD_HEIGHT;
	public static float LINE_DEPTH = 10;
	public static int GUTI_HEIGHT = 45;
	public static int GUTI_WIDTH = 45;
	public static int CONST_RATIO;
	public static Color GRID_COLOR = Color.WHITE;
	public static long TIME_EACH_TURN = 20000;
	public static final int STOPWATCH_ICON_WIDTH = 40;
	public static final int STOPWATCH_ICON_HEIGHT = 40;
	public static int PLAYER_IMAGE_WIDTH = 100;
	public static int PLAYER_IMAGE_HEIGHT = 75;
	public static String[] PLAYER_NAME = { "", "Ashiq", "Rashik" };
	private static Image PLAYER_IMAGE[] = {
			null,
			new ImageIcon(
					GeneralAttribute.class
							.getResource("/resource/images/avaters/p2.jpg"))
					.getImage(),
			new ImageIcon(
					GeneralAttribute.class
							.getResource("/resource/images/avaters/p1.jpg"))
					.getImage() };
public static Image BACKGROUND_IMAGE1 = new ImageIcon(
			GameAttribute.class
					.getResource("/resource/images/gameBack1.jpg"))
			.getImage();

	public static Icon GUTI_ICON[] = {
			null,
			new ImageIcon(
					GameAttribute.class
							.getResource("/resource/images/gutiP1.png")),
			new ImageIcon(
					GameAttribute.class.getResource("/resource/images/gutiP1S.png")),
			new ImageIcon(
					GameAttribute.class
							.getResource("/resource/images/gutiP2.png")),
			new ImageIcon(
					GameAttribute.class.getResource("/resource/images/gutiP2S.png")) };
	public static Icon STOP_WATCH_ICON = new ImageIcon(
			GameAttribute.class.getResource("/resource/images/stopWatch.png"));

	private long remainingTurnTime;
	protected int numberOfGuti[] = new int[3];
	protected int turn;
	private boolean doneSuccessfullyMove;
	private boolean changeNumberOfGuti[] = new boolean[3];
	private boolean changeGutiPosition;
	private boolean changeStopWatchTime;
	private Node[] changedNodeList;
	private boolean inAttack;

	protected boolean errormsg;
	protected boolean gameOverByTimeUp;
	protected boolean win;
	private int winer;
	public static double X_FACTOR = 1.0;

	/**
	 * Default Constructor
	 */
	public GameAttribute() {
		winer = 0;
		win = false;
		inAttack = false;
		errormsg = false;
		changeStopWatchTime = true;
		changeGutiPosition = true;
		numberOfGuti[1] = 16;
		numberOfGuti[2] = 16;
		turn = 1;
		remainingTurnTime = GameAttribute.TIME_EACH_TURN;

	}

	/**
	 * 
	 * @param dim
	 *            Frame Dimension
	 */
	public static void initilizeStaticMemebr(Dimension dim) {
		if (MAX_DIMENTION != null)
			X_FACTOR = (double) FULL_SCREEN_SIZE.width
					/ SMALL_SCREEN_SIZE.width;
		MAX_DIMENTION = dim;
		GAME_BOARD_WIDTH = MAX_DIMENTION.width;
		if (dim == SMALL_SCREEN_SIZE)
			GAME_BOARD_HEIGHT = MAX_DIMENTION.height
					- PLAYER_INFORMATION_PANE_HEIGHT - STATUS_BAR_HEIGHT - 50; // menu
																				// bar
																				// height
																				// +
																				// title
																				// bar
																				// height
																				// =
																				// 20+30
		else
			GAME_BOARD_HEIGHT = MAX_DIMENTION.height
					- PLAYER_INFORMATION_PANE_HEIGHT - STATUS_BAR_HEIGHT - 20; // menu
																				// bar
																				// height
																				// =
																				// 20
		CONST_RATIO = (GAME_BOARD_HEIGHT + GAME_BOARD_WIDTH) / 20;

	}

	public static void fullScreenMode(boolean mode) {
		if (mode) {
			initilizeStaticMemebr(FULL_SCREEN_SIZE);
			FULL_SCREEN = true;
		} else {
			initilizeStaticMemebr(SMALL_SCREEN_SIZE);
			FULL_SCREEN = false;
		}
	}

	/**
	 * Test if stop watch time has changed.
	 * 
	 * @return true if and only if the setChangeStopWatchTime method has been
	 *         called more recently than the clearChangedStopWatchTime method on
	 *         this object; false otherwise.
	 */
	public boolean hasChangeStopWatchTime() {
		return changeStopWatchTime;
	}

	/**
	 * Marks that stop-watch time having been changed;
	 */
	public void setChangeStopWatchTime() {
		changeStopWatchTime = true;
		setChanged();
		notifyObservers();
	}

	/**
	 * Indicates that stop-watch time has no longer changed or that has already
	 * updated.
	 */
	public void clearChangeStopWatchTime() {
		changeStopWatchTime = false;
	}

	/**
	 * Test if Guti Position has changed.
	 * 
	 * @return true if and only if the setChagedGutiPositon method has been
	 *         called more recently than the clearChangedGutiPosition method on
	 *         this object; false otherwise.
	 */
	public boolean hasChangeGutiPosition() {
		return changeGutiPosition;
	}

	/**
	 * Marks that guti position having been changed;
	 */
	public void setChangeGutiPosition() {
		changeGutiPosition = true;
		setChanged();
		notifyObservers();
	}

	/**
	 * Indicates that guti position has no longer changed or that has already
	 * updated.
	 */
	public void clearChangeGutiPosition() {
		changeGutiPosition = false;
	}

	/**
	 * Test if number of guti has changed.
	 * 
	 * @param playerId
	 *            Player Id
	 * @return true if and only if the setChangedNumberOfGuti method has been
	 *         called more recently than the clearChangedNumberOfGuti method on
	 *         this object; false otherwise.
	 */
	public boolean hasChangeNumberOfGuti(int playerId) {
		return changeNumberOfGuti[playerId];
	}

	/**
	 * Marks that number of guti having been changed;
	 * 
	 * @param playerId
	 *            Player Id
	 */
	public void setChangeNumberOfGuti(int playerId) {
		changeNumberOfGuti[playerId] = true;
		setChanged();
		notifyObservers();
	}

	/**
	 * Indicates that number of guti has no longer changed.
	 * 
	 * @param playerId
	 *            Player Id
	 */
	public void clearChangedNumberOfGuti(int playerId) {
		changeNumberOfGuti[playerId] = false;
	}

	/**
	 * Return Player name
	 * 
	 * @param playerNo
	 *            Player Id
	 * @return player name
	 */
	public static String getPlayerName(int playerNo) {
		return PLAYER_NAME[playerNo];
	}

	/**
	 * Return number of guti of specific player
	 * 
	 * @param playerNo
	 *            Player id
	 * @return number of guti
	 */
	public int getNumberOfGuti(int playerNo) {
		return numberOfGuti[playerNo];
	}

	/**
	 * Test if error message has generated.
	 * 
	 * @return boolean value
	 */
	public boolean hasErrorMessage() {
		return errormsg;
	}

	/**
	 * Return specific player name specified by playerNo, the unique id of
	 * player
	 * 
	 * @param playerNo
	 *            player id
	 * @return specific player Image specified by playerNo
	 */
	public static Image getPlayerImage(int playerNo) {
		return PLAYER_IMAGE[playerNo];
	}

	/**
	 * Change time turn when user change time from menu.
	 * 
	 * @param t
	 *            time turn specified by user or player
	 */
	public static void setTimeOut(int time) {
		TIME_EACH_TURN = time * 1000;
	}

	/**
	 * Decrease time bar length, this method is called when stop-watch time
	 * having been changed.
	 * 
	 * @param length
	 *            current time bar length
	 */
	public void setDecreaseTimeBarLength(double length) {
		setChanged();
		notifyObservers();
	}

	/**
	 * Reset remaining time and current time bars, this is method is called
	 * after changeTurn method having been invoked.
	 */
	public void resetAllTimer() {
		setRemainingTurnTime(GameAttribute.TIME_EACH_TURN);
		setChanged();
		notifyObservers();
	}

	/**
	 * Mark that specific player has been made at least 1 move (Eat or move)
	 * 
	 * @return true if and only if specific player having been made a move
	 *         otherwise false.
	 */
	public boolean hasMakeAnyMove() {
		return doneSuccessfullyMove;
	}

	/**
	 * Mark that player has made at least 1 move(Eat or move)
	 */
	public void setMakeAnyMove() {
		doneSuccessfullyMove = true;
		setChanged();
		notifyObservers();
	}

	/**
	 * Indicate that player turn has changed or already updated this
	 * information.
	 */
	public void clearMakeAnyMove() {
		doneSuccessfullyMove = false;
	}

	/**
	 * Test whether game is over or not.
	 * 
	 * @return true if game have been over otherwise false.
	 */
	public boolean isGameOverByTimeUp() {
		return gameOverByTimeUp;
	}

	/**
	 * Test whether player has in attack or not
	 * 
	 * @return true if player has been in attack otherwise false.
	 */
	public boolean hasInAttack() {
		return inAttack;
	}

	/**
	 * Marks that player has in attack.
	 */
	public void setInAttack() {
		inAttack = true;
		setChanged();
		notifyObservers();
	}

	/**
	 * Indicate that player has no longer in attack.
	 */
	public void clearInAttack() {
		inAttack = false;
	}

	/**
	 * Add node in changed list which has changed.
	 * 
	 * @param nodes
	 *            changed nodes
	 */
	public void addChangedGuti(Node... nodes) {
		changedNodeList = nodes;
		setChanged();
		notifyObservers();
	}

	/**
	 * Return changed guti list which has been changed.
	 * 
	 * @return changed guti list.
	 */
	public Node[] getChangedGuti() {
		return changedNodeList;

	}

	/**
	 * Game over and notify all observer
	 */
	public void setGameOverByTimeUp() {
		gameOverByTimeUp = true;
	}

	public void clearGameOverByTimeUp() {
		gameOverByTimeUp = false;
	}

	public static void setPlayerName(String name, int playerId) {
		PLAYER_NAME[playerId] = name;
	}

	public static void setPlayerImage(Image image, int playerId) {
		PLAYER_IMAGE[playerId] = image;
	}

	/**
	 * 
	 * @param playerId
	 */
	public void setWiner(int playerId) {
		winer = playerId;
		SCORE_BOARD[playerId]++;
		setChanged();
		notifyObservers();
	}

	public int getWiner() {
		return winer;
	}

	public static Point getScoreBoard() {
		return new Point(SCORE_BOARD[1], SCORE_BOARD[2]);
	}

	public static void resetScoreBoard() {
		SCORE_BOARD[0] = SCORE_BOARD[1] = SCORE_BOARD[2] = 0;
	}

	/**
	 * @return the remainingTurnTime
	 */
	public long getRemainingTurnTime() {
		return remainingTurnTime;
	}

	/**
	 * @param remainingTurnTime
	 *            the remainingTurnTime to set
	 */
	public void setRemainingTurnTime(long remainingTurnTime) {
		this.remainingTurnTime = remainingTurnTime;
	}

	public static Point setCenterScreen(Dimension dim) {
		return new Point((FULL_SCREEN_SIZE.width - dim.width) / 2,
				(FULL_SCREEN_SIZE.height - dim.height) / 2);
	}
}
