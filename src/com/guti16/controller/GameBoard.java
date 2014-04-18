package com.guti16.controller;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.application.model.ClickAdapter;
import com.guti16.model.DrawImageInPanel;
import com.guti16.model.GameAttribute;
import com.guti16.model.GameLogic;
import com.guti16.view.DrawBox;

/**
 * This is main user interface class to interact with player, all display
 * element manage in this and it is main controlling class
 * 
 * @author Md. Ashiquzzaman & Rashik Hasnat
 * @version 1.00
 */
public class GameBoard extends JPanel implements Observer, ActionListener {
	private static final long serialVersionUID = -8913662804412903898L;
	public DrawBox drawBox;
	private final GameLogic gameLogic;
	private final GutiContainer gutiContainer;
	private final JButton submit[] = { null, new JButton("Pass"),
			new JButton("Pass") };
	DrawImageInPanel backgroundImagePanel;

	/**
	 * Game court initialization
	 * 
	 * @param gameLogic
	 */
	public GameBoard(GameLogic gameLogic) {

		this.gameLogic = gameLogic;
		setLayout(null);
		setOpaque(false);
		setCursor(new Cursor(Cursor.HAND_CURSOR));
		setBounds(0, GameAttribute.MENU_BAR_HEIGHT
				+ GameAttribute.PLAYER_INFORMATION_PANE_HEIGHT,
				GameAttribute.MAX_DIMENTION.width,
				GameAttribute.GAME_BOARD_HEIGHT);

		drawBox = new DrawBox();
		gutiContainer = new GutiContainer(gameLogic);
		gameLogic.addObserver(this);

		for (int i = 1; i <= 2; i++) {
			submit[i].addActionListener(this);
			submit[i].setVisible(false);
			submit[i].setSize(80, 60);
		}

		drawBox.setSize(GameAttribute.GAME_BOARD_WIDTH,
				GameAttribute.GAME_BOARD_HEIGHT);
		drawBox.setLocation(0, 0);
		gutiContainer.setBounds(0, 0, GameAttribute.MAX_DIMENTION.width,
				GameAttribute.GAME_BOARD_HEIGHT);
		submit[1].setLocation(0, 0);
		submit[2].setLocation(GameAttribute.MAX_DIMENTION.width - 80, 0);

		backgroundImagePanel = new DrawImageInPanel(
				GameAttribute.BACKGROUND_IMAGE1, 0, 0,
				GameAttribute.GAME_BOARD_WIDTH, GameAttribute.GAME_BOARD_HEIGHT);
		backgroundImagePanel.setBounds(0, 0, GameAttribute.MAX_DIMENTION.width,
				GameAttribute.GAME_BOARD_HEIGHT);
		backgroundImagePanel.setOpaque(false);

		add(submit[1]);
		add(submit[2]);
		add(gutiContainer);
		add(drawBox);
		add(backgroundImagePanel);
	}

	public void close() {
		repaint();
		gutiContainer.close();
		submit[1].setVisible(false);
		submit[2].setVisible(false);
		gameLogic.deleteObserver(this);
	}

	public void resetGameBoard() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				gutiContainer.setGutiLocation();
				gutiContainer.repaint();
				drawBox.repaint();
				backgroundImagePanel.setBounds(0, 0,
						GameAttribute.MAX_DIMENTION.width,
						GameAttribute.GAME_BOARD_HEIGHT);
				backgroundImagePanel.repaint();
				submit[1].setLocation(0, 0);
				submit[2]
						.setLocation(GameAttribute.MAX_DIMENTION.width - 80, 0);
				setBounds(0, GameAttribute.MENU_BAR_HEIGHT
						+ GameAttribute.PLAYER_INFORMATION_PANE_HEIGHT,
						GameAttribute.MAX_DIMENTION.width,
						GameAttribute.GAME_BOARD_HEIGHT);
			}
		});

	}

	@Override
	public void update(Observable arg0, Object arg1) {

		if (GameAttribute.NETWORK_GAME) {
			if (gameLogic.hasInAttack()
					&& gameLogic.getTurn() == GameAttribute.NETWORK_GAME_PLAYER_ID)
				submit[GameAttribute.NETWORK_GAME_PLAYER_ID].setVisible(true);
			else
				submit[GameAttribute.NETWORK_GAME_PLAYER_ID].setVisible(false);
		} else {
			if (gameLogic.hasInAttack())
				submit[gameLogic.getTurn()].setVisible(true);
			else {
				submit[1].setVisible(false);
				submit[2].setVisible(false);
			}
		}
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		ClickAdapter.getInstance().changeTurnNativeCall();
		if (GameAttribute.NETWORK_GAME) {
			submit[GameAttribute.NETWORK_GAME_PLAYER_ID].setVisible(false);
		}
	}

}
