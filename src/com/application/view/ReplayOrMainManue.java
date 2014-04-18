package com.application.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import com.guti16.controller.Guti_16_Game;
import com.guti16.model.DrawImageInPanel;

public class ReplayOrMainManue extends JDialog{
	private static final long serialVersionUID = 9076034709128146815L;
	private Guti_16_Game guti_16_game;

	public ReplayOrMainManue(Guti_16_Game owner, final int sleepTime) {
		super(owner, ModalityType.APPLICATION_MODAL);
		guti_16_game = owner;
		Thread thread = new Thread() {
			public void run() {
				setUndecorated(true);
				setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				getContentPane().setLayout(null);
				

				JPanel containerPanel = new DrawImageInPanel(
						new ImageIcon(getClass().getResource("/resource/images/back_replay.jpg")).getImage(), 400, 223);
				containerPanel.setBorder(new BevelBorder(BevelBorder.RAISED, Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK));
				containerPanel.setBounds(0, 0, 400, 223);
				getContentPane().add(containerPanel);
				containerPanel.setLayout(null);

				JButton playAgainButton = new JButton("Play Again");
				playAgainButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
						guti_16_game.replay();
					}
				});
				playAgainButton.setBorderPainted(false);
				playAgainButton.setBackground(new Color(0, 0, 0));
				playAgainButton.setForeground(new Color(255, 255, 255));
				playAgainButton.setFont(new Font("Serif", Font.BOLD, 25));
				playAgainButton.setBounds(10, 55, 195, 100);
				containerPanel.add(playAgainButton);

				JButton mainMenuButton = new JButton("Main Menu");
				mainMenuButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
						guti_16_game.exit();
					}
				});
				mainMenuButton.setBorderPainted(false);
				mainMenuButton.setBackground(new Color(0, 0, 0));
				mainMenuButton.setForeground(new Color(255, 255, 255));
				mainMenuButton.setFont(new Font("Serif", Font.BOLD, 25));
				mainMenuButton.setBounds(215, 55, 175, 100);
				containerPanel.add(mainMenuButton);
				setSize(400, 222);
				Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
				setLocation(dim.width / 2 - 150, dim.height / 2 - 150);

				try {
					Thread.sleep(sleepTime);
					setVisible(true);

				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			};
		};
		thread.start();
	}
}
