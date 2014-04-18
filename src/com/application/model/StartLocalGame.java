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
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.TitledBorder;

import com.application.view.PlayerAvaterManager;
import com.guti16.controller.Guti_16_Game;
import com.guti16.model.DrawImageInPanel;
import com.guti16.model.GameAttribute;

public class StartLocalGame extends JDialog implements Observer {

	private static final long serialVersionUID = -597034230608279964L;
	public static Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit()
			.getScreenSize();
	private JTextField player1Name;
	private JTextField player2Name;
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
	private DrawImageInPanel player1Avater;
	private DrawImageInPanel player2avater;
	private Guti_16_Game guti_16_game;
	private Point selectScreenSize = screenSize[0];

	public StartLocalGame(Window owner, GeneralAttribute ga) {
		super(owner, ModalityType.APPLICATION_MODAL);
		this.generalAttribute = ga;
		generalAttribute.addObserver(this);
		getContentPane().setBackground(new Color(102, 102, 102));
		getContentPane().setCursor(
				Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		setBackground(new Color(0, 51, 51));
		getContentPane().setLayout(null);
		
		JPanel panel = new DrawImageInPanel(new ImageIcon(getClass().getResource("/resource/images/pattern.jpg")).getImage(), 386, 355);
		panel.setBounds(0, 0, 386, 355);
		getContentPane().add(panel);
		panel.setLayout(null);
		
				JPanel startLocalGamePanel = new JPanel();
				startLocalGamePanel.setBounds(10, 11, 366, 300);
				panel.add(startLocalGamePanel);
				startLocalGamePanel.setBackground(new Color(153, 153, 153));
				startLocalGamePanel.setBorder(new TitledBorder(UIManager
						.getBorder("TitledBorder.border"), "Local Game Settings",
						TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0,
								153)));
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
								timeOutspinner.setBounds(294, 20, 61, 24);
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
												.getBorder("TitledBorder.border"), "Player Information",
												TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0,
														102)));
										playerNamePanel.setBounds(10, 77, 345, 206);
										startLocalGamePanel.add(playerNamePanel);
										playerNamePanel.setLayout(null);
										
												JSeparator separator = new JSeparator();
												separator.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null,
														null, null));
												separator.setBounds(172, 20, 8, 175);
												playerNamePanel.add(separator);
												
														JLabel leftPlayerLabel = new JLabel("Player 1 (Left)");
														leftPlayerLabel.setForeground(new Color(0, 100, 0));
														leftPlayerLabel.setFont(new Font("Serif", Font.BOLD, 14));
														leftPlayerLabel.setBounds(10, 15, 100, 18);
														playerNamePanel.add(leftPlayerLabel);
														
																JLabel rightPlayerLabel = new JLabel("Player 2 (Right)");
																rightPlayerLabel.setFont(new Font("Serif", Font.BOLD, 14));
																rightPlayerLabel.setForeground(new Color(0, 100, 0));
																rightPlayerLabel.setBounds(190, 15, 100, 18);
																playerNamePanel.add(rightPlayerLabel);
																
																		JLabel laftNameLabel = new JLabel("Name: ");
																		laftNameLabel.setFont(new Font("Serif", Font.BOLD, 12));
																		laftNameLabel.setForeground(new Color(0, 0, 128));
																		laftNameLabel.setBounds(10, 41, 46, 14);
																		playerNamePanel.add(laftNameLabel);
																		
																				JLabel rightNameLabel = new JLabel("Name: ");
																				rightNameLabel.setForeground(new Color(0, 0, 128));
																				rightNameLabel.setFont(new Font("Serif", Font.BOLD, 12));
																				rightNameLabel.setBounds(190, 42, 46, 14);
																				playerNamePanel.add(rightNameLabel);
																				
																						player1Name = new JTextField();
																						player1Name.addActionListener(new ActionListener() {
																							public void actionPerformed(ActionEvent e) {
																								GeneralAttribute.setPlayerName(player1Name.getText(), 1);
																							}
																						});
																						player1Name.setHorizontalAlignment(SwingConstants.CENTER);
																						player1Name.setBounds(10, 58, 150, 25);
																						playerNamePanel.add(player1Name);
																						player1Name.setForeground(new Color(0, 128, 0));
																						player1Name.setFont(new Font("Serif", Font.BOLD, 12));
																						player1Name.setText(GeneralAttribute.getPlayerName(1));
																						player1Name.setColumns(10);
																						player2Name = new JTextField();
																						player2Name.addActionListener(new ActionListener() {
																							public void actionPerformed(ActionEvent e) {
																								GeneralAttribute.setPlayerName(player2Name.getText(), 2);
																							}
																						});
																						player2Name.setHorizontalAlignment(SwingConstants.CENTER);
																						player2Name.setBounds(190, 58, 150, 25);
																						playerNamePanel.add(player2Name);
																						player2Name.setForeground(new Color(0, 128, 0));
																						player2Name.setFont(new Font("Serif", Font.BOLD, 12));
																						player2Name.setText(GeneralAttribute.getPlayerName(2));
																						player2Name.setColumns(10);
																						
																								JLabel lblAvater = new JLabel("Avater:");
																								lblAvater.setForeground(new Color(0, 0, 128));
																								lblAvater.setFont(new Font("Serif", Font.BOLD, 15));
																								lblAvater.setBounds(10, 86, 60, 14);
																								playerNamePanel.add(lblAvater);
																								
																										JLabel lblAvater_1 = new JLabel("Avater: \r\n");
																										lblAvater_1.setForeground(new Color(0, 0, 128));
																										lblAvater_1.setFont(new Font("Serif", Font.BOLD, 15));
																										lblAvater_1.setBounds(190, 86, 66, 14);
																										playerNamePanel.add(lblAvater_1);
																										
																												player1Avater = new DrawImageInPanel(
																														GeneralAttribute.getPlayerImage(1), 0, 0, 150, 90);
																												player1Avater.addMouseListener(new MouseAdapter() {
																													@Override
																													public void mouseClicked(MouseEvent e) {
																														new PlayerAvaterManager(StartLocalGame.this, generalAttribute,
																																getClass().getResource("/resource/images/avaters"), 1);

																													}
																												});
																												player1Avater.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
																												player1Avater.setBounds(10, 110, 150, 90);
																												playerNamePanel.add(player1Avater);
																												player1Avater.setLayout(null);
																												
																														player2avater = new DrawImageInPanel(
																																GeneralAttribute.getPlayerImage(2), 0, 0, 150, 90);
																														player2avater.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
																														player2avater.addMouseListener(new MouseAdapter() {
																															@Override
																															public void mouseClicked(MouseEvent e) {
																																new PlayerAvaterManager(StartLocalGame.this, generalAttribute,
																																		getClass().getResource("/resource/images/avaters"), 2);
																															}
																														});
																														player2avater.setBounds(190, 110, 150, 90);
																														playerNamePanel.add(player2avater);
																														
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
																																screenResulationComboBox.setBounds(219, 48, 136, 20);
																																startLocalGamePanel.add(screenResulationComboBox);
																																JButton okButton = new JButton("Ok");
																																okButton.setBounds(171, 322, 89, 23);
																																panel.add(okButton);
																																okButton.setOpaque(true);
																																JButton CancelButton = new JButton("Cancel\r\n");
																																CancelButton.setBounds(270, 322, 89, 23);
																																panel.add(CancelButton);
																																CancelButton.addActionListener(new ActionListener() {

																																	@Override
																																	public void actionPerformed(ActionEvent e) {
																																		dispose();
																																	}
																																});
																																okButton.addActionListener(new ActionListener() {

																																	@Override
																																	public void actionPerformed(ActionEvent e) {
																																		GeneralAttribute.setPlayerName(player1Name.getText(), 1);
																																		GeneralAttribute.setPlayerName(player2Name.getText(), 2);
																																		GeneralAttribute.setTimeOut((int) timeOutspinner.getValue());

																																		GameAttribute.SMALL_SCREEN_SIZE = new Dimension(
																																				selectScreenSize.x, selectScreenSize.y);
																																		GameAttribute.fullScreenMode(false);
																																		guti_16_game = new Guti_16_Game(GeneralAttribute
																																				.getPlayerName(1), GeneralAttribute.getPlayerName(2),
																																				GeneralAttribute.getPlayerImage(1), GeneralAttribute
																																						.getPlayerImage(2), GeneralAttribute
																																						.getTimeOut(), null);
																																		guti_16_game.addWindowListener(new WindowAdapter() {
																																			@Override
																																			public void windowOpened(WindowEvent e) {
																																				setVisible(false);
																																				getOwner().setVisible(false);
																																			}

																																			@Override
																																			public void windowClosed(WindowEvent e) {
																																				if (Guti_16_Game.GAME_END) {
																																					getOwner().setEnabled(true);
																																					getOwner().setVisible(true);
																																				}
																																			}
																																		});

																																	}
																																});
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Start Local Game");
		setSize(392, 384);
		setLocationRelativeTo(getOwner());
		setIconImage(new ImageIcon(getClass().getResource(
				"/resource/images/startLocalGame.png")).getImage());
		setResizable(false);
		setVisible(true);
	}

	@Override
	public void update(Observable o, Object arg) {
		if (generalAttribute.hasChangePlayerImage(1))
			player1Avater.paintImage(1);
		if (generalAttribute.hasChangePlayerImage(2))
			player2avater.paintImage(2);
		player1Name.setText(GeneralAttribute.getPlayerName(1));
		player2Name.setText(GeneralAttribute.getPlayerName(2));

	}

}
