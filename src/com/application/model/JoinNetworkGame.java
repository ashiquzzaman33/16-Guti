package com.application.model;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.EOFException;
import java.io.IOException;
import java.net.SocketException;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.TitledBorder;

import com.application.api.StartWindows;
import com.application.view.NetworkWindow;
import com.application.view.PlayerAvaterManager;
import com.guti16.controller.Guti_16_Game;
import com.guti16.model.DrawImageInPanel;
import com.guti16.model.GameAttribute;
import com.guti16.network.Client;

public class JoinNetworkGame extends JDialog implements Observer {

	private static final long serialVersionUID = 16L;
	public static Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit()
			.getScreenSize();
	private StartWindows startWindow;
	private JTextField playerName;
	private String screenResulation[] = { "Default", "1366 x 768",
			"1152 x 720", "1026 x 768", "800 x 600", "720 x 480" };
	private Point screenSize[] = {
			new Point((SCREEN_SIZE.width * 9) / 10,
					(SCREEN_SIZE.height * 9) / 10), new Point(1366, 768),
			new Point(1152, 720), new Point(1026, 768), new Point(800, 600),
			new Point(720, 480) };
	private JComboBox screenResulationComboBox;
	private GeneralAttribute generalAttribute;
	private DrawImageInPanel playerAvater;
	private Guti_16_Game guti_16_game;
	private Point selectScreenSize = screenSize[0];
	private Client client;
	private JTextField ipAddress;
	private JSpinner portNumber;
	private NetworkWindow networkWindow;

