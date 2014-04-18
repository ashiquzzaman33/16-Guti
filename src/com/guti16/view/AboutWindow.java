package com.guti16.view;

import java.awt.BorderLayout;
import java.awt.Dialog.ModalityType;
import java.awt.Font;
import java.awt.Window;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import javax.swing.JLabel;

import com.guti16.model.DrawImageInPanel;

public class AboutWindow extends JDialog{
	public AboutWindow(final Window owner) {
		super(owner);
		setTitle("About");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setSize(400, 340);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setLocationRelativeTo(owner);
		setResizable(false);
		JPanel aboutPanel =  new DrawImageInPanel(new ImageIcon(getClass().getResource(
				"/resource/images/about.png")).getImage(), 400, 310);
		add(aboutPanel);
		setVisible(true);
	}
}
