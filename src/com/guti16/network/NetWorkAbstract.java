package com.guti16.network;

import java.awt.Point;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Observable;
import java.util.Scanner;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import com.application.model.ClickAdapter;
import com.application.model.GeneralAttribute;

public abstract class NetWorkAbstract extends Observable {
	protected ObjectOutputStream output;
	protected ObjectInputStream input;
	protected Socket connection;
	protected int port;
	protected boolean connected = false;
	protected String currentMessage;
	protected boolean changeMessage;
	protected String playerName;
	protected boolean gameStarted = false;

	public abstract void tryToConnect() throws IOException;

	protected boolean processData(String command) {
		String action;
		Point clicked = new Point();
		String report = null;
		Scanner scn = new Scanner(command);
		action = scn.next();
		if (action.equalsIgnoreCase("Exit:") || action.equalsIgnoreCase("quit")) {
			scn.close();
			return false;
		} else if (action.equalsIgnoreCase("clicked:")) {
			clicked.x = scn.nextInt();
			clicked.y = scn.nextInt();
			ClickAdapter.getInstance().clickedRemoteCall(clicked.x, clicked.y);
		} else if (action.equalsIgnoreCase("ChangeTurn:")) {
			ClickAdapter.getInstance().changeTurnRemoteCall();
		} else if (action.equalsIgnoreCase("message:")) {
			scn.skip(" ");
			String msg = scn.nextLine();
			setCurrentMessage(msg);
		} else if (action.equalsIgnoreCase("Report:")) {
			report = scn.nextLine();
			displayMessage("Report:" + report);
		} else if (action.equalsIgnoreCase("Header:")) {
			int timeOut = scn.nextInt();
			if (timeOut != -1)
				GeneralAttribute.setTimeOut(timeOut);
			int id = scn.nextInt();
			String name = scn.nextLine();
			if(id==2)
			setCurrentMessage(name+ ".name" + id);
			else
			 GeneralAttribute.setPlayerName(name, id);
		} else if (action.equalsIgnoreCase("start")) {
			setGameStarted(true);
		}
		scn.close();
		return true;
	}

	private void setCurrentMessage(String nextLine) {
		currentMessage = nextLine;
		displayMessage(currentMessage);
		setChangeMessage(true);
	}

	protected void displayMessage(final String message) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				System.out.println(message);
			}
		});
	}

	public void sendData(String message) {
		displayMessage(message);
		try {
			output.writeObject(message);
			output.flush();
		} catch (IOException e) {
			displayMessage("Error while writing object");
		}
	}

	public void close() {
		closeConnection();
	}

	protected void closeConnection() {
		try {
			if (input != null && output != null && connection != null) {
				input.close();
				output.close();
				connection.close();
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	protected void getStream() throws NullPointerException, EOFException,
			IOException {

		displayMessage("Trying to receive I/O");
		output = new ObjectOutputStream(connection.getOutputStream());
		output.flush();
		input = new ObjectInputStream(connection.getInputStream());
		displayMessage("Got I/O stream");
		setConnected(true);

	}

	public void setConnected(boolean connected) {
		this.connected = connected;
		setChanged();
		notifyObservers();

	}

	public boolean getConnected() {
		return connected;
	}

	public void clearConnected() {
		connected = false;
	}

	public void setChangeMessage(boolean flag) {
		changeMessage = flag;
		setChanged();
		notifyObservers();
	}

	public boolean hasChangedMessage() {
		boolean f = changeMessage;
		changeMessage = false;
		return f;
	}

	public void processConnection() throws IOException {
		while (true) {
			try {
				String msg = (String) input.readObject();
				if (!processData(msg)) {
					throw new EOFException("Connection Terminated By Opponent");

				}

			} catch (ClassNotFoundException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	public void runNetwork() throws IOException {

		tryToConnect();
		getStream();
		processConnection();

	}

	public String getAddress() {
		return connection.getInetAddress().getHostAddress();
	}

	public int getPort() {
		return port;
	}

	public String getPlayeName() {
		return playerName;
	}

	public String getMessage() {
		changeMessage = false;
		return currentMessage;
	}

	public void setGameStarted(boolean value) {
		gameStarted = value;
		setChanged();
		notifyObservers();
	}

	public boolean getGameStarted() {
		boolean flag = gameStarted;
		gameStarted = false;
		return flag;
	}
}
