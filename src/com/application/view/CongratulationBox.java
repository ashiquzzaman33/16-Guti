package com.application.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Window;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.guti16.controller.Guti_16_Game;
import com.guti16.model.DrawImageInPanel;

public class CongratulationBox extends JDialog {
	private static final long serialVersionUID = -6640780544972997637L;

	public CongratulationBox(Window owner, final Image image,
			final String winerName, final int sleepTime) {
		super(owner, ModalityType.APPLICATION_MODAL);
		getContentPane().setLayout(null);
		
		final JPanel panel = new DrawImageInPanel(new ImageIcon(
				getClass().getResource("/resource/images/back_cogr.JPG")).getImage(), 434, 261);
		panel.setBounds(0, 0, 434, 261);
		getContentPane().add(panel);
		panel.setLayout(null);
		Thread thread = new Thread() {
			@Override
			public void run() {
				setResizable(false);
				setUndecorated(true);
				JLabel congratulationLabel = new JLabel("Congratulation!!!");
				congratulationLabel.setForeground(new Color(255, 255, 255));
				congratulationLabel.setBackground(new Color(255, 0, 0));
				congratulationLabel.setAlignmentY(1.0f);
				congratulationLabel.setAlignmentX(1.0f);
				congratulationLabel.setEnabled(true);
				congratulationLabel.setFont(new Font("serif", Font.BOLD
						| Font.ITALIC, 47));
				congratulationLabel.setBounds(34, 11, 356, 58);
				panel.add(congratulationLabel);

				DrawImageInPanel playerImagePanel = new DrawImageInPanel(image,
						0, 0, 180, 140);
				playerImagePanel.setBounds(105, 80, 180, 140);
				panel.add(playerImagePanel);

				JLabel playerNameLabel = new JLabel(winerName);
				playerNameLabel.setForeground(new Color(255, 255, 255));
				playerNameLabel.setFont(new Font("serif", Font.BOLD, 30));
				playerNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
				playerNameLabel.setHorizontalTextPosition(SwingConstants.LEFT);
				playerNameLabel.setBounds(0, 220, 400, 39);
				panel.add(playerNameLabel);
				setUndecorated(true);
				setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				setBackground(Color.BLUE);
				Dimension dimension = Toolkit.getDefaultToolkit()
						.getScreenSize();
				setSize(400, 260);
				setLocation(dimension.width / 2 - 200,
						dimension.height / 2 - 140);
				getContentPane().validate();
				setVisible(true);
			}
		};
		thread.start();		
		Runnable r = new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(Guti_16_Game.CONGRATULATION_BOX_SHOW_TIME);
					CongratulationBox.this.dispose();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}
		};
		new Thread(r).start();
	}
}
