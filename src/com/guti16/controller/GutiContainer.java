package com.guti16.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.application.model.ClickAdapter;
import com.guti16.model.GameAttribute;
import com.guti16.model.GameLogic;
import com.guti16.model.Node;

/**
 * This class works as a container of guti and all characteristics of guti like
 * visibility, view, , icon and attribute are updated via observer.
 * 
 * @author Md. Ashiquzzaman & Rashik Hasnat
 * @version 1.00
 */

public class GutiContainer extends JPanel implements ActionListener, Observer {
	private static final long serialVersionUID = -8246814330077028941L;
	JButton guti[][] = new JButton[9][5];
	int height = GameAttribute.GUTI_HEIGHT;
	int width = GameAttribute.GUTI_WIDTH;
	private Icon[] gutiIcon = new Icon[5];
	private GameLogic gameLogic;
	private ClickAdapter clickAdapter;

	/**
	 * Initialize guti container
	 * 
	 * @param gameLogic
	 *            this GameLogic type object is used to interact with same
	 *            object.
	 */
	GutiContainer(GameLogic gameLogic) {

		setLayout(null);
		this.gameLogic = gameLogic;
		clickAdapter = ClickAdapter.getInstance(); 
		gameLogic.addObserver(this);
		setOpaque(false);
		gutiIcon = GameAttribute.GUTI_ICON;
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 5; j++) {
				guti[i][j] = new JButton();
				guti[i][j].setSize(width, height);
				guti[i][j].setActionCommand(String.format("%d %d", i, j));
				guti[i][j].setContentAreaFilled(false);
				guti[i][j].setBorderPainted(false);
				guti[i][j].setIcon(gutiIcon[0]);
				guti[i][j].createImage(100, 200);
			}
		}
		setGutiLocation(); // Set guti Location
		initGuti(); // Initialize guti
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 5; j++) {
				if (guti[i][j].getLocation().x != 0
						&& guti[i][j].getLocation().y != 0) {
					add(guti[i][j]);
					guti[i][j].addActionListener(this);
				}
			}
		}
	}

	public void close() {
		repaint();
		gameLogic.deleteObserver(this);
	}

	/**
	 * This method is used to initialize player Guti.
	 */
	public void initGuti() {

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 5; j++) {
				int value = gameLogic.getGridValue(gameLogic.getNode(i, j));
				if (value != -1) {
					changeIcon(i, j, value);
				}
			}
		}
	}
	@Override
	public void setEnabled(boolean enabled) {
		for(int i=0; i<9; i++){
			for(int j=0; j<5; j++)
				guti[i][j].setEnabled(enabled);
		}
	}
	/**
	 * This method set guti position
	 */
	public void setGutiLocation() {
		for (int i = 0; i <= 2; i++) {
			guti[0][i * 2]
					.setLocation(
							GameAttribute.CONST_RATIO - width / 2,
							GameAttribute.CONST_RATIO
									+ i
									* (GameAttribute.GAME_BOARD_HEIGHT / 2 - GameAttribute.CONST_RATIO)
									- height / 2); // left box outer 3 Node
			guti[1][i + 1]
					.setLocation(
							GameAttribute.GAME_BOARD_WIDTH / 8
									+ GameAttribute.CONST_RATIO / 3 - width / 3,
							GameAttribute.GAME_BOARD_HEIGHT
									/ 4
									+ GameAttribute.CONST_RATIO
									/ 3
									+ i
									* (GameAttribute.GAME_BOARD_HEIGHT / 4 - GameAttribute.CONST_RATIO / 3)
									- height / 2); // left box inner 3 Node
			guti[8][i * 2]
					.setLocation(
							GameAttribute.GAME_BOARD_WIDTH
									- GameAttribute.CONST_RATIO - width / 2,
							GameAttribute.CONST_RATIO
									+ i
									* (GameAttribute.GAME_BOARD_HEIGHT / 2 - GameAttribute.CONST_RATIO)
									- height / 2);// right box outer 3 Node
			guti[7][i + 1]
					.setLocation(
							GameAttribute.GAME_BOARD_WIDTH
									- GameAttribute.GAME_BOARD_WIDTH / 8
									- GameAttribute.CONST_RATIO / 3 - width / 2,
							GameAttribute.CONST_RATIO
									/ 3
									+ GameAttribute.GAME_BOARD_HEIGHT
									/ 4
									+ i
									* (GameAttribute.GAME_BOARD_HEIGHT / 4 - GameAttribute.CONST_RATIO / 3)
									- height / 2);// right box inner 3 Node

		}

		for (int i = 2; i <= 6; i++) {
			for (int j = 0; j <= 4; j++) {
				guti[i][j]
						.setLocation(
								i * (GameAttribute.GAME_BOARD_WIDTH / 8)
										- width / 2,
								GameAttribute.VERTICAL_GAP
										+ j
										* (GameAttribute.GAME_BOARD_HEIGHT / 4 - GameAttribute.VERTICAL_GAP / 2)
										- height / 2);
			}
		}
	}

	/**
	 * This method change specific icon in specific node
	 * 
	 * @param x
	 *            X the first index
	 * @param y
	 *            Y the second index to specify a specific node
	 * @param iconNo
	 *            icon id
	 */
	public void changeIcon(int x, int y, int iconNo) {
		guti[x][y].setIcon(gutiIcon[iconNo]);
		guti[x][y].setDisabledIcon(gutiIcon[iconNo]);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Scanner input = new Scanner(e.getActionCommand());
		int x = input.nextInt();
		int y = input.nextInt();
		clickAdapter.clickedNativeCall(x, y);
		input.close();
	}

	@Override
	public void update(Observable o, Object arg) {
		Node[] node = gameLogic.getChangedGuti();
		if(GameAttribute.NETWORK_GAME){
			if(gameLogic.getTurn()!=GameAttribute.NETWORK_GAME_PLAYER_ID){
				setEnabled(false);
			}
			else{
				setEnabled(true);
			}
		}
		if (node != null) {
			for (Node N : node) {
				changeIcon(N.getRelativeX(), N.getRelativeY(),
						gameLogic.getGridValue(N));
			}
		}
	}
}
