package com.application.api;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.application.model.CreateNetworkGame;
import com.application.model.GeneralAttribute;
import com.application.model.JoinNetworkGame;
import com.application.model.StartLocalGame;
import com.guti16.model.DrawImageInPanel;
import com.guti16.model.GameAttribute;
import com.guti16.view.AboutWindow;
import com.guti16.view.GameRulesDialog;

import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.MatteBorder;

public class StartWindows extends JFrame {
	private static final long serialVersionUID = 2756466278476673858L;
	private UIManager.LookAndFeelInfo looks[] = UIManager
			.getInstalledLookAndFeels();
	private GeneralAttribute generalAttribute = new GeneralAttribute();

	public StartWindows() {
		setTitle("Board Game");
		setComponentOrientation(
				ComponentOrientation.LEFT_TO_RIGHT);
		setCursor(
				Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		setBackground(Color.DARK_GRAY);
		getContentPane().setLayout(null);
		setIconImage(new ImageIcon(getClass().getResource("/resource/images/icon.png")).getImage());
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBorder(null);
		menuBar.setForeground(Color.DARK_GRAY);
		menuBar.setBackground(Color.DARK_GRAY);
		menuBar.setBounds(0, 0, 444, 26);
		getContentPane().add(menuBar);
		JMenu mnBoardGame = new JMenu("Board Game");
		mnBoardGame.setMnemonic(KeyEvent.VK_ALT);
		mnBoardGame.setForeground(new Color(0, 0, 128));
		mnBoardGame.setBackground(Color.WHITE);
		menuBar.add(mnBoardGame);

		JMenuItem quitMenu = new JMenuItem("Quit            Alt+Q");
		quitMenu.setMnemonic(KeyEvent.VK_Q);
		quitMenu.setIcon(new ImageIcon(StartWindows.class
				.getResource("/resource/images/quit.png")));
		quitMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);

			}
		});
		mnBoardGame.add(quitMenu);

		JMenu mnSetting = new JMenu("Help");
		mnSetting.setForeground(new Color(0, 0, 128));
		mnSetting.setBackground(Color.WHITE);
		menuBar.add(mnSetting);
		
		JMenuItem mntmGameRules = new JMenuItem("Game Rules");
		mntmGameRules.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new GameRulesDialog(StartWindows.this);
			}
		});
		mnSetting.add(mntmGameRules);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new AboutWindow(StartWindows.this);
			}
		});
		mnSetting.add(mntmAbout);
		
		JPanel panel = new DrawImageInPanel(new ImageIcon(getClass().
				getResource("/resource/images/background.jpg")).
				getImage(), 444, 371);
		panel.setBounds(0, 0, 444, 371);
		getContentPane().add(panel);
		panel.setLayout(null);
		
				JButton btnStartLocalGame = new JButton("Start Local Game");
				btnStartLocalGame.setBounds(100, 54, 250, 75);
				panel.add(btnStartLocalGame);
				btnStartLocalGame.setIcon(new ImageIcon(StartWindows.class
						.getResource("/resource/images/startLocalGame.png")));
				btnStartLocalGame.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						createLocalGame();
					}
				});
				btnStartLocalGame.setForeground(new Color(255, 255, 255));
				btnStartLocalGame.setFont(new Font("Serif", Font.BOLD, 18));
				btnStartLocalGame.setBackground(new Color(0, 0, 0));
				
						JButton btnCreateNetworkGame = new JButton("Create Network Game");
						btnCreateNetworkGame.setBounds(100, 162, 250, 75);
						panel.add(btnCreateNetworkGame);
						btnCreateNetworkGame.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								createNetworkGame();
							}
						});
						btnCreateNetworkGame.setIcon(new ImageIcon(StartWindows.class
								.getResource("/resource/images/createNGame.png")));
						btnCreateNetworkGame.setFont(new Font("Serif", Font.BOLD, 18));
						btnCreateNetworkGame.setForeground(new Color(255, 255, 255));
						btnCreateNetworkGame.setBackground(new Color(0, 0, 0));
						
								JButton btnJoinNetworkGame = new JButton("Join Network Game");
								btnJoinNetworkGame.setBounds(100, 266, 250, 75);
								panel.add(btnJoinNetworkGame);
								btnJoinNetworkGame.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										joinNetworkGame();
									}
								});
								btnJoinNetworkGame.setIcon(new ImageIcon(StartWindows.class
										.getResource("/resource/images/joinGameLogo.png")));
								btnJoinNetworkGame.setFont(new Font("Serif", Font.BOLD, 18));
								btnJoinNetworkGame.setBackground(new Color(0, 0, 0));
								btnJoinNetworkGame.setForeground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setSize(450, 400);
		setLookAndFeel(this);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public static void main(String[] args) {
		new StartWindows();
	}

	public void createLocalGame() {
		GameAttribute.NETWORK_GAME = false;
		new StartLocalGame(this, generalAttribute);
	}

	public void createNetworkGame() {
		GameAttribute.NETWORK_GAME = true;
		GameAttribute.NETWORK_GAME_PLAYER_ID = 1;
		new CreateNetworkGame(StartWindows.this, generalAttribute, this);
	}

	public void joinNetworkGame() {
		GameAttribute.NETWORK_GAME = true;
		GameAttribute.NETWORK_GAME_PLAYER_ID = 2;
		new JoinNetworkGame(StartWindows.this, generalAttribute, this);
	}

	public void setLookAndFeel(JFrame f) {
		int value = -1;
		for (int i = 0; i < looks.length; i++) {
			if (looks[i].getName().equalsIgnoreCase("Nimbus")) {
				value = i;
				break;
			}
		}
		if (value != -1) {
			try {
				UIManager.setLookAndFeel(looks[value].getClassName());
				SwingUtilities.updateComponentTreeUI(f);
			} catch (ClassNotFoundException | InstantiationException
					| IllegalAccessException | UnsupportedLookAndFeelException e) {
				e.printStackTrace();
			}
		}
	}
}
