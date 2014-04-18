package com.guti16.model;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import com.application.model.GeneralAttribute;

public class DrawImageInPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private Image image;
	private int x;
	private int y;
	private int width;
	private int height;

	public DrawImageInPanel(Image image, int x, int y, int width, int height) {
		this.image = image;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public DrawImageInPanel(Image image, int width, int height) {
		this(image, 0, 0, width, height);
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, x, y, width, height, this);
	}

	public void paintImage(int playerId) {
		image = GeneralAttribute.getPlayerImage(playerId);
		repaint();
	}
}
