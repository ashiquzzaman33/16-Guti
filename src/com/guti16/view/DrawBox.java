package com.guti16.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;

import javax.swing.JPanel;

import com.guti16.model.GameAttribute;

/**
 * This class draws game court
 * 
 * @author Md. Ashiquzzaman & Rashik Hasnat
 * @version 1.00
 */
public class DrawBox extends JPanel {
	private static final long serialVersionUID = -6614365542806334934L;

	/**
	 * Constructor method
	 */
	public DrawBox() {
		setOpaque(false);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setPaint(GameAttribute.GRID_COLOR);
		g2d.setStroke(new BasicStroke(GameAttribute.LINE_DEPTH,
				BasicStroke.JOIN_ROUND, BasicStroke.JOIN_ROUND));
		GradientPaint gp = new GradientPaint(20, 20, Color.WHITE,
		        0, 20, new Color(200, 200, 200), true);
		g2d.setPaint(gp);
		// <-------------------------------DRAW MIDDLE BOX OR
		// RECTIANGLE------------------------------------------------------>
		for (int i = 2; i <= 6; i++) {
			g.drawLine(i * (GameAttribute.GAME_BOARD_WIDTH / 8),
					GameAttribute.VERTICAL_GAP, i
							* (GameAttribute.GAME_BOARD_WIDTH / 8),
					GameAttribute.GAME_BOARD_HEIGHT
							- GameAttribute.VERTICAL_GAP); // Column
			g.drawLine(
					2 * GameAttribute.GAME_BOARD_WIDTH / 8,
					(i - 2)
							* (GameAttribute.GAME_BOARD_HEIGHT - 2 * GameAttribute.VERTICAL_GAP)
							/ 4 + GameAttribute.VERTICAL_GAP,
					GameAttribute.GAME_BOARD_WIDTH
							- (2 * GameAttribute.GAME_BOARD_WIDTH / 8),
					((i - 2)
							* (GameAttribute.GAME_BOARD_HEIGHT - 2 * GameAttribute.VERTICAL_GAP) / 4)
							+ GameAttribute.VERTICAL_GAP);// Row
		}
		g.drawLine(GameAttribute.GAME_BOARD_WIDTH / 4,
				GameAttribute.GAME_BOARD_HEIGHT / 2,
				GameAttribute.GAME_BOARD_WIDTH / 2, GameAttribute.VERTICAL_GAP); // Left-middle
																					// to
																					// Top-middle
		g.drawLine(GameAttribute.GAME_BOARD_WIDTH / 4,
				GameAttribute.GAME_BOARD_HEIGHT / 2,
				GameAttribute.GAME_BOARD_WIDTH / 2,
				GameAttribute.GAME_BOARD_HEIGHT - GameAttribute.VERTICAL_GAP); // Left-middle
																				// to
																				// Bottom-middle
		g.drawLine(6 * GameAttribute.GAME_BOARD_WIDTH / 8,
				GameAttribute.GAME_BOARD_HEIGHT / 2,
				GameAttribute.GAME_BOARD_WIDTH / 2, GameAttribute.VERTICAL_GAP); // Right-middle
																					// to
																					// Top-middle
		g.drawLine(6 * GameAttribute.GAME_BOARD_WIDTH / 8,
				GameAttribute.GAME_BOARD_HEIGHT / 2,
				GameAttribute.GAME_BOARD_WIDTH / 2,
				GameAttribute.GAME_BOARD_HEIGHT - GameAttribute.VERTICAL_GAP); // Right-middle
																				// to
																				// Bottom-middle

		g.drawLine(GameAttribute.GAME_BOARD_WIDTH / 4,
				GameAttribute.VERTICAL_GAP,
				6 * GameAttribute.GAME_BOARD_WIDTH / 8,
				GameAttribute.GAME_BOARD_HEIGHT - GameAttribute.VERTICAL_GAP);
		g.drawLine(GameAttribute.GAME_BOARD_WIDTH / 4,
				GameAttribute.GAME_BOARD_HEIGHT - GameAttribute.VERTICAL_GAP,
				6 * GameAttribute.GAME_BOARD_WIDTH / 8,
				GameAttribute.VERTICAL_GAP);
		// <-------------------------------------DRAW LEFT TRIANGLE
		// BOX----------------------------------------------------------->
		g.drawLine(GameAttribute.GAME_BOARD_WIDTH / 4,
				GameAttribute.GAME_BOARD_HEIGHT / 2, GameAttribute.CONST_RATIO,
				GameAttribute.CONST_RATIO); // Top right to left
		g.drawLine(GameAttribute.GAME_BOARD_WIDTH / 4,
				GameAttribute.GAME_BOARD_HEIGHT / 2, GameAttribute.CONST_RATIO,
				GameAttribute.GAME_BOARD_HEIGHT - GameAttribute.CONST_RATIO); // Bottom
																				// right
																				// to
																				// left
		g.drawLine(GameAttribute.GAME_BOARD_WIDTH / 4,
				GameAttribute.GAME_BOARD_HEIGHT / 2, GameAttribute.CONST_RATIO,
				GameAttribute.GAME_BOARD_HEIGHT / 2); // Middle right to left
		g.drawLine(GameAttribute.CONST_RATIO, GameAttribute.CONST_RATIO,
				GameAttribute.CONST_RATIO, GameAttribute.GAME_BOARD_HEIGHT
						- GameAttribute.CONST_RATIO); // Outer top to bottom
														// line
		g.drawLine(GameAttribute.GAME_BOARD_WIDTH / 8
				+ GameAttribute.CONST_RATIO / 3,
				GameAttribute.GAME_BOARD_HEIGHT / 4 + GameAttribute.CONST_RATIO
						/ 3, GameAttribute.GAME_BOARD_WIDTH / 8
						+ GameAttribute.CONST_RATIO / 3,
				GameAttribute.GAME_BOARD_HEIGHT / 2
						+ GameAttribute.GAME_BOARD_HEIGHT / 4
						- GameAttribute.CONST_RATIO / 3); // Inner top to bottom
															// line
		// //<--------------------------------------DRAW RIGHT TRIANGLE
		// BOX--------------------------------------------------------------->
		g.drawLine(6 * GameAttribute.GAME_BOARD_WIDTH / 8,
				GameAttribute.GAME_BOARD_HEIGHT / 2,
				GameAttribute.GAME_BOARD_WIDTH - GameAttribute.CONST_RATIO,
				GameAttribute.CONST_RATIO);
		g.drawLine(6 * GameAttribute.GAME_BOARD_WIDTH / 8,
				GameAttribute.GAME_BOARD_HEIGHT / 2,
				GameAttribute.GAME_BOARD_WIDTH - GameAttribute.CONST_RATIO,
				GameAttribute.GAME_BOARD_HEIGHT - GameAttribute.CONST_RATIO);
		g.drawLine(6 * GameAttribute.GAME_BOARD_WIDTH / 8,
				GameAttribute.GAME_BOARD_HEIGHT / 2,
				GameAttribute.GAME_BOARD_WIDTH - GameAttribute.CONST_RATIO,
				GameAttribute.GAME_BOARD_HEIGHT / 2);
		g.drawLine(GameAttribute.GAME_BOARD_WIDTH - GameAttribute.CONST_RATIO,
				GameAttribute.CONST_RATIO, GameAttribute.GAME_BOARD_WIDTH
						- GameAttribute.CONST_RATIO,
				GameAttribute.GAME_BOARD_HEIGHT - GameAttribute.CONST_RATIO);
		g.drawLine(GameAttribute.GAME_BOARD_WIDTH
				- GameAttribute.GAME_BOARD_WIDTH / 8
				- GameAttribute.CONST_RATIO / 3,
				GameAttribute.GAME_BOARD_HEIGHT / 4 + GameAttribute.CONST_RATIO
						/ 3, GameAttribute.GAME_BOARD_WIDTH
						- GameAttribute.GAME_BOARD_WIDTH / 8
						- GameAttribute.CONST_RATIO / 3,
				GameAttribute.GAME_BOARD_HEIGHT / 2
						+ GameAttribute.GAME_BOARD_HEIGHT / 4
						- GameAttribute.CONST_RATIO / 3); // Inner top-bottom
															// line
	}
}
