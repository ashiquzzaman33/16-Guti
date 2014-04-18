package com.guti16.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.guti16.model.GameAttribute;
import com.guti16.model.GameLogic;

/**
 * This class draws status bar pane where game turn, turn time and other
 * notification shows
 * 
 * @author Ashiquzzaman
 * @version 1.00
 */
public class StatusBarPane extends JPanel implements Observer {
	private static final long serialVersionUID = 1L;
	private JLabel status;
	private GameLogic gameLogic;
	private int message_length = 0;
	private Point statusLocation = new Point(0, 0);
	private TimePanel timePanel[] = new TimePanel[2];
	private boolean gameOver = false;

	/**
	 * Constructor method
	 * 
	 * @param gameLogic
	 */
	public StatusBarPane(GameLogic gameLogic) {
		this.gameLogic = gameLogic;
		timePanel[0] = new TimePanel(gameLogic);
		timePanel[1] = new TimePanel(gameLogic);

		status = new JLabel(String.format("Your Move, %s",
				GameAttribute.getPlayerName(1)));
		status.setFont(new Font("serif", Font.BOLD, 25));
		setBackground(new Color(50, 50, 50));
		setBounds(0, GameAttribute.GAME_BOARD_HEIGHT
				+ GameAttribute.PLAYER_INFORMATION_PANE_HEIGHT,
				GameAttribute.MAX_DIMENTION.width,
				GameAttribute.STATUS_BAR_HEIGHT);
		status.setForeground(Color.white);
		add(status);
		timePanel[0].setVisible(false);
		timePanel[1].setVisible(false);
		add(timePanel[0]);
		add(timePanel[1]);
		gameLogic.addObserver(this);
	}

	public void repaint() {
		setBounds(0, GameAttribute.GAME_BOARD_HEIGHT
				+ GameAttribute.PLAYER_INFORMATION_PANE_HEIGHT,
				GameAttribute.MAX_DIMENTION.width,
				GameAttribute.STATUS_BAR_HEIGHT);
	}

	public void close() {
		repaint();
		timePanel[0].close();
		timePanel[1].close();
		gameLogic.deleteObserver(this);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		timePanel[0].setBounds(0, 0, (GameAttribute.MAX_DIMENTION.width) / 2,
				40);
		timePanel[1].setBounds((GameAttribute.MAX_DIMENTION.width) / 2, 0,
				(GameAttribute.MAX_DIMENTION.width) / 2, 40);
		if (gameOver) {
			statusLocation = new Point((GameAttribute.MAX_DIMENTION.width / 2)
					- (message_length / 2), 0);
		} else if (gameLogic.getTurn() == 1) {
			statusLocation = new Point(GameAttribute.MAX_DIMENTION.width
					- GameAttribute.MAX_DIMENTION.width / 4 - message_length
					/ 2, 0);
		} else {
			statusLocation = new Point(GameAttribute.MAX_DIMENTION.width / 4
					- message_length / 2, 0);
		}
		status.setLocation(statusLocation);
	}

	/**
	 * Method used to show clock time bar
	 */
	public void showClockTimerBar() {

		if (gameLogic.getTurn() == 1) {
			timePanel[0].setVisible(true);
			timePanel[1].setVisible(false);
		} else {
			timePanel[1].setVisible(true);
			timePanel[0].setVisible(false);
		}

	}

	/**
	 * Method that hide clock time bar
	 */
	public void hideClockTimeBar() {
		timePanel[0].setVisible(false);
		timePanel[1].setVisible(false);
	}

	@Override
	public void update(Observable arg0, Object arg1) {

		showClockTimerBar();
		if (gameLogic.hasErrorMessage()) {
			String msg = String.format("Invalid Move, %s.",
					GameAttribute.getPlayerName(gameLogic.getTurn()));
			status.setText(msg);
			status.setForeground(Color.RED);
			message_length = msg.length() * 10;
		} else if (gameLogic.isGameOverByTimeUp()) {
			hideClockTimeBar();
			status.setFont(new Font("Serif", Font.PLAIN, 20));
			String msg = String.format("%s, Times Up. %s won the game.",
					GameAttribute.getPlayerName(gameLogic.getTurn()),
					GameAttribute.getPlayerName(gameLogic
							.getOppositionPlayerNo(gameLogic.getTurn())));
			status.setText(msg);
			status.setForeground(Color.white);
			message_length = msg.length() * 10;
			gameOver = true;
		} else if (gameLogic.getWiner() != 0) {
			hideClockTimeBar();
			String msg = String.format(
					"Congratulation, %s. You are the WINNER",
					GameAttribute.getPlayerName(gameLogic.getWiner()));
			status.setText(msg);
			status.setFont(new Font("Serif", Font.BOLD, 30));
			gameOver = true;
			message_length = msg.length() * 10;
			status.setBackground(Color.BLUE);

		} else {
			status.setForeground(Color.WHITE);
			String msg = String.format("Your move, %s",
					GameAttribute.getPlayerName(gameLogic.getTurn()));
			status.setText(msg);
			message_length = msg.length() * 10;
		}
	}

