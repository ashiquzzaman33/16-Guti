package com.guti16.controller;

import java.awt.Color;
import java.awt.Container;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.lang.reflect.InvocationTargetException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import com.application.model.ClickAdapter;
import com.application.view.CongratulationBox;
import com.application.view.ReplayOrMainManue;
import com.application.view.Statistics;
import com.guti16.model.GameAttribute;
import com.guti16.model.GameLogic;
import com.guti16.view.AboutWindow;
import com.guti16.view.ChatWindow;
import com.guti16.view.GameRulesDialog;
import com.guti16.view.PlayerInfromationPane;
import com.guti16.view.StatusBarPane;

/**
 * This is main api Class of 16 Guti game
 * 
 * @author Md. Ashiquzzaman & Rashik Hasnat
 * @version 1.00
 */
public class Guti_16_Game extends JFrame {

	private static final long serialVersionUID = 1L;
	private GameBoard gameBoard;
	private PlayerInfromationPane playerInfromationPane;
	private StatusBarPane statusBarPane;
	private GameLogic gameLogic;
	private Container contentPane;
	private boolean fullscreen = true;
	public static int CONGRATULATION_BOX_SHOW_TIME = 3000;// miliseconds
	public static boolean GAME_END = false;
	private ChatWindow chatWindow;
	private boolean chatWinodwVisibility = true;

	public Guti_16_Game(String player1Name, String player2Name,
			Image player1Image, Image player2Image, int timeOut,
			ChatWindow chatWindow) {
		GameAttribute.setPlayerName(player1Name, 1);
		GameAttribute.setPlayerName(player2Name, 2);
		GameAttribute.setPlayerImage(player1Image, 1);
		GameAttribute.setPlayerImage(player2Image, 2);
		GameAttribute.setTimeOut(timeOut);
		setTitle("16 Guti Game");
		this.chatWindow = chatWindow;
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setResizable(false);
		setIconImage(new ImageIcon(getClass().getResource(
				"/resource/images/icon.png")).getImage());
		setLayout(null);
		new MenuBar().setMenuBar(this);
		GameAttribute.fullScreenMode(true);
		startGame();
		setFullScreen(false);
	}

	public void startGame() {
		GAME_END = false;
		gameLogic = new GameLogic(this);
		ClickAdapter.getInstance().setGameLogic(gameLogic);
		gameBoard = new GameBoard(gameLogic);
		statusBarPane = new StatusBarPane(gameLogic);
		playerInfromationPane = new PlayerInfromationPane(gameLogic);

		contentPane = new Container();
		contentPane.setLayout(null);
		if (GameAttribute.NETWORK_GAME)
			contentPane.add(chatWindow);
		contentPane.add(gameBoard);
		contentPane.add(playerInfromationPane);
		contentPane.add(statusBarPane);
		contentPane.validate();

		setContentPane(contentPane);
		gameLogic.startTimer();

	}

	public void replay() {
		contentPane.removeAll();
		contentPane.repaint();
		gameLogic.resetAllTimer();
		startGame();
		if (GameAttribute.FULL_SCREEN)
			setFullScreen(true);
		else
			setFullScreen(false);
		contentPane.validate();
	}

	public void close() {
		final Runnable closeThread = new Runnable() {
			@Override
			public void run() {
				gameBoard.close();
				statusBarPane.close();
				playerInfromationPane.close();
			}
		};
		Thread callerThread = new Thread() {
			@Override
			public void run() {
				try {
					SwingUtilities.invokeAndWait(closeThread);
				} catch (InvocationTargetException | InterruptedException e) {
					System.out.println(e.getMessage());
				}
			}
		};

		if (gameLogic.getWiner() != 0) {
			callerThread.start();
			CongratulationBox b = new CongratulationBox(Guti_16_Game.this,
					GameAttribute.getPlayerImage(gameLogic.getWiner()),
					GameAttribute.getPlayerName(gameLogic.getWiner()),
					Guti_16_Game.CONGRATULATION_BOX_SHOW_TIME);
			if (!GameAttribute.NETWORK_GAME)
				new ReplayOrMainManue(Guti_16_Game.this,
						Guti_16_Game.CONGRATULATION_BOX_SHOW_TIME - 100);
			else {
//				new ReplayOrMainManue(Guti_16_Game.this,
//						Guti_16_Game.CONGRATULATION_BOX_SHOW_TIME - 100);
				Runnable r = new Runnable() {

					@Override
					public void run() {
						try {
							Thread.sleep(Guti_16_Game.CONGRATULATION_BOX_SHOW_TIME - 100);
						} catch (InterruptedException e) {
							System.err.println("Sleep Interupted.");
						}
						System.exit(0);
					}
				};
				new Thread(r).start();
			}
			gameLogic.setWiner(0);
		} else {
			callerThread.start();
			if (!GameAttribute.NETWORK_GAME)
				new ReplayOrMainManue(Guti_16_Game.this, 0);
			else {
				//new ReplayOrMainManue(Guti_16_Game.this, 0);
				//System.exit(0);
			}
		}
	}

