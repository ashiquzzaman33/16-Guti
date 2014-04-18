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
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
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
import com.guti16.model.DrawImageInPanel;
import com.guti16.model.GameAttribute;
import com.guti16.network.Server;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowEvent;

public class CreateNetworkGame extends JDialog implements Observer {

	private static final long serialVersionUID = 16L;
	public static Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit()
			.getScreenSize();
	private StartWindows startWindow;
	private JTextField playerName;
	private JSpinner timeOutspinner = new JSpinner();
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
	private Point selectScreenSize = screenSize[0];
	private Server server;
	private NetworkWindow networkWindow;
	ExecutorService pool;

	public CreateNetworkGame(Window owner, GeneralAttribute ga, StartWindows sw) {
		super(owner, ModalityType.APPLICATION_MODAL);
		this.generalAttribute = ga;
		this.startWindow = sw;
		generalAttribute.addObserver(this);
		getContentPane().setBackground(new Color(102, 102, 102));
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
		startLocalGamePanel.setBounds(10, 11, 344, 267);
		getContentPane().add(startLocalGamePanel);
		startLocalGamePanel.setLayout(null);

		JLabel timeOutLabel = new JLabel("Time Out For Player Action: ");
		timeOutLabel.setForeground(new Color(0, 0, 102));
		timeOutLabel.setBackground(new Color(0, 0, 102));
		timeOutLabel.setBounds(25, 25, 161, 14);
		startLocalGamePanel.add(timeOutLabel);
		timeOutspinner.setToolTipText("Seconds");

		timeOutspinner.setOpaque(false);
		timeOutspinner.setName("");
		timeOutspinner.setModel(new SpinnerNumberModel(20, 2, 500, 1));
		timeOutspinner.setBounds(263, 20, 61, 24);
		timeOutspinner.addMouseWheelListener(new MouseWheelListener() {

			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				if (e.getWheelRotation() == -1
						&& (int) timeOutspinner.getValue() <= 500)
					timeOutspinner.setValue((int) timeOutspinner.getValue() + 1);
				else if (e.getWheelRotation() == 1
						&& (int) timeOutspinner.getValue() > 2)
					timeOutspinner.setValue((int) timeOutspinner.getValue() - 1);

			}
		});
		startLocalGamePanel.add(timeOutspinner);

		JPanel playerNamePanel = new JPanel();
		playerNamePanel.setForeground(new Color(0, 0, 128));
		playerNamePanel.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "Your Information",
				TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0,
						102)));
		playerNamePanel.setBounds(35, 81, 272, 167);
		startLocalGamePanel.add(playerNamePanel);
		playerNamePanel.setLayout(null);

		playerAvater = new DrawImageInPanel(GeneralAttribute.getPlayerImage(1),
				0, 0, 180, 100);
		playerAvater.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new PlayerAvaterManager(CreateNetworkGame.this,
						generalAttribute, getClass().getResource(
								"/resource/images/avaters"), 1);

			}
		});
		playerAvater.setBorder(new SoftBevelBorder(BevelBorder.LOWERED,
				new Color(255, 0, 0), new Color(255, 0, 0),
				new Color(0, 0, 128), new Color(0, 0, 128)));
		playerAvater.setBounds(45, 20, 180, 101);
		playerNamePanel.add(playerAvater);
		playerAvater.setLayout(null);

		playerName = new JTextField();
		playerName.setBounds(35, 130, 209, 25);
		playerNamePanel.add(playerName);
		playerName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GeneralAttribute.setPlayerName(playerName.getText(), 1);
			}
		});
		playerName.setHorizontalAlignment(SwingConstants.CENTER);
		playerName.setForeground(new Color(0, 128, 0));
		playerName.setFont(new Font("Tahoma", Font.BOLD, 14));
		playerName.setText("Ashiq");
		playerName.setColumns(10);

		JLabel lblResulaion = new JLabel("Screen Size: ");
		lblResulaion.setForeground(new Color(0, 0, 128));
		lblResulaion.setBackground(new Color(0, 0, 128));
		lblResulaion.setBounds(25, 50, 161, 14);
		startLocalGamePanel.add(lblResulaion);
		screenResulationComboBox = new JComboBox(screenResulation);
		screenResulationComboBox.setSelectedIndex(0);
		screenResulationComboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				selectScreenSize = screenSize[screenResulationComboBox
						.getSelectedIndex()];
			}
		});
		screenResulationComboBox.setBounds(188, 50, 136, 20);
		startLocalGamePanel.add(screenResulationComboBox);
		JButton okButton = new JButton("Create Game");
		okButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		okButton.setOpaque(true);
		okButton.setBounds(80, 289, 106, 23);
		getContentPane().add(okButton);
		okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GeneralAttribute.setPlayerName(playerName.getText(), 1);
				GeneralAttribute.setTimeOut((int) timeOutspinner.getValue());
				GameAttribute.SMALL_SCREEN_SIZE = new Dimension(
						selectScreenSize.x, selectScreenSize.y);
				GameAttribute.fullScreenMode(false);

				server = new Server(16720);
				ClickAdapter.createFreshInstance(server);
				try {
					networkWindow = new NetworkWindow(getOwner(), startWindow,
							server);
				} catch (ClassNotFoundException | InstantiationException
						| IllegalAccessException
						| UnsupportedLookAndFeelException e2) {
					e2.printStackTrace();
				}
				Runnable net = new Runnable() {

					@Override
					public void run() {
						try {
							server.runNetwork();
						} catch (EOFException e) {
							JOptionPane.showMessageDialog(null, e.getMessage());
						} catch (SocketException e) {
							JOptionPane.showMessageDialog(null, e.getMessage());
						} catch (IOException e) {
							JOptionPane.showMessageDialog(null, e.getMessage());
						} finally {
							server.close();
						}
					}
				};
				Runnable mainGame = new Runnable() {

					@Override
					public void run() {
						networkWindow.start();
					}
				};
				pool = Executors.newCachedThreadPool();
				pool.execute(mainGame);
				pool.execute(net);
			}
		});
		JButton CancelButton = new JButton("Cancel\r\n");
		CancelButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		CancelButton.setBounds(220, 289, 106, 23);
		CancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}

		});
		getContentPane().add(CancelButton);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Create Network Game");
		setSize(371, 349);
		setLocationRelativeTo(startWindow);
		setIconImage(new ImageIcon(getClass().getResource(
				"/resource/images/startLocalGame.png")).getImage());
		setResizable(false);
		setVisible(true);

	}

	@Override
	public void update(Observable o, Object arg) {
		if (generalAttribute.hasChangePlayerImage(1))
			playerAvater.paintImage(1);
	}

}
