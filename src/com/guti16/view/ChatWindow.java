package com.guti16.view;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import com.guti16.model.GameAttribute;
import com.guti16.network.NetWorkAbstract;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JTextField;
import javax.swing.JTextArea;

public class ChatWindow extends JPanel implements Observer {
	private static final long serialVersionUID = 1L;
	private JTextField chatInput;
	private JTextArea chatHistory;
	private NetWorkAbstract netWorkAbstract;

	public ChatWindow(NetWorkAbstract net) {
		setOpaque(false);
		setLayout(null);
		this.netWorkAbstract = net;
		JInternalFrame internalFrame = new JInternalFrame("Chat Window");
		setBounds(0, GameAttribute.GAME_BOARD_HEIGHT
				+ GameAttribute.PLAYER_INFORMATION_PANE_HEIGHT - 180, 250, 180);
		internalFrame.setBounds(0, 153, 250, 29);
		add(internalFrame);
		internalFrame.setMaximizable(true);
		internalFrame.setVisible(true);
		internalFrame.setResizable(false);
		internalFrame.getContentPane().setLayout(new BorderLayout(0, 0));
		netWorkAbstract.addObserver(this);
		chatInput = new JTextField();
		chatInput.setText("Type Your Message Here");
		internalFrame.getContentPane().add(chatInput, BorderLayout.SOUTH);
		chatInput.addFocusListener(new FocusAdapter() {

			@Override
			public void focusLost(FocusEvent e) {
				chatInput.setText("Typer Your Message Here");
			}
			@Override
			public void focusGained(FocusEvent e) {
				chatInput.setText("");
			}
		});
		chatInput.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				chatInput.setText("");
			}
		});
		chatInput.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String msg = GameAttribute
						.getPlayerName(GameAttribute.NETWORK_GAME_PLAYER_ID)
						+ ": " + chatInput.getText();
				netWorkAbstract.sendData("Message: " + msg);
				chatHistory.append(msg + "\n");
				chatInput.setText("");

			}
		});
		chatInput.setColumns(10);

		chatHistory = new JTextArea();
		internalFrame.getContentPane().add(new JScrollPane(chatHistory),
				BorderLayout.CENTER);
		chatHistory.setEditable(false);
	}

	@Override
	public void repaint() {
		setBounds(0, GameAttribute.GAME_BOARD_HEIGHT
				+ GameAttribute.PLAYER_INFORMATION_PANE_HEIGHT - 180, 250, 180);
	}

	@Override
	public void update(Observable o, Object arg) {
		if (netWorkAbstract.hasChangedMessage())
			showMessage(netWorkAbstract.getMessage());
	}

	public void showMessage(final String msg) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				chatHistory.append(msg + "\n");
			}
		});
	}
}