	public JoinNetworkGame(Window owner, GeneralAttribute ga, StartWindows sw) {

		super(owner, ModalityType.APPLICATION_MODAL);
		this.generalAttribute = ga;
		this.startWindow = sw;
		generalAttribute.addObserver(this);
		getContentPane().setBackground(Color.LIGHT_GRAY);
		getContentPane().setCursor(
				Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		setBackground(new Color(0, 51, 51));
		getContentPane().setLayout(null);

		JPanel startLocalGamePanel = new JPanel();
		startLocalGamePanel.setBackground(new Color(153, 153, 153));
		startLocalGamePanel.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "Local Game Settings",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0,
						153)));
		startLocalGamePanel.setBounds(21, 88, 268, 197);
		getContentPane().add(startLocalGamePanel);
		startLocalGamePanel.setLayout(null);

		JLabel lblResulaion = new JLabel("Screen Size: ");
		lblResulaion.setForeground(new Color(0, 0, 128));
		lblResulaion.setBackground(new Color(0, 0, 128));
		lblResulaion.setBounds(10, 23, 161, 14);
		startLocalGamePanel.add(lblResulaion);
		screenResulationComboBox = new JComboBox(screenResulation);
		screenResulationComboBox.setSelectedIndex(0);
		screenResulationComboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				selectScreenSize = screenSize[screenResulationComboBox
						.getSelectedIndex()];
			}
		});
		screenResulationComboBox.setBounds(80, 20, 151, 20);
		startLocalGamePanel.add(screenResulationComboBox);

		playerAvater = new DrawImageInPanel(GeneralAttribute.getPlayerImage(2),
				0, 0, 180, 100);
		playerAvater.setBounds(45, 45, 180, 101);
		startLocalGamePanel.add(playerAvater);
		playerAvater.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new PlayerAvaterManager(JoinNetworkGame.this, generalAttribute,
						getClass().getResource("/resource/images/avaters"), 2);

			}
		});
		playerAvater.setBorder(new SoftBevelBorder(BevelBorder.LOWERED,
				Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY,
				Color.DARK_GRAY));
		playerAvater.setLayout(null);

		playerName = new JTextField();
		playerName.setBounds(35, 161, 207, 25);
		startLocalGamePanel.add(playerName);
		playerName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GeneralAttribute.setPlayerName(playerName.getText(), 2);
			}
		});
		playerName.setHorizontalAlignment(SwingConstants.CENTER);
		playerName.setForeground(new Color(0, 128, 0));
		playerName.setFont(new Font("Serif", Font.BOLD, 12));
		playerName.setText("Rashik");
		playerName.setColumns(10);
		final JButton connectButton = new JButton("Connect");
		connectButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		connectButton.setOpaque(true);
		connectButton.setBounds(21, 290, 131, 30);
		getContentPane().add(connectButton);
		connectButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				final ExecutorService pool = Executors.newCachedThreadPool();
				GeneralAttribute.setPlayerName(playerName.getText(), 2);
				GeneralAttribute.setTimeOut(20);
				GameAttribute.SMALL_SCREEN_SIZE = new Dimension(
						selectScreenSize.x, selectScreenSize.y);
				GameAttribute.fullScreenMode(false);

				client = new Client(ipAddress.getText(), (int) portNumber
						.getValue());
				ClickAdapter.createFreshInstance(client);

				try {
					networkWindow = new NetworkWindow(getOwner(), startWindow,
							client);
				} catch (ClassNotFoundException | InstantiationException
						| IllegalAccessException
						| UnsupportedLookAndFeelException e1) {
					e1.printStackTrace();
				}

				Runnable net = new Runnable() {

					@Override
					public void run() {
						try {
							client.runNetwork();
						} catch (EOFException e) {
							JOptionPane.showMessageDialog(null, e.getMessage());
						} catch (SocketException e) {
							JOptionPane.showMessageDialog(null,
									"Could not connect to the server",
									"Error Message", JOptionPane.ERROR_MESSAGE);
						} catch (IOException e) {
							JOptionPane.showMessageDialog(null, e.getMessage());
						} finally {
							client.close();
						}
					}
				};
				Runnable mainGame = new Runnable() {

					@Override
					public void run() {
						try {
							Thread.sleep(200);
							if (client.getConnected())
								networkWindow.start();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}

					}
				};
				pool.execute(mainGame);
				try {
					pool.execute(net);
				} catch (NullPointerException e1) {
					JOptionPane.showMessageDialog(networkWindow,
							e1.getMessage());
				}

			}

		});
		JButton CancelButton = new JButton("Cancel\r\n");
		CancelButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		CancelButton.setBounds(162, 290, 127, 30);
		CancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		getContentPane().add(CancelButton);

		JPanel connectPanel = new JPanel();
		connectPanel.setLayout(null);
		connectPanel.setBorder(new TitledBorder(new BevelBorder(
				BevelBorder.LOWERED, null, null, null, null),
				"Connect To Network Game", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		connectPanel.setBounds(0, 0, 308, 77);
		getContentPane().add(connectPanel);

		JLabel label = new JLabel("IP / Domain Name: ");
		label.setBounds(10, 20, 126, 14);
		connectPanel.add(label);

		ipAddress = new JTextField();
		ipAddress.setText("Ashiq-pc");
		ipAddress.setColumns(10);
		ipAddress.setBounds(117, 16, 188, 25);
		connectPanel.add(ipAddress);

		JLabel label_1 = new JLabel("Port: ");
		label_1.setBounds(10, 49, 45, 17);
		connectPanel.add(label_1);

		portNumber = new JSpinner();
		portNumber.setModel(new SpinnerNumberModel(16720, 1024, 60000, 1));
		portNumber.setBounds(117, 45, 70, 25);
		connectPanel.add(portNumber);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Join Network Game");
		setSize(318, 354);
		setLocationRelativeTo(startWindow);
		setIconImage(new ImageIcon(getClass().getResource(
				"/resource/images/startLocalGame.png")).getImage());
		setResizable(false);
		setVisible(true);
	}

	@Override
	public void update(Observable o, Object arg) {
		if (generalAttribute.hasChangePlayerImage(2))
			playerAvater.paintImage(2);
	}
}
