package com.application.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.guti16.model.DrawImageInPanel;
import com.guti16.model.GameAttribute;
import javax.swing.border.BevelBorder;

public class Statistics extends JDialog {
	private static final long serialVersionUID = -2399283676848137278L;
	private JLabel player2Name;
	private JLabel player1WinLabel;
	private JLabel player2WinLabel;
	private JButton resetButton;
	private JPanel contentPanel;

	public Statistics(Window owner) {
		super(owner, ModalityType.APPLICATION_MODAL);
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(400, 300);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width / 2 - 200, dim.height / 2 - 150);
		getContentPane().setLayout(null);

		contentPanel = new DrawImageInPanel(new ImageIcon(getClass().getResource("/resource/images/back_score.jpg")).getImage(), 400, 300);
		contentPanel.setBorder(new BevelBorder(BevelBorder.RAISED, Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK));
		contentPanel.setBackground(new Color(0, 128, 128));
		contentPanel.setBounds(0, 0, 400, 300);
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);

		JLabel statisticsLabel = new JLabel("Statistics");
		statisticsLabel.setForeground(Color.WHITE);
		statisticsLabel.setFont(new Font("serif", Font.PLAIN, 50));
		statisticsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		statisticsLabel.setBounds(0, 11, 390, 64);
		contentPanel.add(statisticsLabel);

		resetButton = new JButton("Reset Stats");
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GameAttribute.resetScoreBoard();
				player2WinLabel.setText("Won: "
						+ GameAttribute.getScoreBoard().y);
				player1WinLabel.setText("Won: "
						+ GameAttribute.getScoreBoard().x);
			}
		});
		resetButton.setFont(new Font("serif", Font.BOLD, 30));
		resetButton.setForeground(new Color(255, 255, 255));
		resetButton.setBackground(new Color(0, 0, 0));
		resetButton.setBorderPainted(false);
		resetButton.setBounds(0, 260, 200, 40);
		contentPanel.add(resetButton);

		JButton backButton = new JButton("Back");
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		backButton.setBackground(new Color(0, 0, 0));
		backButton.setForeground(new Color(255, 255, 255));
		backButton.setFont(new Font("serif", Font.BOLD, 30));
		backButton.setBounds(200, 260, 200, 40);
		contentPanel.add(backButton);

		DrawImageInPanel player1Image = new DrawImageInPanel(
				GameAttribute.getPlayerImage(1), 0, 0, 120, 100);
		player1Image.setBorder(new BevelBorder(BevelBorder.RAISED, Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK));
		player1Image.setBounds(26, 71, 120, 100);
		contentPanel.add(player1Image);

		DrawImageInPanel player2Image = new DrawImageInPanel(
				GameAttribute.getPlayerImage(2), 0, 0, 120, 100);
		player2Image.setBorder(new BevelBorder(BevelBorder.RAISED, Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK));
		player2Image.setBounds(250, 71, 120, 100);
		contentPanel.add(player2Image);

		JLabel lblVs = new JLabel("VS");
		lblVs.setForeground(new Color(255, 255, 255));
		lblVs.setFont(new Font("serif", Font.BOLD, 35));
		lblVs.setHorizontalAlignment(SwingConstants.CENTER);
		lblVs.setBounds(173, 107, 46, 40);
		contentPanel.add(lblVs);

		JLabel player1Name = new JLabel(GameAttribute.getPlayerName(1));
		player1Name.setFont(new Font("serif", Font.BOLD, 22));
		player1Name.setForeground(new Color(255, 255, 255));
		player1Name.setHorizontalAlignment(SwingConstants.CENTER);
		player1Name.setBounds(0, 182, 172, 26);
		contentPanel.add(player1Name);

		player2Name = new JLabel(GameAttribute.getPlayerName(2));
		player2Name.setForeground(new Color(255, 255, 255));
		player2Name.setHorizontalAlignment(SwingConstants.CENTER);
		player2Name.setFont(new Font("serif", Font.BOLD, 22));
		player2Name.setBounds(200, 182, 190, 26);
		contentPanel.add(player2Name);

		player1WinLabel = new JLabel("Won: " + GameAttribute.getScoreBoard().x);
		player1WinLabel.setForeground(new Color(255, 255, 255));
		player1WinLabel.setFont(new Font("serif", Font.BOLD, 30));
		player1WinLabel.setHorizontalAlignment(SwingConstants.CENTER);
		player1WinLabel.setBounds(25, 219, 140, 30);
		contentPanel.add(player1WinLabel);
		
		player2WinLabel = new JLabel("Won: " + GameAttribute.getScoreBoard().y);
		player2WinLabel.setForeground(new Color(255, 255, 255));
		player2WinLabel.setFont(new Font("serif", Font.BOLD, 30));
		player2WinLabel.setBounds(250, 219, 140, 30);
		contentPanel.add(player2WinLabel);
		setVisible(true);
	}
}