	/**
	 * Inner class of StatusBarPane class
	 * 
	 * @author Ashiquzzaman
	 * @version 1.00
	 */
	class TimePanel extends JPanel implements Observer {

		private static final long serialVersionUID = 1L;
		JLabel stopWatchIcon;
		GameLogic gameLogic;
		JLabel remainingTimeShow;

		int barLength;

		/**
		 * Constructor method
		 * 
		 * @param gameLogic
		 */
		public TimePanel(GameLogic gameLogic) {

			this.gameLogic = gameLogic;
			stopWatchIcon = new JLabel(GameAttribute.STOP_WATCH_ICON);
			remainingTimeShow = new JLabel();
			barLength = 100;
			remainingTimeShow.setForeground(Color.RED);
			remainingTimeShow.setFont(new Font("Serif", Font.BOLD, 18));
			setOpaque(false);
			gameLogic.addObserver(this);
			add(remainingTimeShow);
			add(stopWatchIcon);
		}

		public void close() {
			repaint();
			gameLogic.deleteObserver(this);
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;
			if (gameLogic.getTurn() == 1) {
				stopWatchIcon.setBounds(0, 0,
						GameAttribute.STOPWATCH_ICON_WIDTH,
						GameAttribute.STOPWATCH_ICON_HEIGHT);
				remainingTimeShow.setLocation(
						GameAttribute.STOPWATCH_ICON_WIDTH + 10, 10);
				g2d.setPaint(new Color(75, 75, 75));
				g2d.fill(new Rectangle(40, 15,
						GameAttribute.MAX_DIMENTION.width / 2
								- GameAttribute.STOPWATCH_ICON_WIDTH, 15));

				g2d.setPaint(new GradientPaint(0, 0, Color.WHITE, 0, 5,
						Color.yellow, true));
				g2d.fill(new Rectangle(
						40,
						15,
						((GameAttribute.MAX_DIMENTION.width / 2 - GameAttribute.STOPWATCH_ICON_WIDTH) * barLength) / 100,
						15));
			} else {
				stopWatchIcon.setBounds(GameAttribute.MAX_DIMENTION.width / 2
						- GameAttribute.STOPWATCH_ICON_WIDTH - 6, 0,
						GameAttribute.STOPWATCH_ICON_HEIGHT,
						GameAttribute.STOPWATCH_ICON_HEIGHT);
				remainingTimeShow.setLocation(
						(GameAttribute.MAX_DIMENTION.width / 2)
								- GameAttribute.STOPWATCH_ICON_WIDTH - 6 - 70,
						10);
				g2d.setPaint(new Color(75, 75, 75));
				g2d.fill(new Rectangle(0, 15, GameAttribute.MAX_DIMENTION.width
						/ 2 - GameAttribute.STOPWATCH_ICON_WIDTH, 15));
				g2d.setPaint(new GradientPaint(0, 0, Color.WHITE, 0, 5,
						Color.yellow, true));
				g2d.fill(new Rectangle(
						GameAttribute.MAX_DIMENTION.width
								/ 2
								- GameAttribute.STOPWATCH_ICON_WIDTH
								- ((GameAttribute.MAX_DIMENTION.width / 2 - GameAttribute.STOPWATCH_ICON_WIDTH) * barLength)
								/ 100,
						15,
						((GameAttribute.MAX_DIMENTION.width / 2 - GameAttribute.STOPWATCH_ICON_WIDTH) * barLength) / 100,
						15));
			}
		}

		@Override
		public void update(Observable o, Object arg) {
			barLength = (int) ((gameLogic.getRemainingTurnTime() * 100.0) / (double) GameAttribute.TIME_EACH_TURN);

			if (gameOver)
				gameLogic.deleteObservers();
			remainingTimeShow.setText(String.format("%02d : %02d",
					gameLogic.getRemainingTimeSecond(),
					gameLogic.getRemainingTimeMill()));
		}
	}

}
