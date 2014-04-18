package com.application.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.TitledBorder;

import com.application.api.StartWindows;
import com.application.model.GeneralAttribute;
import com.guti16.controller.Guti_16_Game;
import com.guti16.model.DrawImageInPanel;
import com.guti16.network.NetWorkAbstract;
import com.guti16.network.Server;
import com.guti16.view.ChatWindow;

public class NetworkWindow extends JDialog implements Observer {
	private static final long serialVersionUID = 1L;
	private JTextField chatInput;
	private JTextField nameServerPlayer;
	private JTextField nameClientPlayer;
	private DrawImageInPanel imageServerPlayer;
	private DrawImageInPanel imageClientPlayer;
	public static Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit()
			.getScreenSize();
	private JButton startGameButton;
	private Guti_16_Game guti_16_game;
	private StartWindows startWindow;
	private NetWorkAbstract netWorkAbstract;
	private JTextArea chatArea;

	public void start() {

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		JPanel connectedPlayerPane = new JPanel();
		connectedPlayerPane.setBorder(new TitledBorder(new BevelBorder(
				BevelBorder.LOWERED, null, null, null, null),
				"Connected Player", TitledBorder.CENTER, TitledBorder.TOP,
				null, null));
		connectedPlayerPane.setBounds(0, 0, 427, 180);
		getContentPane().add(connectedPlayerPane);
		connectedPlayerPane.setLayout(null);
		imageServerPlayer = new DrawImageInPanel(
				GeneralAttribute.getPlayerImage(1), 0, 0, 190, 120);
		imageServerPlayer.setBorder(new SoftBevelBorder(BevelBorder.LOWERED,
				null, null, null, null));
		imageServerPlayer.setBounds(12, 15, 190, 120);
		connectedPlayerPane.add(imageServerPlayer);

		imageClientPlayer = new DrawImageInPanel(
				GeneralAttribute.getPlayerImage(2), 0, 0, 190, 120);
		imageClientPlayer.setBorder(new SoftBevelBorder(BevelBorder.LOWERED,
				null, null, null, null));
		imageClientPlayer.setBounds(230, 15, 190, 120);
		connectedPlayerPane.add(imageClientPlayer);

		JSeparator separator = new JSeparator();
		separator.setBackground(Color.DARK_GRAY);
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(213, 22, 7, 147);
		connectedPlayerPane.add(separator);

		nameServerPlayer = new JTextField();
		nameServerPlayer.setEditable(false);
		nameServerPlayer.setFont(new Font("Tahoma", Font.PLAIN, 14));
		nameServerPlayer.setHorizontalAlignment(SwingConstants.CENTER);
		nameServerPlayer.setText(GeneralAttribute.getPlayerName(1));
		nameServerPlayer.setBounds(10, 140, 193, 29);
		connectedPlayerPane.add(nameServerPlayer);
		nameServerPlayer.setColumns(10);

		nameClientPlayer = new JTextField();
		nameClientPlayer.setEditable(false);
		nameClientPlayer.setFont(new Font("Tahoma", Font.PLAIN, 14));
		nameClientPlayer.setText(GeneralAttribute.getPlayerName(2));
		nameClientPlayer.setHorizontalAlignment(SwingConstants.CENTER);
		nameClientPlayer.setBounds(225, 140, 195, 29);
		connectedPlayerPane.add(nameClientPlayer);
		nameClientPlayer.setColumns(10);

		JPanel chatWindowPanel = new JPanel();
		chatWindowPanel.setBorder(new TitledBorder(new BevelBorder(
				BevelBorder.LOWERED, null, null, null, null), "Chat Window",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		chatWindowPanel.setBounds(0, 190, 440, 290);
		getContentPane().add(chatWindowPanel);
		chatWindowPanel.setLayout(new BorderLayout(0, 0));

		chatArea = new JTextArea();
		chatArea.setEditable(false);
		chatWindowPanel.add(new JScrollPane(chatArea), BorderLayout.CENTER);
		try {
			if (netWorkAbstract instanceof Server) {
				chatArea.append("IP Address / Domain Name: "
						+ InetAddress.getLocalHost().getHostName() + "\nPort: "
						+ netWorkAbstract.getPort()
						+ "\nWating for Opponent.......");
			} else {
				chatArea.append("Try to connect server.....");
			}
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}

		chatInput = new JTextField();
		chatWindowPanel.add(chatInput, BorderLayout.SOUTH);
		chatInput.setColumns(10);
		chatInput.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int playerId = netWorkAbstract instanceof Server ? 1 : 2;
				final String msg = GeneralAttribute.getPlayerName(playerId)
						+ ": " + chatInput.getText();
				chatInput.setText("");
				netWorkAbstract.sendData("Message: " + msg);
				showMessage(msg);
			}
		});
		if (netWorkAbstract instanceof Server) {

			startGameButton = new JButton("Start Game");
			startGameButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					startGame();
					netWorkAbstract.sendData("start");
				}
			});

			startGameButton.setBounds(95, 484, 149, 23);
			getContentPane().add(startGameButton);
			setEnable(false);
		}

		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		cancelButton.setBounds(268, 484, 159, 23);
		getContentPane().add(cancelButton);
		setVisible(true);
	}

	public NetworkWindow(Window owner, StartWindows startWindow,
			NetWorkAbstract netWorkAbstract) throws ClassNotFoundException,
			InstantiationException, IllegalAccessException,
			UnsupportedLookAndFeelException {
		super(owner, ModalityType.APPLICATION_MODAL);
		setTitle("Start Network Game");
		this.startWindow = startWindow;
		this.netWorkAbstract = netWorkAbstract;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(446, 557);
		setLocationRelativeTo(getOwner());
		getContentPane().setLayout(null);
		netWorkAbstract.addObserver(this);
	}

	public void setEnable(boolean visibility) {

		if (netWorkAbstract instanceof Server) {
			startGameButton.setEnabled(visibility);
			nameClientPlayer.setVisible(visibility);
			imageClientPlayer.setVisible(visibility);
			chatInput.setEditable(visibility);
		}

	}

	private void startGame() {
		ChatWindow chatWindow = new ChatWindow(netWorkAbstract);
		guti_16_game = new Guti_16_Game(GeneralAttribute.getPlayerName(1),
				GeneralAttribute.getPlayerName(2),
				GeneralAttribute.getPlayerImage(1),
				GeneralAttribute.getPlayerImage(2),
				GeneralAttribute.getTimeOut(), chatWindow);
		guti_16_game.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				setVisible(false);
				startWindow.setVisible(false);
			}

			@Override
			public void windowClosed(WindowEvent e) {
				if (Guti_16_Game.GAME_END) {
					startWindow.setEnabled(true);
					startWindow.setVisible(true);
				}
			}
		});
	}

	@Override
	public void update(Observable o, Object arg) {

		setEnable(netWorkAbstract.getConnected());
		if (netWorkAbstract.getConnected()
				&& netWorkAbstract.hasChangedMessage()) {
			String message = netWorkAbstract.getMessage();
			if (message.endsWith(".name2")) {
				nameClientPlayer.setText(message.substring(0,
						message.length() - 5));
			} else
				showMessage(message);
			
		}
		if (netWorkAbstract.getConnected() && netWorkAbstract.getGameStarted()) {
			startGame();
		}
	}

	public void showMessage(final String msg) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				chatArea.append("\n" + msg);
			}
		});
	}

}
