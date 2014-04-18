package com.guti16.view;

import java.awt.Font;
import java.awt.Window;

import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

import java.awt.BorderLayout;

public class GameRulesDialog extends JDialog {
	public GameRulesDialog(Window owner) {
		super(owner);
		setTitle("16 Guti Rules");
		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setSize(445, 400);
		
		JEditorPane txtrGameRulesGoes = new JEditorPane("text/html", "");
		txtrGameRulesGoes.setEditable(false);
		txtrGameRulesGoes.setText("<h2 color='FF5555'>Rules of playing 16 Guti: </h2>" +
				"<li color='BLUE'>Each pawn(Guti) can move to any adjacent direction indicated on the board.</li>" +
				"<li color='BLUE'>If there is an opponent's pawn adjacent to selected pawn, and the place after" +
				" it is empty, the selected pawn can capture opponent's pawn & move to the " +
				"next position to it. This can be continued till the selected pawn can" +
				" capture another pawn of the opponent. The player can also stop this movement" +
				" any time he/she wants."+
				"<li color='blue'>The player who captures all the pawn of the opponent wins.</li>");
		txtrGameRulesGoes.setFont(new Font("serif", Font.ITALIC, 20));
		txtrGameRulesGoes.setCaretPosition(0);
		getContentPane().add(new JScrollPane(txtrGameRulesGoes), BorderLayout.CENTER);
		setLocationRelativeTo(null);
		setVisible(true);
	}

}