	@Override
	public void dispose() {
		gameLogic.close();
	}

	public void exit() {
		GAME_END = true;
		super.dispose();
	}

	public void setFullScreen(boolean flag) {
		if (flag) {
			GameAttribute.fullScreenMode(true);
			setVisible(flag);
			super.dispose();
			repaintAll();
			setContentPane(contentPane);
			setUndecorated(true);
			setSize(GameAttribute.FULL_SCREEN_SIZE);
			setLocationRelativeTo(null);
			repaint();
			validate();
			setVisible(true);
		} else {

			GameAttribute.fullScreenMode(false);
			setVisible(flag);
			super.dispose();
			repaintAll();
			setContentPane(contentPane);
			setUndecorated(false);
			setLocation(GameAttribute
					.setCenterScreen(GameAttribute.SMALL_SCREEN_SIZE));
			setSize(GameAttribute.SMALL_SCREEN_SIZE.width + 15,
					GameAttribute.SMALL_SCREEN_SIZE.height + 12);
			setVisible(true);
		}
	}

	public void repaintAll() {
		if (GameAttribute.NETWORK_GAME) {

			chatWindow.repaint();
			chatWindow.validate();
		}
		statusBarPane.repaint();
		statusBarPane.validate();
		gameBoard.resetGameBoard();
		playerInfromationPane.repaint();
		gameBoard.resetGameBoard();
		contentPane.repaint();
		contentPane.validate();
	}

	/**
	 * Set Menu Bar
	 * 
	 * @author Ashiquzzaman
	 */
	private class MenuBar {
		private JMenuBar menuBar;
		private JMenu menu;
		private JMenu view;
		private JMenu help;
		private JMenuItem exitMenu;
		private JMenuItem scoreboardMenu;
		private JMenuItem fullscreenView;
		private JMenuItem showHideView;
		private JMenuItem gameRulesHelp;
		private JMenuItem aboutHelp;

		public MenuBar() {
			menuBar = new JMenuBar();
			menuBar.setForeground(Color.BLACK);
			menuBar.setBorderPainted(false);

			menu = new JMenu("Menu");
			menu.setMnemonic(KeyEvent.VK_M);
			menuBar.add(menu);

			scoreboardMenu = new JMenuItem("Scoreboard               Altr+M+S");
			scoreboardMenu.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					new Statistics(Guti_16_Game.this);
				}
			});
			scoreboardMenu.setMnemonic(KeyEvent.VK_S);
			menu.add(scoreboardMenu);

			exitMenu = new JMenuItem(
					"Exit                             Altr+M+X");
			exitMenu.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					gameLogic.close();
				}
			});
			exitMenu.setMnemonic(KeyEvent.VK_X);
			menu.add(exitMenu);

			view = new JMenu("View");
			view.setMnemonic(KeyEvent.VK_V);
			menuBar.add(view);

			fullscreenView = new JMenuItem(
					"FullScreen                                   Altr+V+F");
			fullscreenView.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (fullscreen) {
						setFullScreen(true);
					} else {
						setFullScreen(false);
					}
					fullscreen = !fullscreen;
				}
			});
			fullscreenView.setMnemonic(KeyEvent.VK_F);
			view.add(fullscreenView);

			showHideView = new JMenuItem(
					"Show/Hide Chat Window        Altr+V+C");
			if (GameAttribute.NETWORK_GAME) {
				showHideView.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						chatWindow.setVisible(!chatWinodwVisibility);
						chatWinodwVisibility = !chatWinodwVisibility;
					}
				});
				showHideView.setMnemonic(KeyEvent.VK_C);
				view.add(showHideView);
			}

			help = new JMenu("Help");
			help.setMnemonic(KeyEvent.VK_H);
			menuBar.add(help);

			gameRulesHelp = new JMenuItem("Game Rules            Altr+H+R");
			gameRulesHelp.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					new GameRulesDialog(Guti_16_Game.this);
				}
			});
			gameRulesHelp.setMnemonic(KeyEvent.VK_R);
			help.add(gameRulesHelp);

			aboutHelp = new JMenuItem("About                      Altr+H+A");
			aboutHelp.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					new AboutWindow(Guti_16_Game.this);
				}
			});
			aboutHelp.setMnemonic(KeyEvent.VK_A);
			help.add(aboutHelp);
		}

		public void setMenuBar(JFrame frame) {
			frame.setJMenuBar(menuBar);
		}
	}
}
