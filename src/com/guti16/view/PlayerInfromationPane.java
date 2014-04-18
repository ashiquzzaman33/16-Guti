package com.guti16.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.guti16.model.GameAttribute;
import com.guti16.model.GameLogic;

/**
 * This class draws player information panel where shows player name, player
 * avater, remaining guti and remaining guti with bar graph
 * 
 * @author Md. Ashiquzzaman & Rashik Hasnat
 * @version 1.00
 */
public class PlayerInfromationPane extends JPanel implements Observer {
	private static final long serialVersionUID = 1L;

	public static final Image img = null;

	private int remGuti[] = new int[3];
	GameLogic gameLogic;
	PlayerInformation playerPane[] = new PlayerInformation[3];

	/**
	 * Constructor method
	 * 
	 * @param gameLogic
	 */
	public PlayerInfromationPane(GameLogic gameLogic) {

		this.gameLogic = gameLogic;
		setLayout(null);
		for (int i = 1; i <= 2; i++) {
			remGuti[i] = gameLogic.getNumberOfGuti(i);
			playerPane[i] = new PlayerInformation(i, remGuti[i]);
			playerPane[i].setPlayerName(GameAttribute.getPlayerName(i));
			playerPane[i].setRemainingGuti(gameLogic.getNumberOfGuti(i));
		}

		setBounds(0, 0, GameAttribute.MAX_DIMENTION.width,
				GameAttribute.PLAYER_INFORMATION_PANE_HEIGHT);

		playerPane[1].setBounds(0, 0, GameAttribute.MAX_DIMENTION.width / 2,
				GameAttribute.PLAYER_INFORMATION_PANE_HEIGHT);
		playerPane[2].setBounds(GameAttribute.MAX_DIMENTION.width / 2, 0,
				GameAttribute.MAX_DIMENTION.width,
				GameAttribute.PLAYER_INFORMATION_PANE_HEIGHT);
		add(playerPane[1]);
		add(playerPane[2]);
		gameLogic.addObserver(this);

	}

	public void close() {
		playerPane[1].setRemainingGuti(gameLogic.getNumberOfGuti(1));
		playerPane[2].setRemainingGuti(gameLogic.getNumberOfGuti(2));
		repaint();
		gameLogic.deleteObserver(this);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		for (int i = 1; i <= 2; i++) {
			if (gameLogic.hasChangeNumberOfGuti(i)) {
				playerPane[i].setRemainingGuti(gameLogic.getNumberOfGuti(i));
				gameLogic.clearChangedNumberOfGuti(i);
			}
		}
	}

	protected void paintComponent(Graphics g) {
		setBounds(0, 0, GameAttribute.MAX_DIMENTION.width,
				GameAttribute.PLAYER_INFORMATION_PANE_HEIGHT);
		playerPane[1].setBounds(0, 0, GameAttribute.MAX_DIMENTION.width / 2,
				GameAttribute.PLAYER_INFORMATION_PANE_HEIGHT);
		playerPane[2].setBounds(GameAttribute.MAX_DIMENTION.width / 2, 0,
				GameAttribute.MAX_DIMENTION.width,
				GameAttribute.PLAYER_INFORMATION_PANE_HEIGHT);

	}

	/**
	 * Inner class of PlayerInformationPane class
	 * 
	 * @author Ashiquzzaman
	 * @version 1.00
	 */
	class PlayerInformation extends JPanel {
		private static final long serialVersionUID = 1L;
		private JLabel playerName, remainingGuti;
		private int numberOfGuti;
		private int playerId;

		/**
		 * Constructor method
		 * 
		 * @param ico
		 *            Guti Icon
		 * @param pName
		 *            Player name
		 * @param numberOfGuti
		 *            Number of Guti
		 */
		public PlayerInformation(int pId, int numberOfGuti) {
			setLayout(null);
			playerId = pId;
			playerName = new JLabel(GameAttribute.getPlayerName(playerId));
			remainingGuti = new JLabel(String.format("Remaining guti: %d",
					numberOfGuti));
			add(playerName);
			add(remainingGuti);
			remainingGuti.setFont(new Font("serif", Font.BOLD, 15));
			remainingGuti.setForeground(new Color(120, 120, 200));
			playerName.setFont(new Font("serif", Font.BOLD, 25));
			playerName.setForeground(new Color(220, 220, 220));
			setBackground(new Color(50, 50, 50));

		}

		/**
		 * Set Remaining guti used to show remaining guti and remaining guti bar
		 * 
		 * @param numberOfGuti
		 *            Number of Guti remaining guti
		 */
		public void setRemainingGuti(int numberOfGuti) {
			this.numberOfGuti = numberOfGuti;
			remainingGuti.setText(String.format("Remaining guti: %d",
					this.numberOfGuti));
			repaint();
		}

		/**
		 * set player name to show changed player name
		 * 
		 * @param pName
		 *            player name
		 */
		public void setPlayerName(String pName) {
			playerName.setText(pName);
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;
			g.drawImage(GameAttribute.getPlayerImage(playerId), 0, 0,
					GameAttribute.PLAYER_IMAGE_WIDTH,
					GameAttribute.PLAYER_IMAGE_HEIGHT, this);
			playerName.setBounds(120, 0, 400, 35);
			remainingGuti.setBounds(120, 30, 400, 20);
			Color barColor;
			if (numberOfGuti > 10)
				barColor = new Color(0, 150, 0);
			else if (numberOfGuti > 5)
				barColor = new Color(200, 200, 0);
			else
				barColor = new Color(255, 0, 0);

			g2d.setPaint(new GradientPaint(0, 0, new Color(200, 200, 200), 0,
					6, new Color(150, 150, 150), true));
			g2d.fill(new Rectangle(
					120,
					55,
					(GameAttribute.MAX_DIMENTION.width - 2 * GameAttribute.PLAYER_IMAGE_WIDTH) / 2,
					30));

			g2d.setPaint(new GradientPaint(0, 0, Color.WHITE, 0, 6, barColor,
					true));
			g2d.fill(new Rectangle(
					120,
					55,
					((GameAttribute.MAX_DIMENTION.width - 2 * GameAttribute.PLAYER_IMAGE_WIDTH) / 32)
							* numberOfGuti, 30));
		}
	}
}
